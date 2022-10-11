package com.example.weather.util

import kotlinx.coroutines.flow.*

fun <I, O> boundResource(
    query: () -> Flow<O>,
    fetch: suspend () -> I,
    saveFetchResult: suspend (I) -> Unit,
    shouldFetch: suspend (O) -> Boolean = { true }

) = flow {
    val result = query().first()

    val flow = if (shouldFetch(result)) {
        emit(ResultOf.Loading)
        try {
            val data: I = fetch()
            saveFetchResult(data)
            query().map { ResultOf.Success(it) }
        } catch (e: Exception) {
            query().map { ResultOf.Error(e) }
        }
    } else {
        query().map { ResultOf.Success(it) }
    }

    emitAll(flow)
}