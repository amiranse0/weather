package com.example.weather.util

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit

val TIME_STEP_REQUEST = TimeUnit.MINUTES.toMillis(5)

fun <I, O1, O2, O3> boundResource(
    query1: () -> Flow<O1>,
    query2: () -> Flow<O2>,
    query3: () -> Flow<O3>,
    query4: () -> Flow<Int>,
    fetch: suspend () -> I,
    saveFetchResult: suspend (I) -> Unit,
    shouldFetch: suspend () -> Boolean = { true }

): Triple<MutableStateFlow<ResultOf<O1>>, MutableStateFlow<ResultOf<O2>>, MutableStateFlow<ResultOf<O3>>> =
    runBlocking {

        val stateFlow1: MutableStateFlow<ResultOf<O1>> =
            MutableStateFlow(ResultOf.Loading)
        val stateFlow2: MutableStateFlow<ResultOf<O2>> =
            MutableStateFlow(ResultOf.Loading)
        val stateFlow3: MutableStateFlow<ResultOf<O3>> =
            MutableStateFlow(ResultOf.Loading)

        try {
//        val data:I = fetch()
//        saveFetchResult(data)
            query4()
            async {
                query1().collect {
                    stateFlow1.emit(ResultOf.Success(it))
                }
            }
            async {
                query2().collect {
                    stateFlow2.emit(ResultOf.Success(it))
                }
            }
            async {
                query3().collect {
                    stateFlow3.emit(ResultOf.Success(it))
                }
            }
        } catch (e: Exception) {
            stateFlow1.emit(ResultOf.Error(e))
            stateFlow2.emit(ResultOf.Error(e))
            stateFlow3.emit(ResultOf.Error(e))
        }


        Triple(stateFlow1, stateFlow2, stateFlow3)
    }

suspend inline fun <I> fetchAndSaveDataToLocal(
    fetch: () -> I,
    saveFetchResult: (I) -> Unit,
    timeStep: Long
) {
    try {
        val data = fetch()
        saveFetchResult(data)
        delay(timeStep)
    } catch (_: Exception) {

    }
}