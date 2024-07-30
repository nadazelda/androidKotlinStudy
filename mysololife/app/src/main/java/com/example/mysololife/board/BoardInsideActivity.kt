package com.example.mysololife.board

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.mysololife.R
import com.example.mysololife.contentsList.ContentModel
import com.example.mysololife.databinding.ActivityBoardInsideBinding
import com.example.mysololife.databinding.FragmentTalkBinding
import com.example.mysololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BoardInsideActivity : AppCompatActivity() {
    private var TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var binding : ActivityBoardInsideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_inside)

        var key = intent.getStringExtra("key")
        Log.d(TAG, "key=============="+key)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_inside)
        getBoardData(key.toString())
        getImagaData(key.toString())





    //1번방식 주석
//        var title = intent.getStringExtra("title")
//        var content = intent.getStringExtra("content")
//        var time = intent.getStringExtra("time")
//
//        Log.d(TAG, "titme="+title)
//        Log.d(TAG, "content="+content)
//        Log.d(TAG, "time="+time)



    }

    private fun getImagaData(key : String){
        val storageReference = Firebase.storage.reference.child(key+".jpg")
        Log.d(TAG, "storageReference================="+storageReference)
        storageReference.downloadUrl.addOnSuccessListener { uri ->
            // URL을 Glide로 로드
            Glide.with(this)
                .load(uri)
                .into(binding.getImageArea)
        }.addOnFailureListener { exception ->
            // 실패 처리
            Log.e(TAG, "Image load failed: ${exception.message}")
            //Toast.makeText(this, "Image load failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getBoardData(key: String) {
        var postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //firebase와 같은 형태의 ㅁmodel 클래스에 맞게끔 매
                val item = dataSnapshot.getValue(BoardModel::class.java)
                binding.timeArea.text = item?.time.toString()
                binding.titleArea.text = item?.title.toString()
                binding.textArea.text = item?.content.toString()

            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
}