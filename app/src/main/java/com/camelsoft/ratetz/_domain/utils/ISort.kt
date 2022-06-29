package com.camelsoft.ratetz._domain.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.camelsoft.ratetz._domain.models.*
import java.util.*

interface ISort {
    fun sort(list: List<MRateRv>): List<MRateRv>
}

class SortCurrencyAsc : ISort {
    override fun sort(list: List<MRateRv>): List<MRateRv> {
        Collections.sort(list, currencyAsc)
        return list
    }
}

class SortCurrencyDesc : ISort {
    override fun sort(list: List<MRateRv>): List<MRateRv> {
        Collections.sort(list, currencyDesc)
        return list
    }
}

class SortRateAsc : ISort {
    override fun sort(list: List<MRateRv>): List<MRateRv> {
        Collections.sort(list, rateAsc)
        return list
    }
}

class SortRateDesc : ISort {
    override fun sort(list: List<MRateRv>): List<MRateRv> {
        Collections.sort(list, rateDesc)
        return list
    }
}

class SortFactory {
    fun getSortImpl(method: SortMethod): ISort {
        return when (method) {
            SortMethod.CURRENCY_ASC -> SortCurrencyAsc()
            SortMethod.CURRENCY_DESC -> SortCurrencyDesc()
            SortMethod.RATE_ASC -> SortRateAsc()
            SortMethod.RATE_DESC -> SortRateDesc()
        }
    }
}

@Parcelize
enum class SortMethod: Parcelable {
    CURRENCY_ASC,
    CURRENCY_DESC,
    RATE_ASC,
    RATE_DESC
}