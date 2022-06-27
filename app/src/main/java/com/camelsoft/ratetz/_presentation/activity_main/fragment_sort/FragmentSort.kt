package com.camelsoft.ratetz._presentation.activity_main.fragment_sort

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.camelsoft.ratetz.databinding.FragmentSortBinding
import java.lang.ref.WeakReference

class FragmentSort : Fragment() {
    private lateinit var binding: FragmentSortBinding
    private lateinit var weakContext: WeakReference<Context>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSortBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}