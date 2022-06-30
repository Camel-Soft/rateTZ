package com.camelsoft.ratetz._data.net

data class RateDto(
    val amount: Float,
    val base: String,
    val date: String,
    val rates: Map<String, Float>
)