package com.camelsoft.ratetz._presentation.activity_main.fragment_fav

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.camelsoft.ratetz.databinding.FragmentFavBinding
import java.lang.ref.WeakReference

class FragmentFav : Fragment() {
    private lateinit var binding: FragmentFavBinding
    private lateinit var weakContext: WeakReference<Context>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}