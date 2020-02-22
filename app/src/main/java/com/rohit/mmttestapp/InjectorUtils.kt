package com.rohit.mmttestapp

import android.app.Application

object InjectorUtils {

    fun provideProfileViewModelFactory(application: Application): VariantViewModelFactory {
        return VariantViewModelFactory(application)
    }
}