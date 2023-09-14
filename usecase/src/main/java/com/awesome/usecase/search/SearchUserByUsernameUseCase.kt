package com.awesome.usecase.search

import com.awesome.entities.User
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.repos.SearchRepository
import com.awesome.entities.repos.model.UserSignUpRequest
import javax.inject.Inject

class SearchUserByUsernameUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String) = searchRepository.searchUserByLoginOrFullName(query)
}