package com.rohit.mmttestapp.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rohit.mmttestapp.pojo.VariantDbData

@Dao
interface VariantDao {

    @Insert
    fun insert(variantDbData: VariantDbData)

    @Query("DELETE FROM variants_table")
    fun deleteAllvariants()

    @Query("SELECT * FROM variants_table ")
    fun getAllvariants(): LiveData<List<VariantDbData>>

    @Query("SELECT * FROM variants_table WHERE group_id = :groupId And variation_id=:variationId")
    fun getVariationByID(groupId: String,variationId:String): VariantDbData

}