package com.camelsoft.ratetz._domain.models

import com.camelsoft.ratetz._domain.utils.floatToString

data class MRate(
    val amount: Float,
    val base: String,
    val date: String,
    val rates: Map<String, Float>
)

fun MRate.toRatesList(): List<MRateRv> {
    val list = mutableListOf<MRateRv>()
    rates.forEach { (name, rate) ->
        list.add(
            MRateRv(
                name = name,
                rate = floatToString(rate, 4),
                isSelected = false
            )
        )
    }
    return list
}

fun MRate.toCurrencyList(): List<String> {
    val list = mutableListOf<String>()
    list.add(base)
    rates.forEach { (name) ->
        list.add(name)
    }
    return list
}