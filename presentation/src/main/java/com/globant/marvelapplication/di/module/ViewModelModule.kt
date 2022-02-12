package com.marvel.marvelapplication.di.module

import com.marvel.domain.usecase.GetCharactersDetailsUseCase
import com.marvel.domain.usecase.GetCharactersUseCase
import com.marvel.marvelapplication.characters.CharacterListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {


    @Provides
    fun providesGetUserUseCase(
        getCharactersUseCase: GetCharactersUseCase,
        getCharactersDetailsUseCase: GetCharactersDetailsUseCase
    ): CharacterListViewModelFactory {
        return CharacterListViewModelFactory(getCharactersUseCase, getCharactersDetailsUseCase)
    }


}