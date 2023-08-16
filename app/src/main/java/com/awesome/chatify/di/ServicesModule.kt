package com.awesome.chatify.di

import com.awesome.network.QuickBloxDataSource
import com.awesome.network.auth.AuthService
import com.awesome.network.auth.QuickBloxAuthServiceImpl
import com.awesome.repository.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServicesModule {

    @Binds
    abstract fun provideAuthService(quickBloxAuthServiceImpl: QuickBloxAuthServiceImpl):AuthService

    @Binds
    abstract fun provideRemoteDataSource(quickBloxDataSource: QuickBloxDataSource):RemoteDataSource
}