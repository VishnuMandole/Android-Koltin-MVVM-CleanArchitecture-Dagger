package com.marvel.data.remote.api

import com.marvel.data.remote.dto.CharacterDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {
    @GET("/v1/public/characters")
    suspend fun getCharacters(@Query("offset") offset: Int): CharacterDto

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharactersDetails(@Path("characterId") characterId: Int): CharacterDto
}