package com.example.mysololife.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysololife.R
import com.example.mysololife.contentsList.BookmarkRvAdaptor
import com.example.mysololife.contentsList.ContentModel
import com.example.mysololife.contentsList.ContentsRvAdaptor
import com.example.mysololife.databinding.FragmentBookmarkBinding
import com.example.mysololife.databinding.FragmentStoreBinding
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class BookmarkFragment : Fragment() {

    private lateinit var binding : FragmentBookmarkBinding
    private var TAG = BookmarkFragment::class.java.simpleName
    lateinit var rvAdaptor : BookmarkRvAdaptor
    var bookmarkIdList = mutableListOf<String>()
    var items = ArrayList<ContentModel>()
    var keyList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_bookmark, container, false )

        //getCategoryData()

        // 2. 사용자가 북마크한 정보를 다 가져옴!
        getBookmarkData()

        //3. 가져온 정보에서 정보를 추출해서 보여줌
        rvAdaptor = BookmarkRvAdaptor(requireContext(), items,keyList,bookmarkIdList)
        var rv: RecyclerView = binding.bookmarkRv
        rv.adapter = rvAdaptor
        rv.layoutManager = GridLayoutManager(requireContext(), 2) //가로 2개 배치

        binding.goodtip.setOnClickListener({
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_tipFragment)
        })
        binding.bottomStore.setOnClickListener({
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_storeFragment )
        })
        binding.hometap.setOnClickListener({
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_homeFragment)
        })
        binding.bottomTalk.setOnClickListener({
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_talkFragment)
        })
        return binding.root


    }

    private fun getCategoryData(){
        //1. 전체 카테고리를 가져옴
        var postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataModel in dataSnapshot.children) {
                    //firebase와 같은 형태의 ㅁmodel 클래스에 맞게끔 매
                    Log.d(TAG, dataModel.toString())
                    val item = dataModel.getValue(ContentModel::class.java)
                    if( bookmarkIdList.contains(dataModel.key.toString())){
                        items.add(item!!)
                        keyList.add(dataModel.key.toString())
                    }

                }
                //비동기 방식이기 때문에 해당 함수가 실행된 이후 어답터를 갱신해준다
                rvAdaptor.notifyDataSetChanged()
                Log.d("ContentListActivity", "")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        }
        FBRef.contents.addValueEventListener(postListener)
        FBRef.contents2.addValueEventListener(postListener)
    }
    private fun getBookmarkData(){
        //1. 전체 카테고리를 가져옴
        var postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataModel in dataSnapshot.children) {
                    //firebase와 같은 형태의 ㅁmodel 클래스에 맞게끔 매
                    //Log.d("ContentListActivity getBookmark",dataModel.toString())
                    bookmarkIdList.add(dataModel.key.toString())
                }
                //비동기 방식이기 때문에 해당 함수가 실행된 이후 어답터를 갱신해준다
                rvAdaptor.notifyDataSetChanged()
                Log.d("ContentListActivity", "")
                //
                getCategoryData()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        }
        //child에 있는 해당 로그인 uid에 저장된 값만 가져옴
        //그냥 데이터 가져오는 게 아님
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)

    }



}