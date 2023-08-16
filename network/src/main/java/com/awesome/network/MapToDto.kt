package com.awesome.network

import com.awesome.repository.model.response.UserDto
import com.quickblox.users.model.QBUser

fun QBUser.toUserDto() = UserDto(
    fullName = fullName,
    email = email,
    login = login,
    phone = phone,
    website = website,
    lastRequestAt = lastRequestAt,
    externalId = externalId,
    facebookId = facebookId,
    twitterId = twitterId,
    twitterDigitsId = twitterDigitsId
)
