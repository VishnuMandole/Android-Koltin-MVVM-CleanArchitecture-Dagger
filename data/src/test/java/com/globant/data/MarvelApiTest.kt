package com.marvel.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marvel.data.datasource.RemoteDataSourceImpl
import com.marvel.data.remote.api.MarvelApi
import com.marvel.data.remote.dto.CharacterDto
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class MarvelApiTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private lateinit var remoteDataSourceImpl: RemoteDataSourceImpl
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val v = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build().create(MarvelApi::class.java)
        remoteDataSourceImpl = RemoteDataSourceImpl(v)
    }

    @Test
    fun testApiSuccess() {
        runBlocking {
            val mockedResponse = MockResponseFileReader("success_response.json").content
            val characterDto = Gson().fromJson(mockedResponse, CharacterDto::class.java)
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(mockedResponse)
            )
            val response = remoteDataSourceImpl.getCharacters()
            assertEquals(characterDto, response)
        }
    }

    @Test
    fun `read sample success json file`() {
        val reader = MockResponseFileReader("success_response.json")
        assertNotNull(reader.content)
    }

    @Test
    fun `fetch details and check response success returned`() {
        runBlocking {
            val mockedResponse = MockResponseFileReader("success_response.json").content
            val characterDto = Gson().fromJson(mockedResponse, CharacterDto::class.java)
            // Assign
            val response = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockResponseFileReader("success_response.json").content)
            mockWebServer.enqueue(response)
            val actualResponse: CharacterDto = remoteDataSourceImpl.getCharacters()
            // Assert
            assertEquals(characterDto.status, actualResponse.status)
        }
    }

    @Test
    fun `fetch details for failed response 400 returned`() {
        runBlocking {
            // Assign
            val response = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody(MockResponseFileReader("success_response.json").content)
            mockWebServer.enqueue(response)
            try {
                remoteDataSourceImpl.getCharacters()
            } catch (he: HttpException) {
                he.code()
                assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, he.code())
            } catch (e: Exception) {
                e.message
            }
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}