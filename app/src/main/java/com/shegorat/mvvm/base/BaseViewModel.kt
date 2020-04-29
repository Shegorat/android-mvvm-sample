package com.shegorat.mvvm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shegorat.mvvm.http.RequestScope

abstract class BaseViewModel<T : ViewModelAction> : ViewModel(), RequestScope {
    val event: MutableLiveData<Event<T>> = MutableLiveData()

    fun pushEvent(action: T) {
        event.postValue(Event(action))
    }

}

sealed class ViewModelAction {
    data class Error(val throwable: Throwable) : ViewModelAction()
}
