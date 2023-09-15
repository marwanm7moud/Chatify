package com.awesome.usecase.messaging

import com.awesome.entities.Message
import com.awesome.entities.repos.MessagingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendSystemMessage @Inject constructor(
    private val messagingRepository: MessagingRepository
) {
    operator fun invoke(chatId:String , recipientId :Int) = messagingRepository.sendSystemMessage(chatId , recipientId)
}