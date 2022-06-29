package com.camelsoft.ratetz._presentation.activity_main.fragment_rate

import com.camelsoft.ratetz._domain.models.MRate
import com.camelsoft.ratetz._domain.utils.ISort

sealed class FragmentRateState {
    data class ProvideData(val mRate: MRate, val sortImpl: ISort): FragmentRateState()
    data class ShowError(val message: String): FragmentRateState()
    data class ShowLoading(val isLoad: Boolean): FragmentRateState()
}
