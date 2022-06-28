package com.camelsoft.ratetz._domain.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun floatToString(number: Float, fractionDigits: Int, separator: Char): String {
    val decimalFormatSymbols = DecimalFormatSymbols()
    decimalFormatSymbols.decimalSeparator = separator
    val decimalFormat = DecimalFormat("###.###", decimalFormatSymbols)
    decimalFormat.maximumFractionDigits = fractionDigits
    return decimalFormat.format(number)
}