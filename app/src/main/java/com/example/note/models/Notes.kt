package com.example.note.models

data class Notes(
    var id:Int,
    var title:String?,
    var content:String,
    val createdAt:Long,
    var lastUpdated:Long,
    var isPinned:Boolean
)
