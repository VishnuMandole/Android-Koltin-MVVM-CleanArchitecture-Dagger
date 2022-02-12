package com.marvel.data.mapper

import com.marvel.data.remote.dto.CharacterDto
import com.marvel.domain.model.Characters

/* Mapper fro convert dto obj nin domain layer character obj  */
class Mapper {
    fun mapToDomain(input: CharacterDto): List<Characters> {
        val headlines: MutableList<Characters> = mutableListOf()
        input.data?.results?.map {
            headlines.add(
                Characters(
                    it.id,
                    it.thumbnail.path + "." + it.thumbnail.extension.replace(
                        "http://",
                        "https://"
                    ),
                    it.name,
                    it.description,
                    it.modified
                )
            )
        }
        return headlines
    }
}