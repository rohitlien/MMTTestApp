package com.rohit.mmttestapp

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rohit.mmttestapp.daos.VariantDao
import com.rohit.mmttestapp.database.VariantDatabase
import com.rohit.mmttestapp.pojo.ProductsData
import com.rohit.mmttestapp.pojo.VariantDbData
import com.rohit.mmttestapp.pojo.Variants
import com.rohit.mmttestapp.retrofit.AppConstants
import com.rohit.mmttestapp.retrofit.RetrofitFactory
import com.rohit.mmttestapp.retrofit.RetrofitServices
import retrofit2.Call
import retrofit2.Callback

class VariantRepository(application: Application) {

    private var variantDao: VariantDao

    private var allvariants: LiveData<List<VariantDbData>>

    private var variants: MutableLiveData<Variants> = MutableLiveData()
    fun getVariants(): MutableLiveData<Variants> = variants

    private var error: MutableLiveData<String> = MutableLiveData()
    fun getError(): MutableLiveData<String> = error

    private var productsData: ProductsData? = null

    init {
        val database: VariantDatabase = VariantDatabase.getInstance(
            application.applicationContext
        )!!
        variantDao = database.variantDao()
        allvariants = variantDao.getAllvariants()
    }

    fun insert(variantDbData: VariantDbData) {
        InsertVariantAsyncTask(variantDao).execute(variantDbData)
    }

    fun deleteAllvariants() {
        DeleteAllVariantsAsyncTask(
            variantDao
        ).execute()
    }

    fun getAllvariants(): LiveData<List<VariantDbData>> {
        return allvariants
    }

    fun getAllProducts() {
        val service = RetrofitFactory.getRetrofit().create(RetrofitServices::class.java)
        val bareUrl = AppConstants.BASE_URL

        val call = service.getAllProducts(bareUrl)
        call.enqueue(object : Callback<ProductsData> {
            override fun onFailure(call: Call<ProductsData>, t: Throwable) {
                error.postValue("Error")
            }

            override fun onResponse(
                call: Call<ProductsData>,
                response: retrofit2.Response<ProductsData>
            ) {
                productsData = response.body()
                GetAllSorteddProduct(variants, variantDao).execute(productsData)

            }

        })
    }

    fun addDataToDb(data: VariantDbData) {

        AddDataToDbVariantAsyncTask(variantDao).execute(data)

    }

    fun decrementData(data: VariantDbData) {
//        try {
//            val localVariantData = variantDao.getVariationByID(data.group_id, data.variation_id)
//            if (localVariantData?.group_id != null) {
//                localVariantData.count = localVariantData.count - 1
//                UpdateVariantAsyncTask(variantDao).execute(localVariantData)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
        DecrementDbVariantAsyncTask(variantDao).execute(data)
    }


    private class DecrementDbVariantAsyncTask(variantDao: VariantDao) :
        AsyncTask<VariantDbData, Unit, Unit>() {
        val variantDao = variantDao

        override fun doInBackground(vararg p0: VariantDbData?) {
            val data = p0[0]!!
            val localVariantData = variantDao.getVariationByID(data.group_id, data.variation_id)
            if (localVariantData?.group_id != null) {
                localVariantData.count = localVariantData.count - 1
                variantDao.update(localVariantData)
            }
        }
    }


    private class AddDataToDbVariantAsyncTask(variantDao: VariantDao) :
        AsyncTask<VariantDbData, Unit, Unit>() {
        val variantDao = variantDao

        override fun doInBackground(vararg p0: VariantDbData?) {
            val data = p0[0]!!
            val localVariantData = variantDao.getVariationByID(data.group_id, data.variation_id)
            if (localVariantData?.group_id == null) {
                variantDao.insert(data)
            } else {
                localVariantData.count = localVariantData.count + 1
                variantDao.update(localVariantData)
            }
        }
    }

    private class InsertVariantAsyncTask(variantDao: VariantDao) :
        AsyncTask<VariantDbData, Unit, Unit>() {
        val variantDao = variantDao

        override fun doInBackground(vararg p0: VariantDbData?) {
            variantDao.insert(p0[0]!!)
        }
    }

    private class UpdateVariantAsyncTask(variantDao: VariantDao) :
        AsyncTask<VariantDbData, Unit, Unit>() {
        val variantDao = variantDao

        override fun doInBackground(vararg p0: VariantDbData?) {
            variantDao.update(p0[0]!!)
        }
    }

    private class GetAllSorteddProduct(
        val variants: MutableLiveData<Variants>,
        val variantDao: VariantDao
    ) :
        AsyncTask<ProductsData, Unit, Unit>() {
        private var productsData: ProductsData? = null

        override fun doInBackground(vararg p0: ProductsData?) {
            productsData = p0[0]!!
            val excludeArray = productsData!!.variants.exclude_list
            for (i in excludeArray) {
                for (j in i) {
                    disableVariation(productsData!!, j.group_id, j.variation_id)

                }
            }
        }

        private fun disableVariation(
            productsData: ProductsData,
            groupId: String?,
            variationId: String?
        ) {
            for (i in productsData.variants.variant_groups) {
                if (i.group_id == groupId) {
                    for (j in i.variations) {
                        val localDbData = variantDao.getVariationByID(i.group_id!!, j.id!!)

                        if (localDbData != null && localDbData.count != null) {
                            j.count = localDbData.count
                        }
                        if (j.id.equals(variationId))
                            j.disabled = true
                    }
                }
            }
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            variants.postValue(productsData?.variants)
        }


    }

    private class DeleteAllVariantsAsyncTask(val variantDao: VariantDao) :
        AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit?) {
            variantDao.deleteAllvariants()
        }
    }

}