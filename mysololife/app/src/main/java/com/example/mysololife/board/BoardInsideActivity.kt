package com.example.mysololife.board

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
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
    private lateinit var key : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_inside)
        key = intent.getStringExtra("key").toString()

        Log.d(TAG, "key=============="+key)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_inside)
        getBoardData(key.toString())
        getImagaData(key.toString())




        //우상단 메뉴를 누르면 삭제수정 팝업을 보여줍니다
        binding.Dialogmenu.setOnClickListener({
            showDialog()
        })



    //1번방식 주석
//        var title = intent.getStringExtra("title")
//        var content = intent.getStringExtra("content")
//        var time = intent.getStringExtra("time")
//
//        Log.d(TAG, "titme="+title)
//        Log.d(TAG, "content="+content)
//        Log.d(TAG, "time="+time)



    }
    private fun showDialog(){
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.activity_dialog,null)
        var builder = AlertDialog.Builder(this).setView(dialog_view).setTitle("게시글 수정/삭제")
        var alertDialog = builder.show()

        alertDialog.findViewById<Button>(R.id.deleteBtn)?.setOnClickListener({
            Toast.makeText(this,"게시글 삭제",Toast.LENGTH_SHORT).show()
            FBRef.boardRef.child(key).removeValue()
            finish()
        })
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener({
            var intent = Intent(this, BoardEditActivity::class.java)

            intent.putExtra("key",key)
            startActivity(intent)
            finish()
        })


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

                Log.d(TAG, "select list KEY ===="+key +" UID ===="+ item?.uid)
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
}