package com.appsculture.climato.module.home

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(private val homeViewModel: HomeViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java!!)) {
            return homeViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}