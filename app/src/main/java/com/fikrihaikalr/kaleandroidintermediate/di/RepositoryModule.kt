package com.fikrihaikalr.kaleandroidintermediate.di

import com.fikrihaikalr.kaleandroidintermediate.data.repository.AuthRepository
import com.fikrihaikalr.kaleandroidintermediate.data.repository.AuthRepositoryImpl
import com.fikrihaikalr.kaleandroidintermediate.data.repository.SettingsRepository
import com.fikrihaikalr.kaleandroidintermediate.data.repository.SettingsRepositoryImpl
import com.fikrihaikalr.kaleandroidintermediate.data.repository.StoryRepository
import com.fikrihaikalr.kaleandroidintermediate.data.repository.StoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideStoryRepository(storyRepositoryImpl: StoryRepositoryImpl): StoryRepository

    @Binds
    abstract fun provideSettingRepository(settingRepositoryImpl: SettingsRepositoryImpl): SettingsRepository
}