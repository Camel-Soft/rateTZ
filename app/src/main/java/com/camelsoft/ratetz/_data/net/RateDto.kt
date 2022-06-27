package com.camelsoft.ratetz._data.net

import com.camelsoft.ratetz._domain.models.Rate

data class RateDto(
    val amount: Float,
    val base: String,
    val date: String,
    val rates: Map<String, Float>
)

fun RateDto.toRate(): Rate {
    return Rate(
        amount = amount,
        base = base,
        date =date,
        rates = rates
    )
}