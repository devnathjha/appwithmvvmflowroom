package com.app.testexample.data.repository

import com.app.testexample.data.api.ApiService
import com.app.testexample.data.model.Medicine
import com.app.testexample.utils.NetworkHelper
import com.app.testexample.utils.NetworkUtils
import com.app.testexample.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.ConnectException
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val networkHelper: NetworkHelper,
) {

    suspend fun getMedicines(): Flow<State<Medicine>> {
        return flow {
            emit(State.LoadingState)
            try {
                if (networkHelper.isNetworkConnected()) {
                    apiService.getMedicine().let {
                        if (it.isSuccessful) {
                           // Log.e("Devnath", it.body().toString())
                            it.body()?.let { data ->
                                emit(State.DataState(data))
                            }
                        } else emit(NetworkUtils.resolveError(Exception(it.message())))
                    }
                } else emit(NetworkUtils.resolveError(ConnectException()))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(NetworkUtils.resolveError(e))
            }
        }.flowOn(Dispatchers.IO)
    }



}
