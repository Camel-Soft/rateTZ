package com.camelsoft.ratetz._domain.models

data class MRate(
    val amount: Float,
    val base: String,
    val date: String,
    val rates: List<MRateRv>
)

fun MRate.toRatesList(): List<MRateRv> {
    return rates
}

fun MRate.toCurrencyList(): List<String> {
    val list = mutableListOf<String>()
    list.add(base)
    rates.forEach {
        list.add(it.name)
    }
    return list
}