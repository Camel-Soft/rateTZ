package com.camelsoft.ratetz._domain.models

data class Rate(
    val amount: Float,
    val base: String,
    val date: String,
    val rates: Map<String, Float>
)