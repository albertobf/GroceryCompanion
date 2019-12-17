package com.github.albertobf.grocerycompanion.ui.productdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.github.albertobf.grocerycompanion.GroceryCompanion
import com.github.albertobf.grocerycompanion.R
import com.github.albertobf.grocerycompanion.databinding.ProductDetailFragmentBinding
import com.github.albertobf.grocerycompanion.viewmodel.ViewModelFactory
import javax.inject.Inject

class ProductDetailFragment : Fragment() {

    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var binding: ProductDetailFragmentBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.product_detail_fragment,
            container,
            false
        )
        binding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductDetailViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val productId = ProductDetailFragmentArgs.fromBundle(requireArguments()).productId
        viewModel.loadProductDetails(productId)

        viewModel.pricesProduct.observe(this, Observer {pricesProduct ->
            binding.pricesProduct = viewModel.pricesProduct.value
            val adapter = ProductDetailAdapter()
            val orderedList = pricesProduct.priceSupermarket.sortedBy { it.price }
            adapter.submitList(orderedList)
            binding.detailProductPricesList.adapter = adapter
        })

        binding.addPriceButton.setOnClickListener {
            val productParcelable = viewModel.getProductParcelable()
            val action = ProductDetailFragmentDirections.actionProductDetailToAddPriceFragment(productParcelable)
            findNavController().navigate(action)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getComponent().inject(this)
    }

    private fun getComponent() = (activity!!.application as GroceryCompanion).component

}