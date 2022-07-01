package com.camelsoft.ratetz._presentation.activity_main.fragment_rate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camelsoft.ratetz._domain.use_cases.DeleteCurrencyUseCase
import com.camelsoft.ratetz._domain.use_cases.GetRateByBaseUseCase
import com.camelsoft.ratetz._domain.use_cases.InsertCurrencyUseCase
import com.camelsoft.ratetz._domain.utils.SortMethod
import com.camelsoft.ratetz.common.Const.DEFAULT_BASE
import com.camelsoft.ratetz.common.state.StateAsync
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentRateViewModel @Inject constructor(
    private val getRateByBaseUseCase: GetRateByBaseUseCase,
    private val insertCurrencyUseCase: InsertCurrencyUseCase,
    private val deleteCurrencyUseCase: DeleteCurrencyUseCase
) : ViewModel() {

    private val _stateUI: MutableStateFlow<FragmentRateState> =
        MutableStateFlow(
            FragmentRateState(
                mRateUiState = FragmentRateUiState.ShowLoading,
                base = DEFAULT_BASE,
                sortMethod = SortMethod.CURRENCY_ASC,
                isFavorite = false
            )
        )

    val stateUI = _stateUI.asStateFlow()

    init {
        getRateByBase()
    }

    fun getRateByBase() {
        getRateByBaseUseCase(base = stateUI.value.base).onEach { result ->
            when (result) {
                is StateAsync.Success -> {
                    //sendStateRateUi(FragmentRateStateOLD.ShowLoading(false))
                    _stateUI.value = _stateUI.value.copy(
                        mRateUiState = FragmentRateUiState.ShowLoading
                    )

                    result.data?.let {
                        _stateUI.value = _stateUI.value.copy(
                            mRateUiState = FragmentRateUiState.Success(data = it)
                        )
                    }
                }
                is StateAsync.Error -> {
                    _stateUI.value = _stateUI.value.copy(
                        mRateUiState = FragmentRateUiState.ShowError(result.message)
                    )
                }
                is StateAsync.Loading -> {
                    _stateUI.value = _stateUI.value.copy(
                        mRateUiState = FragmentRateUiState.ShowLoading
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setBase(base: String) {
        _stateUI.value = _stateUI.value.copy(
            base = base
        )
        getRateByBase()
    }

    fun setSortMethod(sortMethod: SortMethod) {
        _stateUI.value = _stateUI.value.copy(
            sortMethod = sortMethod
        )
    }

    fun setIsFavorite(isFavorite: Boolean) {
        _stateUI.value = _stateUI.value.copy(
            isFavorite = isFavorite
        )
    }

    fun addFavorite(position: Int) {
        viewModelScope.launch {
            val mRateUiState = stateUI.value.mRateUiState
            if (mRateUiState is FragmentRateUiState.Success) {
                insertCurrencyUseCase(mRateUiState.data.rates[position].name)
                getRateByBase()
            }
        }
    }

    fun rmFavorite(position: Int) {
        viewModelScope.launch {
            val mRateUiState = stateUI.value.mRateUiState
            if (mRateUiState is FragmentRateUiState.Success) {
                deleteCurrencyUseCase(mRateUiState.data.rates[position].name)
                getRateByBase()
            }
        }
    }
}