package com.rohit.mmttestapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rohit.mmttestapp.R
import com.rohit.mmttestapp.callBacks.AddProductListener
import com.rohit.mmttestapp.pojo.VariantDbData
import com.rohit.mmttestapp.pojo.VariantGroups

class VariantAdapter(val context : Context,val listener: AddProductListener) : RecyclerView.Adapter<VariantAdapter.ProductHolder>() {
    private var variantGroupData: List<VariantGroups> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return ProductHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val currentNote = variantGroupData[position]
        holder.textViewTitle.text = currentNote.name
        holder.variationRv.layoutManager = LinearLayoutManager(context)
        val adapter = VariationAdapter(context,currentNote.variations,listener)
        adapter.groupId = currentNote.group_id
        holder.variationRv.adapter = adapter

        holder.addToCart.setOnClickListener {
            val grpId = currentNote.group_id
            val variation = adapter.getSelectedVId()
            if(variation?.count != null && variation.inStock!=null ) {
                if( variation.inStock!!>variation.count!!) {
                    if (!grpId.isNullOrEmpty() && !variation.id.isNullOrEmpty()) {
                        adapter.updateAdapter(variation.id!!)
                        listener.onAdd(VariantDbData(grpId, variation.id!!, 1))
                    }
                }else{
                    Toast.makeText(context,"Not in stock!",Toast.LENGTH_SHORT).show()
                }
            }else if(variation?.count == null && variation?.inStock!=null){
                if( variation.inStock!!>0) {
                    if (!grpId.isNullOrEmpty() && !variation.id.isNullOrEmpty()) {
                        adapter.updateAdapter(variation.id!!)
                        listener.onAdd(VariantDbData(grpId, variation.id!!, 1))
                    }
                }else{
                    Toast.makeText(context,"Not in stock!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return variantGroupData.size
    }

    fun setVariants(variantDbData: List<VariantGroups>) {
        this.variantGroupData = variantDbData
        notifyDataSetChanged()
    }

    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        var variationRv: RecyclerView = itemView.findViewById(R.id.variationsRv)
        var addToCart:TextView = itemView.findViewById(R.id.addToCart)
    }
}