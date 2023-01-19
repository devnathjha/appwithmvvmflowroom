package com.app.testexample.db

import android.content.Context
import androidx.room.Room
import com.app.testexample.db.dao.DrugDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDao(databaseHelper: DatabaseHelper): DrugDao {
        return databaseHelper.getDrugDao()
    }

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        DatabaseHelper::class.java,
        "drugdatabase.db"
    ).build() // The reason we can construct a database for the repo

     // The reason we can implement a Dao for the database


}