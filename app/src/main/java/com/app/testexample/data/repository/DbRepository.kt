package com.app.testexample.data.repository

import androidx.lifecycle.LiveData
import com.app.testexample.db.dao.DrugDao
import com.app.testexample.db.entity.Drug
import javax.inject.Inject

class DbRepository @Inject constructor(
    private val drugdao: DrugDao
) {


    // get all the events
    fun getAllDrugs(): LiveData<List<Drug>> =  drugdao.getAllDrugs()

    // adds an event to our database.
    suspend fun insertDrug(drugs: List<Drug>) {
        drugdao.insertAll(drugs)

    }

    suspend fun clearDrug() {
        drugdao.clearDrug()

    }



}
