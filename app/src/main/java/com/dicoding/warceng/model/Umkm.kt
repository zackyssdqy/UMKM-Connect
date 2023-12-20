package com.dicoding.warceng.model

data class Umkm(
    val id: Long,
    val image: Int,
    val type: String,
    val desc: String,
    val title: String,
    val location: String,
    val isFavorite: Boolean = false

)