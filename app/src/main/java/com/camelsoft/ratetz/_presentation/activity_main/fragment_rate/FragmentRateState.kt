package com.camelsoft.ratetz._presentation.activity_main.fragment_rate

sealed class FragmentRateState {
    data class ShowError(val message: String): FragmentRateState()
    data class ShowLoading(val isLoad: Boolean): FragmentRateState()
}
