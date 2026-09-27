package apps.robot.sindarin_dictionary_en.dictionary.base

import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseAppInitializer
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.processLifecycleScope
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.ElfToEngDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.EngToElfDao
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.base.data.DictionaryRepositoryImpl
import apps.robot.sindarin_dictionary_en.dictionary.base.data.DictionaryDataSource
import apps.robot.sindarin_dictionary_en.dictionary.base.data.FirebaseDictionaryDataSource
import apps.robot.sindarin_dictionary_en.dictionary.base.data.LocalDictionaryDataSource
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordDomainMapper
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordDomainMapperImpl
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordElfToEngEntityMapper
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordElfToEngEntityMapperImpl
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordEngToElfEntityMapper
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordEngToElfEntityMapperImpl
import apps.robot.sindarin_dictionary_en.dictionary.list.data.paging.DictionaryPagingSource
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

internal fun dictionaryBaseModule() = module {
    single { FirebaseFirestore.getInstance() }
    single<DictionaryDataSource> {
        if (apps.robot.dictionary.impl.BuildConfig.USE_FIREBASE_DICTIONARY_SOURCE) {
            FirebaseDictionaryDataSource(
                db = get(),
                dispatchers = get(),
                mapper = get(),
                fallback = LocalDictionaryDataSource(resources = androidApplication().resources)
            )
        } else {
            LocalDictionaryDataSource(resources = androidApplication().resources)
        }
    }
    factory<DictionaryRepository> {
        DictionaryRepositoryImpl(
            dataSource = get(),
            elfToEngDao = get(),
            engToElfDao = get(),
            mapper = get(),
            engToElfEntityMapper = get(),
            elfToEngEntityMapper = get(),
            elfToEngPagingSource = get(named("ElfToEng")),
            engToElfPagingSource = get(named("EngToElf"))
        )
    }

    factory(named("EngToElf")) {
        DictionaryPagingSource(dictionaryDao = get<EngToElfDao>())
    }
    factory(named("ElfToEng")) {
        DictionaryPagingSource(dictionaryDao = get<ElfToEngDao>())
    }

    factory<WordDomainMapper> { WordDomainMapperImpl() }
    factory<WordElfToEngEntityMapper> { WordElfToEngEntityMapperImpl() }
    factory<WordEngToElfEntityMapper> { WordEngToElfEntityMapperImpl() }

    factory {
        DictionaryInitializer(
            loadWordList = get(),
            coroutineScope = processLifecycleScope,
            repository = get(),
            dispatchers = get()
        )
    } bind BaseAppInitializer::class
}
