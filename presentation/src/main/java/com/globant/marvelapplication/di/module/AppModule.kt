package com.marvel.marvelapplication.di.module


import com.marvel.data.datasource.RemoteDataSource
import com.marvel.data.mapper.Mapper
import com.marvel.data.repository.DataRepositoryImpl
import com.marvel.domain.repository.Repository
import dagger.Module
import dagger.Provides


@Module
class AppModule {
    @Provides
    fun providesGetUserUseCase(
        remoteDataSource: RemoteDataSource,
        mapper: Mapper
    ): Repository {
        return DataRepositoryImpl(remoteDataSource, mapper)
    }

    @Provides
    fun providesMapper(): Mapper {
        return Mapper()
    }
}