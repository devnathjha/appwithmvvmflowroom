package com.app.trukker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.trukker.db.dao.DrugDao
import com.app.trukker.db.entity.Drug

@Database(entities = [Drug::class], version = 1, exportSchema = false)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun getDrugDao(): DrugDao

    companion object {
        // Volatile annotation means any change to this field
        // are immediately made visible to other threads.
        @Volatile
        private var INSTANCE: DatabaseHelper? = null

        private const val DB_NAME = "drugdatabase.db"

        fun getDatabase(context: Context): DatabaseHelper {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            // here synchronised used for blocking the other thread
            // from accessing another while in a specific code execution.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseHelper::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}