package com.camelsoft.ratetz._domain

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class Ext {
    companion object {
        fun <T> Fragment.collectStateFlow(stateFlow: StateFlow<T>, collector: suspend (T) -> Unit) {
            lifecycleScope.launchWhenStarted {
                stateFlow.collectLatest(collector)
            }
        }

        fun <T> Fragment.collectStateFlowOwner(stateFlow: StateFlow<T>, collector: suspend (T) -> Unit) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                stateFlow.collectLatest(collector)
            }
        }

        fun <T> Fragment.collectLatestFlow(flow: Flow<T>, collector: suspend (T) -> Unit) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    flow.collectLatest(collector)
                }
            }
        }
    }
}