package com.app.trukker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.trukker.data.model.Medicine
import com.app.trukker.data.repository.HomeRepository
import com.app.trukker.db.DatabaseHelper
import com.app.trukker.db.dao.DrugDao
import com.app.trukker.db.entity.Drug
import com.app.trukker.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    application: Application
) : AndroidViewModel(application){

    private val _mediceen = MutableStateFlow<State<Medicine>>(State.LoadingState)

    //private val _drugList =  MutableLiveData<List<Drug>>()
    val drugList:LiveData<List<Drug>>



    val medicine1:StateFlow<State<Medicine>>
        get() = _mediceen

    init {
        // if Data is blank in db then fetch from api and store it in db.
        DatabaseHelper.getDatabase(application).getDrugDao()
        drugList = homeRepository.getAllDrugs()
        if(drugList.value?.isEmpty() == true)
            fetchMedicine()
    }



    fun fetchMedicine() {
        viewModelScope.launch {
            homeRepository.getMedicines().collect {
                _mediceen.value = it
            }
        }
    }

    fun insertDruglist(drugs: List<Drug>) =
        viewModelScope.launch(Dispatchers.IO) { homeRepository.insertDrug(drugs) }
}
