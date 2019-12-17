package com.github.albertobf.grocerycompanion.ui.addproduct

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.albertobf.grocerycompanion.GroceryCompanion

import com.github.albertobf.grocerycompanion.R
import com.github.albertobf.grocerycompanion.databinding.AddProductFragmentBinding
import com.github.albertobf.grocerycompanion.viewmodel.ViewModelFactory
import javax.inject.Inject

class AddProductFragment : Fragment() {

    private lateinit var viewModel: AddProductViewModel
    private lateinit var binding: AddProductFragmentBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_product_fragment,
            container,
            false
        )
        binding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddProductViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.sizeTypes.observe(this, Observer {sizeTypes ->
            if (!sizeTypes.isNullOrEmpty()) {
                val sizeTypesDesc = sizeTypes.map { it.name }
                val arrayAdapter = ArrayAdapter(activity!!.applicationContext,
                    R.layout.dropdown_menu_popup_item, sizeTypesDesc)
                binding.addProductSizeType.setAdapter(arrayAdapter)
            }
        })
        viewModel.navigate.observe(this, Observer {navigate ->
            if(navigate) {
                val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view!!.windowToken, 0)
                findNavController().navigate(R.id.action_addProductFragment_to_productsListFragment)
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getComponent().inject(this)
    }

    private fun getComponent() = (activity!!.application as GroceryCompanion).component

}