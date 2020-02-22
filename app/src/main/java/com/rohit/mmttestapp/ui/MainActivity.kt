package com.rohit.mmttestapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohit.mmttestapp.InjectorUtils
import com.rohit.mmttestapp.adapters.VariantAdapter
import com.rohit.mmttestapp.VariantViewModel
import com.rohit.mmttestapp.R
import com.rohit.mmttestapp.callBacks.AddProductListener
import com.rohit.mmttestapp.pojo.VariantDbData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),AddProductListener {

    private lateinit var variantViewModel: VariantViewModel
    private val adapter = VariantAdapter(this,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter

        variantViewModel= ViewModelProviders.of(this,InjectorUtils.provideProfileViewModelFactory(application))
            .get(VariantViewModel::class.java)

        variantViewModel.getAllProducts()

        variantViewModel.variants.observe(this, Observer {
            progressLl.visibility = View.GONE
            adapter.setVariants(it.variant_groups)
        })

        variantViewModel.error.observe(this, Observer {
            if(!it.isNullOrEmpty()){
                progressLl.visibility = View.GONE
                noItemLl.visibility = View.VISIBLE
            }
        })
    }

    override fun onAdd(data: VariantDbData) {

    }

    override fun onDelete(data: VariantDbData) {
    }

}
