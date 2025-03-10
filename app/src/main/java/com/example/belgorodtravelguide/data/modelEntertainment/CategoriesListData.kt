package com.example.belgorodtravelguide.data.modelEntertainment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoriesListData(
    val id: Int,
    var imgCat: Int,
    var nameCat: String,
    var detailCat: Array<String>
) : Parcelable

data class HeaderData(val title: String)
