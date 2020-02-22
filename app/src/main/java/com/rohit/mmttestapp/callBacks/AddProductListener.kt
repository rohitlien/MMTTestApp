package com.rohit.mmttestapp.callBacks

import com.rohit.mmttestapp.pojo.VariantDbData
import com.rohit.mmttestapp.pojo.VariantGroups

interface AddProductListener {
    fun onAdd( data : VariantDbData)
    fun onDelete(data : VariantDbData)
}