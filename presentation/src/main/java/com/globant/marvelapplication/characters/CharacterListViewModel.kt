package com.marvel.marvelapplication.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.domain.model.Characters
import com.marvel.domain.usecase.GetCharactersDetailsUseCase
import com.marvel.domain.usecase.GetCharactersUseCase
import com.marvel.domain.util.Resource
import kotlinx.coroutines.launch

/* MainViewModel to get data from use cases*/
class CharacterListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharactersDetailsUseCase: GetCharactersDetailsUseCase
) : ViewModel() {

    private val _charactersList = MutableLiveData<Resource<List<Characters>>>()
    val charactersList: LiveData<Resource<List<Characters>>> get() = _charactersList

    // fetch characters list from GetCharactersUseCase
    fun getCharactersList() {
        viewModelScope.launch {
            _charactersList.postValue(Resource.loading(null))
            try {
                val usersFromApi = getCharactersUseCase.getCharacters()
                _charactersList.postValue(usersFromApi)
            } catch (e: Exception) {
                _charactersList.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    // fetch characters details from GetCharactersDetailsUseCase
    fun getCharactersDetails(characterId: Int): LiveData<Resource<Characters>> {
        val character = MutableLiveData<Resource<Characters>>()
        viewModelScope.launch {
            character.postValue(Resource.loading(null))
            try {
                val usersFromApi = getCharactersDetailsUseCase.getCharactersDetails(characterId)
                character.postValue(usersFromApi)
            } catch (e: Exception) {
                character.postValue(Resource.error(e.toString(), null))
            }
        }
        return character
    }
}