package com.camelsoft.ratetz._presentation.activity_main.fragment_rate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camelsoft.ratetz._domain.models.MRate
import com.camelsoft.ratetz._domain.use_cases.GetRateByBaseUseCase
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

    private val _fragmentRateState =  Channel<FragmentRateState>()
    val fragmentRateState = _fragmentRateState.receiveAsFlow()

    private val _rate = MutableLiveData<MRate>()
    val rate: LiveData<MRate> = _rate

    fun getRateByBase(base: String) {
        getRateByBaseUseCase(base = base).onEach { result ->
            when (result) {
                is StateAsync.Success -> {
                    sendStateRateUi(FragmentRateState.ShowLoading(false))
                    result.data?.let {
                        _rate.value = it
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

    private fun sendStateRateUi(fragmentRateState: FragmentRateState) {
        viewModelScope.launch {
            _fragmentRateState.send(fragmentRateState)
        }
    }
}