package com.marvel.data.datasource

import com.marvel.data.remote.dto.CharacterDto

interface RemoteDataSource {
    suspend fun getCharacters(): CharacterDto
    suspend fun getCharactersDetails(characterId: Int): CharacterDto
}