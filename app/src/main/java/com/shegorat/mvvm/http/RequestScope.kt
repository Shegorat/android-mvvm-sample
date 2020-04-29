package com.shegorat.mvvm.http

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

interface RequestScope : CoroutineScope {

    val apiJob: Job
    override val coroutineContext: CoroutineContext
        get() = apiJob + Dispatchers.Main
}

fun RequestScope.api() = this.launch {

}

suspend fun <T> RequestScope.io(block: suspend () -> T) = withContext(Dispatchers.IO) {
    block()
}

suspend fun <T> RequestScope.main(block: suspend () -> T) = withContext(Dispatchers.Main) {
    block
}