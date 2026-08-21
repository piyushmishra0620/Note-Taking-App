package com.example.note.models

data class Todos(
    val id:Int,
    var todo:String,
    val createdAt:Long,
    var deadline:Long,
    var status:Float=0f
)
