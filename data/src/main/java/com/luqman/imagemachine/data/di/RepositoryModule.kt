package com.luqman.imagemachine.data.di

import com.luqman.imagemachine.data.repository.DataRepository
import com.luqman.imagemachine.data.repository.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideProvinceRepository(): DataSource = DataRepository()
}