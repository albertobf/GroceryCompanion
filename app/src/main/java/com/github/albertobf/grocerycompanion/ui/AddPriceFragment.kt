package com.github.albertobf.grocerycompanion.ui

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.albertobf.grocerycompanion.GroceryCompanion

import com.github.albertobf.grocerycompanion.R
import com.github.albertobf.grocerycompanion.databinding.AddPriceFragmentBinding
import com.github.albertobf.grocerycompanion.ui.addsupermarket.AddSupermarketDialog
import com.github.albertobf.grocerycompanion.viewmodel.ViewModelFactory
import javax.inject.Inject

class AddPriceFragment : Fragment(), AddSupermarketDialog.SupermarketDialogListener {

    private lateinit var viewModel: AddPriceViewModel
    private lateinit var binding: AddPriceFragmentBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_price_fragment,
            container,
            false
        )
        binding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddPriceViewModel::class.java)
        viewModel.product.value = AddPriceFragmentArgs.fromBundle(requireArguments()).product
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.supermarkets.observe(this, Observer {
            val arrayAdapter = ArrayAdapter(activity!!.applicationContext,
                R.layout.dropdown_menu_popup_item, it)
            binding.addPriceSupermarket.setAdapter(arrayAdapter)
        })

        viewModel.currencies.observe(this, Observer {
            val arrayAdapter = ArrayAdapter(activity!!.applicationContext,
                R.layout.dropdown_menu_popup_item, it)
            binding.addPriceCurrency.setAdapter(arrayAdapter)
        })

        binding.addPriceAddSupermarketButton.setOnClickListener {
            val addSupermarketDialog = AddSupermarketDialog()
            addSupermarketDialog.setTargetFragment(this, 0)
            addSupermarketDialog.show(requireFragmentManager(), "addSupermarketDialog")
        }

        binding.addPriceButton.setOnClickListener {
            viewModel.savePrice()
        }

        viewModel.error.observe(this, Observer { error ->
            if(error) {
                binding.addPriceSupermarketTil.error = getString(R.string.error_add_price)
            }
        })

        viewModel.priceSaved.observe(this, Observer {saved ->
            if(saved) {
                val action = AddPriceFragmentDirections.actionAddPriceFragmentToProductDetail(viewModel.product.value!!.id)
                findNavController().navigate(action)
            }
        })
    }

    override fun onFinish() {
        viewModel.loadSupermarkets()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getComponent().inject(this)
    }

    private fun getComponent() = (activity!!.application as GroceryCompanion).component

}