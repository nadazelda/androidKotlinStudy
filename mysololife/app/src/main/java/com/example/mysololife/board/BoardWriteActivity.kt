package com.example.mysololife.board

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.mysololife.R
import com.example.mysololife.contentsList.BookmarkModel
import com.example.mysololife.databinding.ActivityBoardWriteBinding
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardWriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write)


        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_write)
        binding.writeBtn.setOnClickListener({
            var title = binding.titleArea.text.toString()
            var content = binding.contentArea.text.toString()

            //board
            //  -key
            //      - boardModel
            //          title, content, uid, time
            FBRef.boardRef
                    .push()
                    .setValue(BoardModel(title,content,FBAuth.getUid().toString(), FBAuth.getTime().toString()))
        })
    }
}