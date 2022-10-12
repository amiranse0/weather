package com.example.weather.util

import android.os.CountDownTimer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

val TIME_STEP_REQUEST = TimeUnit.MINUTES.toMillis(5)

fun <I, O1,O2,O3> boundResource(
    query1: () -> Flow<O1>,
    query2: () -> Flow<O2>,
    query3: () -> Flow<O3>,
    fetch: suspend () -> I,
    saveFetchResult: suspend (I) -> Unit,
    shouldFetch: suspend () -> Boolean = { true }

):Triple<MutableStateFlow<ResultOf<O1>>, MutableStateFlow<ResultOf<O2>>, MutableStateFlow<ResultOf<O3>>> {

    val stateFlow1: MutableStateFlow<ResultOf<O1>> =
        MutableStateFlow(ResultOf.Loading)
    val stateFlow2: MutableStateFlow<ResultOf<O2>> =
        MutableStateFlow(ResultOf.Loading)
    val stateFlow3: MutableStateFlow<ResultOf<O3>> =
        MutableStateFlow(ResultOf.Loading)

    object : CountDownTimer(TIME_STEP_REQUEST, 1000){
        val countDownTimer = this

        override fun onTick(millisUntilFinished: Long) {

        }

        override fun onFinish() {
            GlobalScope.launch {
                try {
                    val data = fetch()
                    saveFetchResult(data)
                }catch (_:Exception){

                }
                countDownTimer.start()
            }
        }
    }.start()

    CoroutineScope(Dispatchers.IO).launch{
        try {
            val data = fetch()
            saveFetchResult(data)

            query1().collect{
                stateFlow1.emit(ResultOf.Success(it))
            }
            query2().collect{
                stateFlow2.emit(ResultOf.Success(it))
            }
            query3().collect{
                stateFlow3.emit(ResultOf.Success(it))
            }
        } catch (e:Exception){
            stateFlow1.emit(ResultOf.Error(e))
            stateFlow2.emit(ResultOf.Error(e))
            stateFlow3.emit(ResultOf.Error(e))
        }
    }

//    val flow = if (shouldFetch(result)) {
//        emit(ResultOf.Loading)
//        try {
//            val data: I = fetch()
//            saveFetchResult(data)
//            query1().map { ResultOf.Success(it) }
//        } catch (e: Exception) {
//            query1().map { ResultOf.Error(e) }
//        }
//    } else {
//        query1().map { ResultOf.Success(it) }
//    }
//
//    emitAll(flow)

    return Triple(stateFlow1, stateFlow2, stateFlow3)
}