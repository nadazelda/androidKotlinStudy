package com.example.mysololife.board

import android.Manifest
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
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityBoardWriteBinding
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

class BoardWriteActivity : AppCompatActivity() {
    private var TAG = BoardWriteActivity::class.java.simpleName
    private lateinit var binding : ActivityBoardWriteBinding
    private var imageClick = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write)


        requestPermissions()  // 권한 요청

        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_write)
        binding.writeBtn.setOnClickListener{
            var title = binding.titleArea.text.toString()
            var content = binding.contentArea.text.toString()

            //board
            //  -key
            //      - boardModel
            //          title, content, uid, time
            //storage에 저장된 이미지정보를 글 키값으로 설정하기위해 변경
            var key =FBRef.boardRef.push().key.toString()
            FBRef.boardRef.child(key)
                    .setValue(BoardModel(title,content,FBAuth.getUid().toString(), FBAuth.getTime().toString()))


            Toast.makeText(this, "게시글 입력 완료", Toast.LENGTH_LONG).show()
            if( imageClick) {
                uploadImage(key)
            }
            finish()
        }
        //이미지 업로드를 위해서 권한 추가
        //onActivityResult활용
        //Intent로 갤러리호출
        //result code로 데이터 활용 조건 추가
        binding.imageArea.setOnClickListener({
            imageClick = true
            var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(intent,100)

        })
    }

    private fun uploadImage(key : String ){
        var storage = Firebase.storage
        // Create a storage reference from our app
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key+".jpg")
        val mountainImagesRef = storageRef.child("images/"+key+".jpg")
        mountainsRef.name == mountainImagesRef.name // true
        mountainsRef.path == mountainImagesRef.path // false

        // Get the data from an ImageView as bytes
        var imageView = binding.imageArea
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
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
}