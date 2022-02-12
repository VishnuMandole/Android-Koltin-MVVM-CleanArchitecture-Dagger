package com.marvel.domain.repository

import com.marvel.domain.model.Characters
import com.marvel.domain.util.Resource

interface Repository {
    suspend fun getCharacters(): Resource<List<Characters>>
    suspend fun getCharactersDetails(characterId: Int): Resource<Characters>
}