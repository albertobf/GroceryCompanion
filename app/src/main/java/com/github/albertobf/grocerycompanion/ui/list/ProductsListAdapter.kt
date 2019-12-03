package com.github.albertobf.grocerycompanion.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.albertobf.grocerycompanion.databinding.ProductsItemListBinding
import com.github.albertobf.grocerycompanion.model.Product

class ProductsListAdapter(private val listener: (Product) -> Unit) :
    ListAdapter<Product, ProductsListAdapter.ViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProductsItemListBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, listener)
    }

    class ViewHolder(private val binding: ProductsItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, listener: (Product) -> Unit) {
            binding.product = product
            binding.root.setOnClickListener{listener(product)}
        }
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}