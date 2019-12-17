package com.github.albertobf.grocerycompanion.ui.productdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.albertobf.grocerycompanion.databinding.ProductDetailItemListBinding
import com.github.albertobf.grocerycompanion.model.PriceSupermarket

class ProductDetailAdapter :  ListAdapter<PriceSupermarket, ProductDetailAdapter.ViewHolder>(
    ProductDetailDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProductDetailItemListBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val priceSupermarket = getItem(position)
        holder.bind(priceSupermarket)
    }

    class ViewHolder(private val binding: ProductDetailItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(priceSupermarket: PriceSupermarket) {
            binding.priceSupermarket = priceSupermarket
        }
    }

}

class ProductDetailDiffCallback : DiffUtil.ItemCallback<PriceSupermarket>() {
    override fun areItemsTheSame(oldItem: PriceSupermarket, newItem: PriceSupermarket): Boolean {
        return oldItem.supermarket.id == newItem.supermarket.id
    }

    override fun areContentsTheSame(oldItem: PriceSupermarket, newItem: PriceSupermarket): Boolean {
        return oldItem == newItem
    }
}