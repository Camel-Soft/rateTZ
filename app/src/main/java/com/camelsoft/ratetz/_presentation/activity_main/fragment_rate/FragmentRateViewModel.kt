package com.camelsoft.ratetz._presentation.activity_main.fragment_rate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camelsoft.ratetz._domain.models.MRate
import com.camelsoft.ratetz._domain.use_cases.GetRateByBaseUseCase
import com.camelsoft.ratetz._domain.utils.SortFactory
import com.camelsoft.ratetz._domain.utils.SortMethod
import com.camelsoft.ratetz.common.Const.DEFAULT_BASE
import com.camelsoft.ratetz.common.state.StateAsync
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentRateViewModel @Inject constructor(
    private val getRateByBaseUseCase: GetRateByBaseUseCase
) : ViewModel() {

    private val _base = MutableLiveData<String>()
    private val _sortMethod = MutableLiveData<SortMethod>()
    val sortMethod: LiveData<SortMethod> = _sortMethod
    private val _mRate = MutableLiveData<MRate>()

    private val _fragmentRateState =  Channel<FragmentRateState>()
    val fragmentRateState = _fragmentRateState.receiveAsFlow()

    init {
        _base.value = DEFAULT_BASE
        _sortMethod.value = SortMethod.CURRENCY_ASC
        getRateByBase()
    }

    fun getRateByBase() {
        getRateByBaseUseCase(base = _base.value!!).onEach { result ->
            when (result) {
                is StateAsync.Success -> {
                    sendStateRateUi(FragmentRateState.ShowLoading(false))
                    result.data?.let {
                        _mRate.value = it
                        sendStateRateUi(FragmentRateState.ProvideData(
                            mRate = _mRate.value!!,
                            SortFactory().getSortImpl(_sortMethod.value!!)
                        ))
                    }
                }
                is StateAsync.Error -> {
                    sendStateRateUi(FragmentRateState.ShowLoading(false))
                    result.message?.let {
                        sendStateRateUi(FragmentRateState.ShowError(it))
                    }
                }
                is StateAsync.Loading -> {
                    sendStateRateUi(FragmentRateState.ShowLoading(true))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setBase(base: String) { _base.value = base }
    fun setSortMethod(sortMethod: SortMethod) { _sortMethod.value = sortMethod }

    private fun sendStateRateUi(fragmentRateState: FragmentRateState) {
        viewModelScope.launch {
            _fragmentRateState.send(fragmentRateState)
        }
    }
}