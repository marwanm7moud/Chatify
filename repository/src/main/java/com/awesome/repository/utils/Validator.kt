package com.awesome.repository.utils

import com.awesome.entities.utils.ValidationException


object Validator {
    operator fun invoke(username: String?, password: String?) {
        val errors = mutableListOf<String>()
        username.isUsernameValid()?.let { errors.add(it) }
        password.isPasswordValid()?.let { errors.add(it) }
        if (errors.isNotEmpty()) throw ValidationException(errors)
    }

    operator fun invoke(username: String?, password: String?, email: String?, fullName: String?) {
        val errors = mutableListOf<String>()
        username.isUsernameValid()?.let { errors.add(it) }
        password.isPasswordValid()?.let { errors.add(it) }
        email.isEmailValid()?.let { errors.add(it) }
        fullName.isFullNameValid()?.let { errors.add(it) }
        if (errors.isNotEmpty()) throw ValidationException(errors)
    }
}

private fun String?.isUsernameValid(): String? {
    return if (isNullOrEmpty()) "The Username shouldn't be empty"
    else if (contains(regex = "[!@#$%&*()_+=|<>?{}\\[\\]~-]".toRegex())) "The Username shouldn't have special characters"
    else if (length < 8) "Username Minimum length is 6"
    else null
}

private fun String?.isEmailValid(): String? {
    return if (isNullOrEmpty()) "The Email shouldn't be empty"
    else if (contains(regex = "[!#$%&*()_+=|<>?{}\\[\\]~-]".toRegex())) "The Email shouldn't have special characters"
    else if (!contains("@")) "Please enter a valid email"
    else null
}

private fun String?.isPasswordValid(): String? {
    return if (isNullOrEmpty()) "The Password shouldn't be empty"
    else if (length < 8) "Password Minimum length is 8"
    else null
}

private fun String?.isFullNameValid(): String? {
    return if (isNullOrEmpty()) "The FullName shouldn't be empty"
    else if (contains(regex = "[!#@$%&*()_+=|<>?{}\\[\\]~-]".toRegex())) "The fullName shouldn't have special characters"
    else if (length < 6) "FullName Minimum length is 6"
    else null
}