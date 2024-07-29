package com.example.mysololife.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mysololife.R
import com.example.mysololife.board.BoardListAdaptor
import com.example.mysololife.board.BoardModel
import com.example.mysololife.board.BoardWriteActivity
import com.example.mysololife.databinding.FragmentHomeBinding
import com.example.mysololife.databinding.FragmentTalkBinding

// TODO: Rename parameter arguments, choose names that match

class TalkFragment : Fragment() {

    private lateinit var binding : FragmentTalkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)


        binding.writeBtn.setOnClickListener({
            var intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)
        })

        var boardList = mutableListOf<BoardModel>();

        var boardListAdaptor = BoardListAdaptor(boardList)
        binding.boardListView.adapter = boardListAdaptor



        binding.hometap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }

        binding.goodtip.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)
        }

        binding.bottomBookmark.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)
        }

        binding.bottomStore.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)
        }

        return binding.root
    }


}