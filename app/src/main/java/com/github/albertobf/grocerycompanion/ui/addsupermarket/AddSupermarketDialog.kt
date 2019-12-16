package com.github.albertobf.grocerycompanion.ui.addsupermarket

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.albertobf.grocerycompanion.GroceryCompanion
import com.github.albertobf.grocerycompanion.R
import com.github.albertobf.grocerycompanion.viewmodel.ViewModelFactory
import javax.inject.Inject

class AddSupermarketDialog : DialogFragment() {

    private lateinit var viewModel: AddSupermarketViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_supermarket_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddSupermarketViewModel::class.java)
        val addSupermarketButton = view.findViewById<Button>(R.id.add_supermarket_button)
        val supermarketName = view.findViewById<EditText>(R.id.add_supermarket_name)
        addSupermarketButton.setOnClickListener {
            viewModel.addSupermarket(supermarketName.text.toString())
        }
        viewModel.newSupermarketId.observe(this, Observer {
            val supermarketDialogListener = targetFragment as SupermarketDialogListener
            supermarketDialogListener.onFinish()
            this.dismiss()
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getComponent().inject(this)
    }

    private fun getComponent() = (activity!!.application as GroceryCompanion).component

    interface SupermarketDialogListener {
        fun onFinish()
    }

}