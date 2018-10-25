package com.appsculture.climato.di.modules

import com.appsculture.climato.module.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

  @ContributesAndroidInjector
  abstract fun contributeHomeActivity(): HomeActivity
}