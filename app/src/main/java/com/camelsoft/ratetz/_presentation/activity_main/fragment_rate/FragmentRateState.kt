package com.camelsoft.ratetz._presentation.activity_main.fragment_rate

import com.camelsoft.ratetz._domain.models.MRate
import com.camelsoft.ratetz._domain.utils.SortMethod

data class FragmentRateState(
    val mRateUiState: FragmentRateUiState<MRate>,
    val base: String,
    val sortMethod: SortMethod,
    val isFavorite: Boolean
)