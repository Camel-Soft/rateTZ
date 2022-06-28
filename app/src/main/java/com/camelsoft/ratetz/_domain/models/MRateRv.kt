package com.camelsoft.ratetz._domain.models

data class MRateRv(
    val name: String,
    val rate: String,
    val isSelected: Boolean
)

val currencyAsc = Comparator<MRateRv> { p0, p1 -> p0.name.compareTo(p1.name) }
val currencyDesc = Comparator<MRateRv> { p0, p1 -> p1.name.compareTo(p0.name) }
val rateAsc = Comparator<MRateRv> { p0, p1 -> (p0.rate.toFloat()*10000).toInt() - (p1.rate.toFloat()*10000).toInt() }
val rateDesc = Comparator<MRateRv> { p0, p1 -> (p1.rate.toFloat()*10000).toInt() - (p0.rate.toFloat()*10000).toInt() }
