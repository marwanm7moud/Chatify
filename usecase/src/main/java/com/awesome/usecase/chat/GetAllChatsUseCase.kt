package com.awesome.usecase.chat

import com.awesome.entities.User
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.repos.ChatRepository
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.usecase.utils.Validator
import javax.inject.Inject

class GetAllChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    operator fun invoke() = chatRepository.getAllChats()

}