package apps.robot.sindarin_dictionary_en.base

import androidx.annotation.AnyThread
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

interface BaseJobContainer {
    var coroutineScope: CoroutineScope
    val error: MutableLiveData<Throwable?>
    val loading: MutableLiveData<Boolean>
    val alert: MutableLiveData<String>

    fun launchJob(
        exceptionBlock: ((Throwable) -> Unit)? = null,
        block: suspend () -> Unit
    ): Job {
        return coroutineScope.launch(
            BaseCoroutineExceptionHandler(error, false, exceptionBlock)
        ) {
            block()
        }
    }

    @Suppress("DeferredIsResult")
    fun <T> asyncCatching(
        onError: ((Throwable) -> Unit)? = ::postError,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T
    ): Deferred<T?> {
        return coroutineScope.async(start = start) {
            runCatching {
                block()
            }.onFailure { error ->
                onError?.invoke(error)
            }.getOrNull()
        }
    }

    @AnyThread
    fun postError(throwable: Throwable?) {
        error.postValue(throwable)
    }

    private class BaseCoroutineExceptionHandler(
        private val errorData: MutableLiveData<Throwable?>,
        private val postToView: Boolean,
        private val callback: ((Throwable) -> Unit)?
    ) : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {

        override fun handleException(context: CoroutineContext, exception: Throwable) {
            //Timber.e(exception)
            callback?.invoke(exception)
            if (postToView) {
                errorData.postValue(exception)
            }
        }
    }
}