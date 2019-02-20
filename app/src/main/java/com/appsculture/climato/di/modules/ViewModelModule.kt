package com.appsculture.climato.di.modules

import android.arch.lifecycle.ViewModelProvider
import com.appsculture.climato.module.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ViewModelModule() {

    @Provides
    @Singleton
    fun provideHomeViewModelFactory(factory: HomeViewModelFactory): ViewModelProvider.Factory =
        factory

}