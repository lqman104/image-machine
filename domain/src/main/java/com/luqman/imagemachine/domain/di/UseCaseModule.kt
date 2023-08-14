package com.luqman.imagemachine.domain.di

import com.luqman.imagemachine.domain.usecase.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCase(): UseCase {
        return UseCase()
    }

}