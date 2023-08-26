package com.awesome.chatify.di

import com.awesome.network.auth.QuickBloxAuthService
import com.awesome.network.auth.QuickBloxQuickBloxAuthServiceImpl
import com.awesome.network.chat.QuickBloxChatService
import com.awesome.network.chat.QuickBloxChatServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServicesModule {

    @Binds
    abstract fun provideAuthService(quickBloxAuthServiceImpl: QuickBloxQuickBloxAuthServiceImpl):QuickBloxAuthService

    @Binds
    abstract fun provideChatService(quickBloxChatServiceImpl: QuickBloxChatServiceImpl):QuickBloxChatService

}