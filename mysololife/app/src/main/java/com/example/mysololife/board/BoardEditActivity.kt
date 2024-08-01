package com.example.mysololife.board

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityBoardEditBinding
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

class BoardEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBoardEditBinding
    private var TAG : String = BoardEditActivity::class.java.simpleName
    private lateinit var key : String
    private lateinit var writeUID : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_edit)
        key = intent.getStringExtra("key").toString()
        requestPermissions()  // 권한 요청




        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_edit)
        //글작성과 다른건 화면 로딩시 key 값으로 데이터 알아와서 화면에 뿌려줌

        getBoardData(key)
        getImagaData(key)


        binding.writeBtn.setOnClickListener{
            voidEditBoardData(key)
        }
        //이미지 업로드를 위해서 권한 추가
        //onActivityResult활용
        //Intent로 갤러리호출
//        //result code로 데이터 활용 조건 추가
//        binding.imageArea.setOnClickListener({
//            imageClick = true
//            var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            startActivityForResult(intent,100)
//
//        })
    }

    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if( resultCode == RESULT_OK &&  requestCode == 100){
            binding.imageArea.setImageURI(data?.data)
        }
    }

    private fun voidEditBoardData(key:String){
        var title = binding.titleArea.text.toString()
        var content = binding.contentArea.text.toString()
        //write랑 다른건 key를 가져온걸사용
        //key 새로발급하면 안돼요
        FBRef.boardRef.child(key)
            .setValue(BoardModel(title,content
                //선택한 글의 uid를 셋팅
                //, FBAuth.getUid().toString()
                ,writeUID
                , FBAuth.getTime().toString()))

        Log.d(TAG, "modifyDate list KEY ===="+key +" UID ===="+ writeUID)


        Toast.makeText(this, "게시글 수정 완료", Toast.LENGTH_LONG).show()

        finish()
    }
    private fun getBoardData(key : String){
        var postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //firebase와 같은 형태의 ㅁmodel 클래스에 맞게끔 매
                val item = dataSnapshot.getValue(BoardModel::class.java)

                binding.titleArea.setText(item?.title.toString())
                binding.contentArea.setText(item?.content.toString())
                //해당 게시글 수정을 위해
                writeUID = item!!.uid


            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
    private fun getImagaData(key : String){
        val storageReference = com.google.firebase.ktx.Firebase.storage.reference.child(key+".jpg")
        Log.d(TAG, "storageReference================="+storageReference)
        storageReference.downloadUrl.addOnSuccessListener { uri ->
            // URL을 Glide로 로드
            Glide.with(this)
                .load(uri)
                .into(binding.imageArea)
        }.addOnFailureListener { exception ->
            // 실패 처리
            Log.e(TAG, "Image load failed: ${exception.message}")
            //Toast.makeText(this, "Image load failed", Toast.LENGTH_SHORT).show()
        }
    }
}