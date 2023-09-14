package com.awesome.usecase.chat

import com.awesome.entities.User
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.repos.ChatRepository
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.usecase.utils.Validator
import javax.inject.Inject

class CreatePrivateChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    suspend operator fun invoke(userId:Int) = chatRepository.createPrivateChat(userId)

}