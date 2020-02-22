package com.rohit.mmttestapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rohit.mmttestapp.R
import com.rohit.mmttestapp.pojo.VariantGroups

class VariantAdapter(val context : Context) : RecyclerView.Adapter<VariantAdapter.ProductHolder>() {
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
        holder.variationRv.adapter = VariationAdapter(currentNote.variations)

        holder.addToCart.setOnClickListener {

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