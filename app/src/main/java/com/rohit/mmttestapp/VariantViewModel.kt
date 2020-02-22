package com.rohit.mmttestapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rohit.mmttestapp.pojo.VariantDbData
import com.rohit.mmttestapp.pojo.Variants

class VariantViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: VariantRepository =
        VariantRepository(application)
    private var allVariants: LiveData<List<VariantDbData>> = repository.getAllvariants()

    val variants: MutableLiveData<Variants> = repository.getVariants()
    val error: MutableLiveData<String> = repository.getError()

    fun insert(variantDbData: VariantDbData) {
        repository.insert(variantDbData)
    }

    fun deleteAllNotes() {
        repository.deleteAllvariants()
    }

    fun getAllNotes(): LiveData<List<VariantDbData>> {
        return allVariants
    }

    fun getAllProducts() {
        repository.getAllProducts()
    }
}