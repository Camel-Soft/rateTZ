package com.camelsoft.ratetz._presentation.activity_main.fragment_sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.camelsoft.ratetz.R
import com.camelsoft.ratetz._domain.utils.*
import com.camelsoft.ratetz.databinding.FragmentSortBinding

class FragmentSort : Fragment() {
    private lateinit var binding: FragmentSortBinding
    private var sortMethod = SortMethod.CURRENCY_ASC

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSortBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { sortMethod = it.getParcelable("sortMethod")!! }
        btnApply()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)
        sortMethodSelector()
        sortMethodSetter()
    }

    private fun sortMethodSetter() {
        when(sortMethod) {
            SortMethod.CURRENCY_ASC -> binding.btnCurrencyAsc.isChecked = true
            SortMethod.CURRENCY_DESC -> binding.btnCurrencyDesc.isChecked = true
            SortMethod.RATE_ASC -> binding.btnRateAsc.isChecked = true
            SortMethod.RATE_DESC -> binding.btnRateDesc.isChecked = true
        }
    }

    private fun sortMethodSelector() {
        binding.radioGroup.setOnCheckedChangeListener { radioGroup, checkedBtnId ->
            when(checkedBtnId) {
                R.id.btnCurrencyAsc -> sortMethod = SortMethod.CURRENCY_ASC
                R.id.btnCurrencyDesc -> sortMethod = SortMethod.CURRENCY_DESC
                R.id.btnRateAsc -> sortMethod = SortMethod.RATE_ASC
                R.id.btnRateDesc -> sortMethod = SortMethod.RATE_DESC
            }
        }
    }

    private fun btnApply() {
        binding.btnApply.setOnClickListener { goBack(sortMethod) }
    }

    private val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() { goBack(null) }
    }

    private fun goBack(sortMethod: SortMethod?) {
        val bundle = Bundle()
        bundle.putParcelable("sortMethod", sortMethod)
        setFragmentResult("FragmentSort_SortMethod", bundle)
        findNavController().popBackStack()
    }
}