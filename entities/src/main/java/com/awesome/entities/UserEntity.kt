package com.awesome.entities

data class UserEntity(
    val id:Int,
    val email: String,
    val login: String,
    var password: String,
)
