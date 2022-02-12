package com.marvel.marvelapplication.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marvel.domain.usecase.GetCharactersDetailsUseCase
import com.marvel.domain.usecase.GetCharactersUseCase

/*MainViewModelFactory return view model object instance*/
class CharacterListViewModelFactory(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharactersDetailsUseCase: GetCharactersDetailsUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterListViewModel::class.java)) {
            return CharacterListViewModel(getCharactersUseCase, getCharactersDetailsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}