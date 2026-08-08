package com.example.note.data.local.entities

import androidx.room.*

@Entity(tableName="Notes")
data class Notes(
    @PrimaryKey(autoGenerate=true)
    var id:Int=0,
    var title:String?,
    var content:String,
    val createdAt:Long,
    var lastUpdated:Long,
    var isPinned:Boolean
)
