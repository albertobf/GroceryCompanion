package com.github.albertobf.grocerycompanion.di

import androidx.lifecycle.ViewModel
import com.github.albertobf.grocerycompanion.ui.AddPriceViewModel
import com.github.albertobf.grocerycompanion.ui.addproduct.AddProductViewModel
import com.github.albertobf.grocerycompanion.ui.addsupermarket.AddSupermarketViewModel
import com.github.albertobf.grocerycompanion.ui.list.ProductsListViewModel
import com.github.albertobf.grocerycompanion.ui.productdetail.ProductDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProductsListViewModel::class)
    abstract fun bindProductsListViewModel(productsListViewModel: ProductsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddProductViewModel::class)
    abstract fun bindAddProductViewModel(addProductViewModel: AddProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailViewModel::class)
    abstract fun bindProductDetailViewModel(productDetailViewModel: ProductDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddPriceViewModel::class)
    abstract fun bindAddPriceViewModel(addPriceViewModel: AddPriceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddSupermarketViewModel::class)
    abstract fun bindAddSupermarketViewModel(addSupermarketViewModel: AddSupermarketViewModel): ViewModel

}