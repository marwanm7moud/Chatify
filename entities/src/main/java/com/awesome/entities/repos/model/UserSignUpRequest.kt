package com.awesome.entities.repos.model

data class UserSignUpRequest(
    val fullName:String,
    val username:String,
    val email:String,
    val password:String,
)
