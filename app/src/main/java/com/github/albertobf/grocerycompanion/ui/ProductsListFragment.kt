package com.github.albertobf.grocerycompanion.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.albertobf.grocerycompanion.GroceryCompanion
import com.github.albertobf.grocerycompanion.R
import com.github.albertobf.grocerycompanion.databinding.FragmentProductsListBinding
import com.github.albertobf.grocerycompanion.viewmodel.ProductsListViewModel
import com.github.albertobf.grocerycompanion.viewmodel.ViewModelFactory
import javax.inject.Inject

class ProductsListFragment : Fragment() {

    private lateinit var productsListViewModel: ProductsListViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: FragmentProductsListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_products_list,
            container,
            false
        )
        productsListViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductsListViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val productsListAdapter = ProductsListAdapter()
        binding.productsList.adapter = productsListAdapter
        productsListViewModel.products.observe(this, Observer {products ->
            productsListAdapter.submitList(products)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getComponent().inject(this)
    }

    private fun getComponent() = (activity!!.application as GroceryCompanion).component

}
