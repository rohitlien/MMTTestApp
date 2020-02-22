package com.rohit.mmttestapp.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "variants_table")
data class VariantDbData(

    var title: String,

    var description: String

) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}