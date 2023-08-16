package com.awesome.repository.model.request

data class UserSignUpRequest(
    val fullName:String,
    val username:String,
    val email:String,
    val password:String,
)
