package com.camelsoft.ratetz._presentation.activity_main.fragment_rate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.camelsoft.ratetz.R
import com.camelsoft.ratetz._domain.Ext.Companion.collectStateFlowOwner
import com.camelsoft.ratetz._domain.models.*
import com.camelsoft.ratetz._domain.utils.ISort
import com.camelsoft.ratetz._domain.utils.SortFactory
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
    private var sortMethod = SortMethod.CURRENCY_ASC
    private var isFavorite = false

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

    private fun invokeRv(list: List<MRateRv>, sortImpl: ISort, isFavorite: Boolean) {
        sortImpl.sort(list)
        adapterRv.submitList(list)
        if (isFavorite) adapterRv.filter.filter("true")
        else adapterRv.filter.filter("false")
    }

    private fun chargeRv() {
        adapterRv = FragmentRateAdapter()
        adapterRv.setOnItemClickListener = { position ->
            if (!isFavorite) viewModel.addFavorite(position) else showOnlyFullList()
        }
        adapterRv.setOnItemLongClickListener = { position ->
            if (!isFavorite) viewModel.rmFavorite(position) else showOnlyFullList()
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
            if (!isFavorite) {
                if (binding.spinnerCurrency.selectedItem == null) viewModel.getRateByBase()
                else viewModel.setBase(binding.spinnerCurrency.selectedItem.toString())
            }
            else
                showOnlyFullList()
        }
    }

    private fun showOnlyFullList() {
        Toast.makeText(weakContext.get()!!, resources.getString(R.string.only_full_list), Toast.LENGTH_SHORT).show()
    }

    private fun refreshing() {
        binding.refreshLayout.setOnRefreshListener {
            if (!isFavorite) {
                if (binding.spinnerCurrency.selectedItem == null) viewModel.getRateByBase()
                else viewModel.setBase(binding.spinnerCurrency.selectedItem.toString())
            }
            else {
                binding.refreshLayout.isRefreshing = false
                showOnlyFullList()
            }

        }
    }

    private fun btnSort() {
        binding.btnSort.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("sortMethod", sortMethod)
            findNavController().navigate(R.id.action_fragGraphRate_to_fragGraphSort, bundle)
        }
    }

    private fun catchSortMethod() {
        setFragmentResultListener("FragmentSort_SortMethod") { key, bundle ->
            val sortMethod: SortMethod? = bundle.getParcelable("sortMethod")
            sortMethod?.let { viewModel.setSortMethod(it) }
        }
    }

    private fun btnFavorite() {
        binding.btnFavorite.setOnClickListener {
            if (isFavorite) {
                viewModel.setIsFavorite(false)
                binding.btnFavorite.text = resources.getString(R.string.fav)
            }
            else {
                viewModel.setIsFavorite(true)
                binding.btnFavorite.text = resources.getString(R.string.full)
            }
        }
    }

    private fun catchState() {
        collectStateFlowOwner(stateFlow = viewModel.stateUI) { state ->
            if (state.mRateUiState is FragmentRateUiState.ShowLoading) {
                binding.refreshLayout.isRefreshing = true
            }
            if (state.mRateUiState is FragmentRateUiState.ShowError) {
                binding.refreshLayout.isRefreshing = false
                state.mRateUiState.message?.let { showError(weakContext.get()!!, it) {} }
            }
            if (state.mRateUiState is FragmentRateUiState.Success) {
                binding.refreshLayout.isRefreshing = false
                invokeSpinner(state.mRateUiState.data.toCurrencyList())
                invokeRv(state.mRateUiState.data.toRatesList(), SortFactory().getSortImpl(state.sortMethod), state.isFavorite)
            }
            sortMethod = state.sortMethod
            isFavorite = state.isFavorite
        }
    }
}