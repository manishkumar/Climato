package com.appsculture.climato.di.modules

import com.appsculture.climato.module.detail.DetailActivity
import com.appsculture.climato.module.home.HomeActivity
import com.appsculture.climato.module.map.MapsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): DetailActivity

    @ContributesAndroidInjector
    abstract fun contributeMapActivity(): MapsActivity
}