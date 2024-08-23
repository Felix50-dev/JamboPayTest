package com.example.jambopaytest.di

import com.example.jambopaytest.data.UserRepositoryImpl
import com.example.jambopaytest.data.remote.DummyApi
import com.example.jambopaytest.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDummyApi(): DummyApi{
        return DummyApi.create()
    }

    @Provides
    @Singleton
    fun provideAvatarRepository(api: DummyApi): UserRepository {
        return UserRepositoryImpl(api)
    }
}