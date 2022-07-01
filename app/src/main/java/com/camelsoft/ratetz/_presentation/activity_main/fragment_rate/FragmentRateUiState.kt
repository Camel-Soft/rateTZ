package com.camelsoft.ratetz._presentation.activity_main.fragment_rate

sealed class FragmentRateUiState<out T : Any> {
    data class Success<out T: Any>(val data: T) : FragmentRateUiState<T>()
    object ShowLoading : FragmentRateUiState<Nothing>()
    class ShowError(val message: String? = null) : FragmentRateUiState<Nothing>()
}
