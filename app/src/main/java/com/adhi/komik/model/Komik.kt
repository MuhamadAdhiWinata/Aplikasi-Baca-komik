package com.adhi.komik.model

data class Komik(
    val id: Int,
    val name: String,
    val description: String,
    val photoUrl: String,
    val rating: Double,
    var isFavorite: Boolean = false,
)
