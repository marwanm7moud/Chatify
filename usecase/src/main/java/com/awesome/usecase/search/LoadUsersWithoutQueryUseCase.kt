package com.awesome.usecase.search

import com.awesome.entities.repos.SearchRepository
import javax.inject.Inject

class LoadUsersWithoutQueryUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke() = searchRepository.loadUsersWithoutQuery()
}