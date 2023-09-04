package com.awesome.repository.repos

import com.awesome.entities.UserEntity
import com.awesome.repository.response.UserDto

fun UserDto.toUserEntity() = UserEntity(
    id = id ?:0,
    email = email ?:"",
    password = password?:"",
    username = login ?:"",
    fullName = fullName?:"",
)
fun List<UserDto>.toUserEntity() = map { it.toUserEntity() }