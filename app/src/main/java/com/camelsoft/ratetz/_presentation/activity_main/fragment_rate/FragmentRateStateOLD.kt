package com.camelsoft.ratetz._presentation.activity_main.fragment_rate

import com.camelsoft.ratetz._domain.models.MRate
import com.camelsoft.ratetz._domain.utils.ISort

sealed class FragmentRateStateOLD {
    data class ProvideData(val mRate: MRate, val sortImpl: ISort): FragmentRateStateOLD()
    data class ShowError(val message: String): FragmentRateStateOLD()
    data class ShowLoading(val isLoad: Boolean): FragmentRateStateOLD()
}
