package com.shegorat.mvvm.base

class Event<T>(val action: T) {
    var isDispatched = false

    @Throws
    fun dispatch(block: (T) -> Unit) {
        if (isDispatched)
            return

        block(action)
        isDispatched = true
    }
}