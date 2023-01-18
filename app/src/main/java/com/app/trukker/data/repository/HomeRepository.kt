package com.app.trukker.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.app.trukker.data.api.ApiService
import com.app.trukker.data.model.Medicine
import com.app.trukker.db.dao.DrugDao
import com.app.trukker.db.entity.Drug
import com.app.trukker.utils.NetworkHelper
import com.app.trukker.utils.NetworkUtils
import com.app.trukker.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.ConnectException
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val networkHelper: NetworkHelper,
    private val drugdao:DrugDao
) {
    suspend fun getMedicines(): Flow<State<Medicine>> {
        return flow {
            emit(State.LoadingState)
            try {
                if (networkHelper.isNetworkConnected()) {
                    apiService.getMedicine().let {
                        if (it.isSuccessful) {
                            Log.e("Devnath", it.body().toString())
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


    // get all the events
    fun getAllDrugs(): LiveData<List<Drug>> =  drugdao.getAllDrugs()




    // adds an event to our database.
    suspend fun insertDrug(drugs: List<Drug>) {
        drugdao.insertAll(drugs)

    }

}
