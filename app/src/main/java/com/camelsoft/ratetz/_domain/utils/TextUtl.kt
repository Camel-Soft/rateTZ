package com.camelsoft.ratetz._domain.utils

import java.text.DecimalFormat

fun floatToString(number: Float, decimal: Int): String {
    val decimalFormat = DecimalFormat()
    decimalFormat.maximumFractionDigits = decimal
    return decimalFormat.format(number)
}