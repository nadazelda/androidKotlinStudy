package com.example.mysololife.fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mysololife.R
import com.example.mysololife.board.BoardInsideActivity
import com.example.mysololife.board.BoardListAdaptor
import com.example.mysololife.board.BoardModel
import com.example.mysololife.board.BoardWriteActivity
import com.example.mysololife.databinding.FragmentTalkBinding
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


// TODO: Rename parameter arguments, choose names that match

class TalkFragment : Fragment() {

    private lateinit var binding : FragmentTalkBinding
    private lateinit var boardListAdaptor :  BoardListAdaptor
    private var TAG = TalkFragment::class.java.simpleName
    var boardList = mutableListOf<BoardModel>();
    var boardKeyList =  mutableListOf<String>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)

        boardListAdaptor = BoardListAdaptor(boardList)
        binding.boardListView.adapter = boardListAdaptor

//boardInsideActivity로 전달

        //게시물 클릭시 액티비티 이동 데이터 통째로 넘기기
        //intent activity 값 전달
        //
//        binding.boardListView.setOnItemClickListener { parent, view, position, id ->
//            var intent = Intent(requireContext(), BoardInsideActivity::class.java)
//            intent.putExtra("title", boardList[position].title)
//            intent.putExtra("content", boardList[position].content)
//            intent.putExtra("time", boardList[position].time)
//            startActivity(intent)
//        }

        //게시물의 아이디를 알아와서 db에서 아이디 조회해서 보역주기
        //KEY 값을 가져와서 넘겨주고 넘겨진 곳에서 FIREBASE 데이터 가져오기
        //multiplevalue 리스트로 데이터를 받아서
        //불러온 데이터를 adaptor랑 연결
        //adaptor를 동기화
        //데이터 불러오면 viewlistadaptor의 getView에서 값들을 매핑시켜준다
        binding.boardListView.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(requireContext(), BoardInsideActivity::class.java)
            //사용자 uid를 넘겨서 수정삭제 유효성을 체크 --> 별로 비효율
          //  intent.putExtra("uid",FBAuth.getUid())
            intent.putExtra("key",boardKeyList[position])
            startActivity(intent)
        }

        binding.writeBtn.setOnClickListener({
            var intent = Intent(context, BoardWriteActivity::class.java)

            startActivity(intent)
        })

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
        Log.d(TAG, "before call firebase")
        //firebase에서 데이터 가젹오기
        getFBBoardData();
        Log.d(TAG, "after call firebase")
        return binding.root
    }

    //firebase에서 데이터 불러오는 함수 작성

    private fun getFBBoardData(){
        Log.d(TAG, "start getGbboardList")
        var postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //database 호출시마다 클리어해서 다시 더해줌
                Log.d(TAG,"load board")
                boardList.clear()
                for (dataModel in dataSnapshot.children) {
                    //firebase와 같은 형태의 ㅁmodel 클래스에 맞게끔 매
                    Log.d(TAG, dataModel.toString())
                    val item = dataModel.getValue(BoardModel::class.java)
                    boardList.add(item!!)
                    //2번방식을 위해서 키값을 넣어줌
                    boardKeyList.add(dataModel.key.toString())
                }
                boardList.reverse()
                boardKeyList.reverse() // 최신글로 리버스됏기때문에 키도 리버스해줌
                //비동기 방식이기 때문에 해당 함수가 실행된 이후 어답터를 갱신해준다
                boardListAdaptor.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        FBRef.boardRef.addValueEventListener(postListener)
    }

}