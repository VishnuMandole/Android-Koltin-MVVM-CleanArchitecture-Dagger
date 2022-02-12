package com.marvel.data.datasource

import com.marvel.data.remote.api.MarvelApi
import com.marvel.data.remote.dto.CharacterDto

/* RemoteDataSourceImpl have methods fro get dat from remote source */
class RemoteDataSourceImpl(private val apiInterface: MarvelApi) : RemoteDataSource {
    override suspend fun getCharacters(): CharacterDto {
        return apiInterface.getCharacters(1)
    }

    override suspend fun getCharactersDetails(characterId: Int): CharacterDto {
        return apiInterface.getCharactersDetails(characterId)
    }
}