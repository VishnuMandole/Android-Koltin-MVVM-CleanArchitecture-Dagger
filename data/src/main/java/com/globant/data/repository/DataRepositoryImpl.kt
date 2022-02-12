package com.marvel.data.repository

import com.marvel.data.datasource.RemoteDataSource
import com.marvel.data.mapper.Mapper
import com.marvel.domain.model.Characters
import com.marvel.domain.repository.Repository
import com.marvel.domain.util.Resource

/* DataRepositoryImpl have methods fro get dat from remote source bind to result obj and return success or fail*/
class DataRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val mapper: Mapper
) : Repository {
    // get Characters list from remote sourc
    override suspend fun getCharacters(): Resource<List<Characters>> {
        return try {
            Resource.success(mapper.mapToDomain(remoteDataSource.getCharacters()))
        } catch (e: Exception) {
            print(e.toString())
            Resource.error("$e", null)
        }
    }

    // get Characters Details from remote
    override suspend fun getCharactersDetails(characterId: Int): Resource<Characters> {
        return try {
            val result = remoteDataSource.getCharactersDetails(characterId)
            Resource.success(mapper.mapToDomain(result)[0])
        } catch (e: Exception) {
            print(e.toString())
            Resource.error("$e", null)
        }
    }
}