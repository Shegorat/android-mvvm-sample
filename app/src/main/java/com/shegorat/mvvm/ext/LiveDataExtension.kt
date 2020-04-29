package com.shegorat.mvvm.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

// Nullable
fun <T, R> LiveData<T?>.map(mapper: (T?) -> R?): LiveData<R?> {
    return MediatorLiveData<R?>().apply {
        addSource(this@map) {
            value = mapper(it)
        }
    }
}

fun <T> LiveData<T?>.filter(predicate: (T?) -> Boolean): LiveData<T?> {
    return MediatorLiveData<T?>().apply {
        addSource(this@filter) {
            if (predicate(it))
                value = it
        }
    }
}

fun <T> LiveData<T?>.distinct(): LiveData<T?> {
    return MediatorLiveData<T?>().apply {
        addSource(this@distinct) {
            if (value != it)
                value = it
        }
    }
}

fun <T, T2> LiveData<T?>.trigger(trigger: LiveData<T2?>, action: (T?, T2?) -> T): LiveData<T?> {
    return MediatorLiveData<T>().apply {
        var a1: T? = null
        var a2: T2? = null

        fun update() {
            val v = action(a1, a2)
            if (v != value)
                value = v
        }

        addSource(this@trigger) {
            a1 = it
            update()
        }

        addSource(trigger) {
            a2 = it
            update()
        }
    }
}

fun <T1, T2, R> LiveData<T1?>.zip(src2: LiveData<T2?>, zipper: (T1?, T2?) -> R?): LiveData<R?> {
    return MediatorLiveData<R?>().apply {
        var a1: T1? = null
        var a2: T2? = null

        fun update() {
            value = zipper(a1, a2)
        }

        addSource(this@zip) {
            a1 = it
            update()
        }

        addSource(src2) {
            a2 = it
            update()
        }
    }
}

fun <T1, T2, R> LiveData<T1?>.combine(
    src2: LiveData<T2?>,
    combiner: (T1?, T2?) -> R?
): LiveData<R?> {
    return MediatorLiveData<R?>().apply {
        var a1: T1? = null
        var a2: T2? = null

        fun update() {
            value = combiner(a1, a2)
        }

        addSource(this@combine) {
            a1 = it
            update()
        }

        addSource(src2) {
            a2 = it
            update()
        }
    }
}

fun <T> LiveData<T?>.notNull(): LiveData<T> {
    return MediatorLiveData<T>().apply {
        addSource(this@notNull) {
            if (it != null)
                value = it
        }
    }
}
