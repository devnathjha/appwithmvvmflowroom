package com.app.testexample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.app.testexample.data.model.Medicine
import com.app.testexample.data.repository.DbRepository
import com.app.testexample.data.repository.HomeRepository
import com.app.testexample.db.entity.Drug
import com.app.testexample.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val homeRepository: HomeRepository,
    private val dbRepository: DbRepository
) : AndroidViewModel(application){

    private val _mediceen = MutableStateFlow<State<Medicine>>(State.LoadingState)
    //private val homeRepository: HomeRepository
    //private val _drugList =  MutableLiveData<List<Drug>>()
    val drugList:LiveData<List<Drug>>

    var isOffLine:Boolean=false


    val medicine1:StateFlow<State<Medicine>>
        get() = _mediceen

    init {
        // if Data is blank in db then fetch from api and store it in db.

        /*val dao=  DatabaseHelper.getDatabase(application).getDrugDao()
        dbRepository = DbRepository(dao)
        print(dao.toString())*/

        drugList = dbRepository.getAllDrugs()

        if (drugList != null && !isOffLine){
            fetchMedicine()
            isOffLine = true
        }

    }





    fun fetchMedicine() {
        viewModelScope.launch {
            homeRepository.getMedicines().collect {
                _mediceen.value = it
            }
        }
    }

    fun insertDruglist(drugs: List<Drug>) =
        viewModelScope.launch(Dispatchers.IO) { dbRepository.insertDrug(drugs) }

    fun clearDruglist() =
        viewModelScope.launch(Dispatchers.IO) { dbRepository.clearDrug() }



}
