package com.rohit.mmttestapp.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "variants_table")
data class VariantDbData(

    var group_id: String,

    var variation_id: String,

    var count:Int

) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}