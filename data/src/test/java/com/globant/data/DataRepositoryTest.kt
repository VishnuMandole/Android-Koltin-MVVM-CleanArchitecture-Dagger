package com.marvel.data

import com.marvel.data.datasource.RemoteDataSource
import com.marvel.data.mapper.Mapper
import com.marvel.data.remote.dto.CharacterDto
import com.marvel.data.repository.DataRepositoryImpl
import com.marvel.domain.util.Resource
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class DataRepositoryTest {

    @Mock
    lateinit var remoteDataSource: RemoteDataSource
    private lateinit var dataRepositoryImpl: DataRepositoryImpl

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        dataRepositoryImpl = DataRepositoryImpl(remoteDataSource, Mapper())
    }

    @Test
    fun readCharacterFromApi() = runBlocking {
        val reader = MockResponseFileReader("success_response.json").content
        val characterDto = Gson().fromJson(reader, CharacterDto::class.java)
        val list = Resource.success(Mapper().mapToDomain(characterDto))
        whenever(remoteDataSource.getCharacters()).thenReturn(characterDto)
        val result = dataRepositoryImpl.getCharacters()
        // ...then the result should be the expected one.
        assertThat(result, Is.`is`(list))
    }

    @Test
    fun readCharacterDetailsFromApi() = runBlocking {
        val reader = MockResponseFileReader("success_response.json").content
        val characterDto = Gson().fromJson(reader, CharacterDto::class.java)
        val list = Resource.success(Mapper().mapToDomain(characterDto))
        whenever(remoteDataSource.getCharactersDetails(1009144)).thenReturn(characterDto)
        val result = dataRepositoryImpl.getCharactersDetails(1009144)
        // ...then the result should be the expected one.
        assertThat(result.data?.id, Is.`is`(list.data?.get(0)?.id))
    }

    @Test
    fun `When getAllLaunches then getCharacters invoked`() {
        runBlockingTest {
            // When
            whenever(remoteDataSource.getCharacters()).thenReturn(mock())
            dataRepositoryImpl.getCharacters()
            // Then
            verify(remoteDataSource, times(1)).getCharacters()
        }
    }

}