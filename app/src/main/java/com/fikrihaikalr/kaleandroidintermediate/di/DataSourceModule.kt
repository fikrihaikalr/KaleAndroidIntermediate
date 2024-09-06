package com.fikrihaikalr.kaleandroidintermediate.di

import com.fikrihaikalr.kaleandroidintermediate.data.local.datasource.AuthLocalDataSource
import com.fikrihaikalr.kaleandroidintermediate.data.local.datasource.AuthLocalDataSourceImpl
import com.fikrihaikalr.kaleandroidintermediate.data.local.datasource.SettingsLocalDataSource
import com.fikrihaikalr.kaleandroidintermediate.data.local.datasource.SettingsLocalDataSourceImpl
import com.fikrihaikalr.kaleandroidintermediate.data.remote.datasource.AuthRemoteDataSource
import com.fikrihaikalr.kaleandroidintermediate.data.remote.datasource.AuthRemoteDataSourceImpl
import com.fikrihaikalr.kaleandroidintermediate.data.remote.datasource.StoryRemoteDataSource
import com.fikrihaikalr.kaleandroidintermediate.data.remote.datasource.StoryRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideAuthLocalDataSource(authLocalDataSourceImpl: AuthLocalDataSourceImpl): AuthLocalDataSource

    @Binds
    abstract fun provideAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    abstract fun provideStoryRemoteDataSource(storyRemoteDataSourceImpl: StoryRemoteDataSourceImpl): StoryRemoteDataSource

    @Binds
    abstract fun provideSettingLocalDataSource(settingLocalDataSourceImpl: SettingsLocalDataSourceImpl): SettingsLocalDataSource
}