package com.awesome.chatify.di

import com.awesome.network.auth.QuickBloxAuthService
import com.awesome.network.auth.QuickBloxQuickBloxAuthServiceImpl
import com.awesome.network.chat.QuickBloxChatService
import com.awesome.network.chat.QuickBloxChatServiceImpl
import com.awesome.network.service.QuickBloxService
import com.awesome.network.service.QuickBloxServiceImpl
import com.awesome.network.search.QuickBloxSearchService
import com.awesome.network.search.QuickBloxSearchServiceImpl
import com.quickblox.chat.QBRestChatService
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
    abstract fun provideService(quickBloxServiceImpl: QuickBloxServiceImpl):QuickBloxService

    @Binds
    abstract fun provideSearchService(quickBloxSearchServiceImpl: QuickBloxSearchServiceImpl): QuickBloxSearchService
    @Binds
    abstract fun provideChatService(quickBloxChatService: QuickBloxChatServiceImpl): QuickBloxChatService
}