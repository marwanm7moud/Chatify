package com.awesome.repository.repos

import com.awesome.entities.User
import com.awesome.repository.response.UserDto

fun UserDto.toUserEntity() = User(
    id = id ?:0,
    email = email ?:"",
    password = password?:"",
    username = login ?:"",
    fullName = fullName?:"",
    avatarId = imageId ?: 0
)
fun List<UserDto>.toUserEntity() = map { it.toUserEntity() }