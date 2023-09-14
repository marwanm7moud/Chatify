package com.awesome.usecase.service

import com.awesome.entities.repos.ServiceRepository
import javax.inject.Inject

class GetConnectionStateUseCase @Inject constructor(
    private val serviceRepository: ServiceRepository,
) {
    operator fun invoke() = serviceRepository.subscribeToConnectionState()
}