package com.rohit.mmttestapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rohit.mmttestapp.R
import com.rohit.mmttestapp.pojo.Variations

class VariationAdapter(val variations:ArrayList<Variations>) : RecyclerView.Adapter<VariationAdapter.ProductHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.variation_item, parent, false)
        return ProductHolder(itemView)
    }
    var selectedId:String?=null

    fun setCheckedId( id:String?){
        selectedId = id
        notifyDataSetChanged()
    }
    fun getSelectedVId():String?{
        return selectedId
    }
    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val currentNote = variations[position]
        if(currentNote.isVeg==1){
            holder.veg.visibility = View.VISIBLE
        }else{
            holder.veg.visibility = View.GONE
        }
        holder.price.text = ""+currentNote.price
        holder.checkBox.text = currentNote.name
        if(currentNote.default==1 && selectedId.isNullOrEmpty()){
            holder.checkBox.isChecked = true
        }else {
           currentNote.id?.let {
               if(it.equals(selectedId)){
                   holder.checkBox.isChecked = true
               }
           }
        }

        holder.checkBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            setCheckedId(currentNote.id)
        }

        currentNote.disabled?.let {
            if(it){
                holder.parent.alpha = 0.2f
            }else
                holder.parent.alpha = 1f
        }

    }

    override fun getItemCount(): Int {
        return variations.size
    }



    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var veg: TextView = itemView.findViewById(R.id.veg)
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        var price:TextView=itemView.findViewById(R.id.price)
        var parent:LinearLayout=itemView.findViewById(R.id.parent)

    }
}