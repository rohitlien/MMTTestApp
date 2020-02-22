package com.rohit.mmttestapp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VariantViewModelFactory(
    private val application: Application
): ViewModelProvider.NewInstanceFactory() {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T = VariantViewModel( application) as T
}