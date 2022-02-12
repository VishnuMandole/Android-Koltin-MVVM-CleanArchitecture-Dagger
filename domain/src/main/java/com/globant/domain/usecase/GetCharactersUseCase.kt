package com.marvel.domain.usecase

import com.marvel.domain.repository.Repository

// GetCharactersUseCase for get Characters list from repo
class GetCharactersUseCase(private val repository: Repository) {
    suspend fun getCharacters() = repository.getCharacters()
}