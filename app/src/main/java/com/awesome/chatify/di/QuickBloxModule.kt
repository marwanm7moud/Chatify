package com.awesome.chatify.di

import com.quickblox.auth.session.QBSessionManager
import com.quickblox.auth.session.QBSessionParameters
import com.quickblox.chat.QBChatService
import com.quickblox.chat.QBRestChatService
import com.quickblox.chat.QBRoster
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class QuickBloxModule {

    @Provides
    @Singleton
    fun provideSessionParameters(): QBSessionParameters? {
        return QBSessionManager.getInstance().getSessionParameters();
    }

    @Provides
    @Singleton
    fun provideQBChatService(): QBChatService {
        return QBChatService.getInstance()
    }

    @Provides
    @Singleton
    fun provideQbChatRoster() : QBRoster = QBChatService.getInstance().roster

}