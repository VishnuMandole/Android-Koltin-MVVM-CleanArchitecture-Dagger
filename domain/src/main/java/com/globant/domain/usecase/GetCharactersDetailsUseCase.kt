package com.marvel.domain.usecase

import com.marvel.domain.repository.Repository

// GetCharactersDetailsUseCase for get Characters Details from repo
class GetCharactersDetailsUseCase(private val repository: Repository) {
    suspend fun getCharactersDetails(characterId: Int) =
        repository.getCharactersDetails(characterId)
}