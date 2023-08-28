package apps.robot.sindarin_dictionary_en.dictionary.list.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.DictionaryDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.ElfToEngDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.EngToElfDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word
import apps.robot.sindarin_dictionary_en.dictionary.base.data.ElfToEngDictionaryRepositoryImpl
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalPagingApi::class)
class DictionaryRemoteMediator(
    private val query: String,
    private val database: RoomDatabase,
    private val firebaseFirestore: FirebaseFirestore,
    private val dispatchers: AppDispatchers,
    private val dao: ElfToEngDao
): RemoteMediator<Int, Word>() {

    override suspend fun initialize(): InitializeAction {
        return super.initialize()
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Word>): MediatorResult {
        return try {
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    lastItem.id
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val words = withContext(dispatchers.network) {
                suspendCoroutine<List<ElfToEngWordEntity?>> { emitter ->
                    firebaseFirestore.collection(ElfToEngDictionaryRepositoryImpl.ELF_TO_ENG_WORDS)
                        .get(Source.SERVER)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                emitter.resume(
                                    task.result?.documents?.map {
                                        val word = it.toObject(ElfToEngWordEntity::class.java)
                                        word?.id = it.id
                                        word
                                    } ?: listOf()
                                )
                            } else {
                                emitter.resumeWithException(
                                    task.exception ?: Exception()
                                )
                            }
                        }
                }
            }.filterNotNull()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dao.insertAll(words)
                }

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                dao.insertAll(words)
            }

            MediatorResult.Success(
                endOfPaginationReached = words.isNotEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}