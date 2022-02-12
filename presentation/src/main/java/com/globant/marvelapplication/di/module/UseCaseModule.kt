package com.marvel.marvelapplication.di.module

import com.marvel.domain.repository.Repository
import com.marvel.domain.usecase.GetCharactersDetailsUseCase
import com.marvel.domain.usecase.GetCharactersUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun providesGetUserUseCase(repository: Repository): GetCharactersUseCase {
        return GetCharactersUseCase(repository)
    }

    @Provides
    fun providesGetCharactersDetailsUseCase(repository: Repository): GetCharactersDetailsUseCase {
        return GetCharactersDetailsUseCase(repository)
    }

}