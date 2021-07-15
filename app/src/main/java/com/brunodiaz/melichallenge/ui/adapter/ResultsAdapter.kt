package com.brunodiaz.melichallenge.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brunodiaz.R
import com.brunodiaz.melichallenge.ui.tools.DataTools.setConditionText
import com.brunodiaz.melichallenge.ui.tools.DataTools.setPriceText
import com.brunodiaz.melichallenge.ui.tools.DataTools.setShipmentText
import com.brunodiaz.melichallenge.data.model.Item
import com.brunodiaz.databinding.ItemProductBinding
import com.squareup.picasso.Picasso

class ResultsAdapter(
    val results: List<Item>,
    private val itemClickListener: (Item) -> Unit,
) :
    RecyclerView.Adapter<ResultsAdapter.ResultsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ResultsHolder(layoutInflater.inflate(R.layout.item_product, parent, false))
    }

    override fun getItemCount(): Int {
        return if (results.size > 0) {
            results.size
        } else {
            0   // lista esta vacia, proteccion contra Index out of bonds
        }
    }

    override fun onBindViewHolder(holder: ResultsHolder, position: Int) {
        holder.render(results[position])
    }

    // inner para que ResultsHolder sea hija de ResultsAdapter, si muere el adaptador morira el viewHolder
    inner class ResultsHolder(val view: View) : RecyclerView.ViewHolder(view) {

        // viewbinding
        val binding = ItemProductBinding.bind(view)
        fun render(item: Item) {
            binding.tvTitle.text = item.title
            binding.tvPrice.text = setPriceText(item.price)
            binding.tvShipment.text = setShipmentText(item.shipping.free_shipping)
            binding.tvCondition.text = setConditionText(item.condition)
            Picasso.get().load(item.thumbnail).into(binding.ivProduct)
            binding.cvItem.setOnClickListener { itemClickListener(item) }
        }

    }


}