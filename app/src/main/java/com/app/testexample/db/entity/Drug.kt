package com.app.testexample.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "drugTable")
data class Drug(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val dose: String?,
    val strength: String?

)