package apps.robot.sindarin_dictionary_en.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel(), BaseJobContainer {
    override var coroutineScope = viewModelScope
    override val error = MutableLiveData<Throwable?>()
    override val loading = MutableLiveData<Boolean>()
    override val alert = MutableLiveData<String>()

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancel()
    }
}