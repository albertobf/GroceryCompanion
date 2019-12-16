package com.github.albertobf.grocerycompanion.model.parcelable

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductParcelable(
    val id: Long,
    val name: String,
    val size: Int
) : Parcelable