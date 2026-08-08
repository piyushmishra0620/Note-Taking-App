package com.example.note.data.local.entities

import androidx.room.*

@Entity(tableName="Todos")
data class Todos(
    @PrimaryKey(autoGenerate=true)
    val id:Int=0,
    var todo:String,
    val createdAt:Long,
    var deadline:Long,
    var status:Float=0f
)
