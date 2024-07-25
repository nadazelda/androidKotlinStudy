package com.example.mysololife.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mysololife.R
import com.example.mysololife.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {
    private lateinit var binding : FragmentStoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_store, container, false )

        binding.goodtip.setOnClickListener({
            it.findNavController().navigate(R.id.action_storeFragment_to_tipFragment)
        })
        binding.bottomBookmark.setOnClickListener({
            it.findNavController().navigate(R.id.action_storeFragment_to_bookmarkFragment)
        })
        binding.hometap.setOnClickListener({
            it.findNavController().navigate(R.id.action_storeFragment_to_homeFragment)
        })
        binding.bottomTalk.setOnClickListener({
            it.findNavController().navigate(R.id.action_storeFragment_to_talkFragment)
        })

        return binding.root


    }

}