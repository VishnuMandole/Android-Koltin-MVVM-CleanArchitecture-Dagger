package com.marvel.marvelapplication.di

import android.app.Application
import com.marvel.marvelapplication.characters.CharacterListActivity
import com.marvel.marvelapplication.characters.details.CharacterDetailsFragment
import com.marvel.marvelapplication.characters.list.CharacterListFragment
import com.marvel.marvelapplication.di.module.AppModule
import com.marvel.marvelapplication.di.module.NetworkModule
import com.marvel.marvelapplication.di.module.UseCaseModule
import com.marvel.marvelapplication.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, ViewModelModule::class, UseCaseModule::class])
interface ApplicationComponent {


    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun application(application: Application): Builder
    }

    fun inject(mainFragment: CharacterListFragment)
    fun inject(mainFragment: CharacterDetailsFragment)
    fun inject(mainFragment: CharacterListActivity)

}