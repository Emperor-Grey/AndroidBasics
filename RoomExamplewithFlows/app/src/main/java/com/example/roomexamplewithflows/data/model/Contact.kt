package com.example.roomexamplewithflows.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roomexamplewithflows.utils.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String = "",
    var email: String = "",
)
