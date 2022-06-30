package com.camelsoft.ratetz._presentation.activity_main.fragment_rate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.camelsoft.ratetz.R
import com.camelsoft.ratetz._domain.models.*
import com.camelsoft.ratetz._domain.utils.ISort
import com.camelsoft.ratetz._domain.utils.SortMethod
import com.camelsoft.ratetz._presentation.utils.dialogs.showError
import com.camelsoft.ratetz.databinding.FragmentRateBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

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
        btnSort()
        btnFavorite()
        chargeRv()
        catchSortMethod()
    }

    private fun btnFavorite() {
        binding.btnFavorite.setOnClickListener {
            adapterRv.filter.filter("sdf")
        }
    }

    private fun invokeRv(list: List<MRateRv>, sortImpl: ISort) {
        sortImpl.sort(list)
        adapterRv.submitList(list)
    }

    private fun chargeRv() {
        adapterRv = FragmentRateAdapter()
        adapterRv.setOnItemClickListener = { position -> viewModel.addFavorite(position) }
        adapterRv.setOnItemLongClickListener = { position -> viewModel.rmFavorite(position) }
        binding.rvRate.layoutManager = LinearLayoutManager(weakContext.get()!!, RecyclerView.VERTICAL,false)
        binding.rvRate.adapter = adapterRv
    }

    private fun invokeSpinner(list: List<String>) {
        val adapter = ArrayAdapter(weakContext.get()!!, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list)
        binding.spinnerCurrency.adapter = adapter
    }

    private fun btnRefresh() {
        binding.btnRefresh.setOnClickListener {
            if (binding.spinnerCurrency.selectedItem == null) viewModel.getRateByBase()
            else {
                viewModel.setBase(binding.spinnerCurrency.selectedItem.toString())
                viewModel.getRateByBase()
            }
        }
    }

    private fun refreshing() {
        binding.refreshLayout.setOnRefreshListener {
            if (binding.spinnerCurrency.selectedItem == null) viewModel.getRateByBase()
            else {
                viewModel.setBase(binding.spinnerCurrency.selectedItem.toString())
                viewModel.getRateByBase()
            }
        }
    }

    private fun btnSort() {
        binding.btnSort.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("sortMethod", viewModel.sortMethod.value)
            findNavController().navigate(R.id.action_fragGraphRate_to_fragGraphSort, bundle)
        }
    }

    private fun catchSortMethod() {
        setFragmentResultListener("FragmentSort_SortMethod") { key, bundle ->
            val sortMethod: SortMethod? = bundle.getParcelable("sortMethod")
            if (sortMethod != null) {
                viewModel.setSortMethod(sortMethod)
                viewModel.getRateByBase()
            }
            else
                viewModel.getRateByBase()
        }
    }

    // Обработка состояний от View Model
    private fun catchState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.fragmentRateState.collect { state ->
                when(state) {
                    is FragmentRateState.ProvideData -> {
                        invokeSpinner(state.mRate.toCurrencyList())
                        invokeRv(state.mRate.toRatesList(), state.sortImpl)
                    }
                    is FragmentRateState.ShowError -> { showError(weakContext.get()!!, state.message) {} }
                    is FragmentRateState.ShowLoading -> { binding.refreshLayout.isRefreshing = state.isLoad }
                }
            }
        }
    }
}