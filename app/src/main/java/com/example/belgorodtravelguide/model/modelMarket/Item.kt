package com.example.belgorodtravelguide.model.modelMarket

data class Item(
    val imageRes: String,
    val price: Int,
    val seller: String,
    val description: String
)

data class MarketResponse(
    val items: List<Item>
)