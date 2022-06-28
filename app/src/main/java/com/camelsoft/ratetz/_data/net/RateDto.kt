package com.camelsoft.ratetz._data.net

import com.camelsoft.ratetz._domain.models.MRate

data class RateDto(
    val amount: Float,
    val base: String,
    val date: String,
    val rates: Map<String, Float>
)

fun RateDto.toRate(): MRate {
    return MRate(
        amount = amount,
        base = base,
        date =date,
        rates = rates
    )
}