package com.awesome.chatify.di

import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.repos.ChatRepository
import com.awesome.entities.repos.SearchRepository
import com.awesome.repository.repos.auth.AuthRepositoryImpl
import com.awesome.repository.repos.chat.ChatRepositoryImpl
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
    abstract fun provideChatRepository(chatRepositoryImpl: ChatRepositoryImpl) : ChatRepository
    @Binds
    abstract fun provideSearchRepository(searchRepositoryImpl: SearchRepositoryImpl) : SearchRepository
}