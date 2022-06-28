package com.camelsoft.ratetz._presentation.activity_main.fragment_rate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.camelsoft.ratetz._domain.models.*
import com.camelsoft.ratetz._presentation.utils.dialogs.showError
import com.camelsoft.ratetz.databinding.FragmentRateBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.*

@AndroidEntryPoint
class FragmentRate : Fragment() {
    private lateinit var binding: FragmentRateBinding
    private lateinit var weakContext: WeakReference<Context>
    private val viewModel: FragmentRateViewModel by viewModels()
    private lateinit var adapterRv: FragmentRateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRateBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weakContext = WeakReference<Context>(requireContext())
        catchState()
        refreshing()
        btnRefresh()
        chargeRv()




        binding.btnSort.setOnClickListener {

        }






        viewModel.rate.observe(viewLifecycleOwner) {
            invokeSpinner(it.toCurrencyList())
            invokeRv(it.toRatesList())
        }

        getDefRate()
    }

    private fun invokeRv(list: List<MRateRv>) {
        Collections.sort(list, rateDesc)
        adapterRv.submitList(list)
    }

    private fun chargeRv() {
        adapterRv = FragmentRateAdapter()

        adapterRv.setOnItemClickListener = { pos ->

        }

        adapterRv.setOnItemLongClickListener = { pos ->

        }

        binding.rvRate.layoutManager = LinearLayoutManager(weakContext.get()!!, RecyclerView.VERTICAL,false)
        binding.rvRate.adapter = adapterRv
    }

    private fun invokeSpinner(list: List<String>) {
        val adapter = ArrayAdapter(weakContext.get()!!, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list)
        binding.spinnerCurrency.adapter = adapter
    }

    private fun btnRefresh() {
        binding.btnRefresh.setOnClickListener {
            if (binding.spinnerCurrency.selectedItem == null) getDefRate()
            else viewModel.getRateByBase(binding.spinnerCurrency.selectedItem.toString())
        }
    }

    private fun getDefRate() {
        viewModel.getRateByBase("EUR")
    }

    private fun refreshing() {
        binding.refreshLayout.setOnRefreshListener {
            if (binding.spinnerCurrency.selectedItem == null) getDefRate()
            else viewModel.getRateByBase(binding.spinnerCurrency.selectedItem.toString())
        }
    }

    // Обработка состояний от View Model
    private fun catchState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.fragmentRateState.collect { state ->
                when(state) {
                    is FragmentRateState.ShowError -> { showError(weakContext.get()!!, state.message) {} }
                    is FragmentRateState.ShowLoading -> { binding.refreshLayout.isRefreshing = state.isLoad }
                }
            }
        }
    }
}