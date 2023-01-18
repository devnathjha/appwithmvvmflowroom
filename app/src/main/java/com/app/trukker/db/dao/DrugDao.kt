package com.app.trukker.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.trukker.db.entity.Drug
import com.app.trukker.utils.State
import kotlinx.coroutines.flow.Flow

@Dao
interface DrugDao {

    // adds a new entry to our database.
    // if some data is same/conflict, it'll be replace with new data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(drugs: List<Drug>)

    // deletes an Drug
    @Delete
    suspend fun deleteEvent(drug: Drug)

    // updates an Drug.
    @Update
    suspend fun updateEvent(drug: Drug)

    // read all the events from eventTable
    // and arrange events in ascending order
    // of their ids
    @Query("Select * from drugTable order by id ASC")
    fun getAllDrugs():LiveData<List<Drug>>
    // why not use suspend ? because Room does not support LiveData with suspended functions.
    // LiveData already works on a background thread and should be used directly without using coroutines

    /*// delete all events
    @Query("DELETE FROM drugTable")
    suspend fun clearDrug()

    //you can use this too, to delete an Drug by id.
    @Query("DELETE FROM drugTable WHERE id = :id")
    suspend fun deleteDrugById(id: Int)*/

}