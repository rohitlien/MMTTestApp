package com.rohit.mmttestapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.rohit.mmttestapp.R
import com.rohit.mmttestapp.callBacks.AddProductListener
import com.rohit.mmttestapp.pojo.VariantDbData
import com.rohit.mmttestapp.pojo.Variations

class VariationAdapter(val context : Context, val variations: ArrayList<Variations>, val listener:AddProductListener) :
    RecyclerView.Adapter<VariationAdapter.ProductHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.variation_item, parent, false)
        return ProductHolder(itemView)
    }

    var selectedId: String? = null
    var groupId: String? = null

    fun setCheckedId(id: String?) {
        selectedId = id
        notifyDataSetChanged()
    }

    fun getSelectedVId(): Variations? {
        for(i in variations){
            if(i.id==selectedId){
                return i
            }
        }
        return null
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val currentNote = variations[position]
        if (currentNote.isVeg == 1) {
            holder.veg.visibility = View.VISIBLE
        } else {
            holder.veg.visibility = View.GONE
        }
        holder.price.text = "" + currentNote.price
        holder.checkBox.text = currentNote.name
        if (currentNote.default == 1 && selectedId.isNullOrEmpty()) {
            holder.checkBox.isChecked = true
            selectedId = currentNote.id
        }

        currentNote.id?.let {
            if (holder.checkBox.isChecked != it.equals(selectedId))
                holder.checkBox.isChecked = it.equals(selectedId)
        }


        holder.checkBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            try {
                if (isChecked)
                    setCheckedId(currentNote.id)
            } catch (e: Exception) {
            }
        }

        currentNote.disabled?.let {
            if (it) {
                holder.parent.alpha = 0.2f
                holder.checkBox.isEnabled = false
            } else {
                holder.parent.alpha = 1f
                holder.checkBox.isEnabled = true
            }
        }

        currentNote.count?.let {
            holder.count.text = ""+it
        }


        holder.addButton.setOnClickListener {
            val variation = currentNote
            if(variation?.count != null && variation.inStock!=null ) {
                if( variation.inStock!!>variation.count!!) {
                    if (!groupId.isNullOrEmpty() && !variation.id.isNullOrEmpty()) {
                        updateAdapter(variation.id!!)
                        listener.onAdd(VariantDbData(groupId!!, variation.id!!, 1))
                    }
                }else{
                    Toast.makeText(context,"Not in stock!", Toast.LENGTH_SHORT).show()
                }
            }else if(variation?.count == null && variation?.inStock!=null){
                if( variation.inStock!!>0) {
                    if (!groupId.isNullOrEmpty() && !variation.id.isNullOrEmpty()) {
                        updateAdapter(variation.id!!)
                        listener.onAdd(VariantDbData(groupId!!, variation.id!!, 1))
                    }
                }else{
                    Toast.makeText(context,"Not in stock!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        holder.subtractButton.setOnClickListener {
            
        }

    }

    override fun getItemCount(): Int {
        return variations.size
    }

    fun updateAdapter(id: String) {

        for(i in variations){
            if(i.id==selectedId){
                i.count =+1
            }
        }
        notifyDataSetChanged()

    }


    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var veg: TextView = itemView.findViewById(R.id.veg)
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        var price: TextView = itemView.findViewById(R.id.price)
        var parent: LinearLayout = itemView.findViewById(R.id.parent)
        var count: TextView = itemView.findViewById(R.id.countTv)
        var addButton: ImageView =itemView.findViewById(R.id.addButton)
        var subtractButton : ImageView=itemView.findViewById(R.id.subtractButton)

    }
}