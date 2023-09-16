package com.awesome.usecase.search

import com.awesome.entities.repos.SearchRepository
import javax.inject.Inject

class SearchUserByFullNameUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String) = searchRepository.searchUserByFullName(query)
}