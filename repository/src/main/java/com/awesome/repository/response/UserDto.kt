package com.awesome.repository.response

import java.util.Date

data class UserDto(
    val id:Int?=null,
    var fullName: String? = null,
    val email: String? = null,
    val login: String? = null,
    val phone: String? = null,
    val website: String? = null,
    var lastRequestAt: Date? = null,
    var externalId: String? = null,
    var facebookId: String? = null,
    var twitterId: String? = null,
    var twitterDigitsId: String? = null,
    var imageId: Int? = null,
    var blobId: Int? = null,
    var tags: String? = null,
    var password: String? = null,
    var oldPassword: String? = null,
    val customData: String? = null,
)
