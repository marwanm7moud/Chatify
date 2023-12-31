package com.awesome.chatify.di

import com.awesome.local.DataStoreDataSource
import com.awesome.network.QuickBloxDataSource
import com.awesome.repository.LocalDataSource
import com.awesome.repository.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourcesModule {

    @Binds
    abstract fun provideRemoteDataSource(quickBloxDataSource: QuickBloxDataSource): RemoteDataSource

    @Binds
    abstract fun provideLocalDataSource(dataStoreDataSource: DataStoreDataSource): LocalDataSource
}