package com.awesome.chatify.di

import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.repos.ChatRepository
import com.awesome.entities.repos.MessagingRepository
import com.awesome.entities.repos.ServiceRepository
import com.awesome.entities.repos.SearchRepository
import com.awesome.network.chat.QuickBloxChatService
import com.awesome.repository.repos.auth.AuthRepositoryImpl
import com.awesome.repository.repos.chat.ChatRepositoryImpl
import com.awesome.repository.repos.messaging.MessagingRepositoryImpl
import com.awesome.repository.repos.service.ServiceRepositoryImpl
import com.awesome.repository.repos.search.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository

    @Binds
    abstract fun provideServiceRepository(serviceRepositoryImpl: ServiceRepositoryImpl) : ServiceRepository
    @Binds
    abstract fun provideSearchRepository(searchRepositoryImpl: SearchRepositoryImpl) : SearchRepository
    @Binds
    abstract fun provideChatRepository(chatService: ChatRepositoryImpl) : ChatRepository
    @Binds
    abstract fun provideMessagingRepository(messagingRepositoryImpl: MessagingRepositoryImpl) : MessagingRepository
}