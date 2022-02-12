package com.marvel.marvelapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marvel.data.MockResponseFileReader
import com.marvel.data.mapper.Mapper
import com.marvel.data.remote.dto.CharacterDto
import com.marvel.domain.usecase.GetCharactersDetailsUseCase
import com.marvel.domain.usecase.GetCharactersUseCase
import com.marvel.domain.util.Resource
import com.marvel.marvelapplication.characters.CharacterListViewModel
import com.marvel.marvelapplication.utils.getOrAwaitValue
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @Mock
    private lateinit var getCharactersDetailsUseCase: GetCharactersDetailsUseCase
    private lateinit var viewModel: CharacterListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = CharacterListViewModel(getCharactersUseCase, getCharactersDetailsUseCase)
    }

    @Test
    fun testCharactersList() {
        runBlocking {
            val reader = MockResponseFileReader("success_response.json").content
            val characterDto = Gson().fromJson(reader, CharacterDto::class.java)
            val list = Resource.success(Mapper().mapToDomain(characterDto))
            whenever(getCharactersUseCase.getCharacters()).thenReturn(list)
            viewModel.getCharactersList()
            val result = viewModel.charactersList.getOrAwaitValue()
            MatcherAssert.assertThat(result.data, Is.`is`(list.data))
        }
    }

    @Test
    fun testCharactersDetails() {
        runBlocking {
            val reader = MockResponseFileReader("success_response.json").content
            val characterDto = Gson().fromJson(reader, CharacterDto::class.java)
            val resp = Resource.success(Mapper().mapToDomain(characterDto)[0])
            whenever(getCharactersDetailsUseCase.getCharactersDetails(1009144)).thenReturn(resp)
            val result = viewModel.getCharactersDetails(1009144).getOrAwaitValue()
            MatcherAssert.assertThat(result.data, Is.`is`(resp.data))
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}
