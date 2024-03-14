package com.example.artel.Adapters

import android.graphics.Bitmap

data class Basket(
    val img: Bitmap,
    val title: String,
    val content: String,
    var count: Int = 1,
    var price: Double = 0.0,
    var quantity: Int = 0
)