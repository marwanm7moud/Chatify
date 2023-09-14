package com.awesome.usecase.service

import com.awesome.entities.repos.ServiceRepository
import javax.inject.Inject

class ManageChatServerConnectionUseCase @Inject constructor(
    private val serviceRepository: ServiceRepository,
) {
    suspend fun connectToChatServer() = serviceRepository.connectToChatServer()
    fun disconnectToChatServer() = serviceRepository.disconnectFromChatServer()
}