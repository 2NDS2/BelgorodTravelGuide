package com.example.belgorodtravelguide.domain.modelMarket

data class Item(
    val imageRes: String,
    val price: Int,
    val seller: String,
    val description: String
)

data class MarketResponse(
    val items: List<Item>
)