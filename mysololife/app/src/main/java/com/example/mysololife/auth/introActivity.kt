package com.example.mysololife.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityIntroBinding

class introActivity : AppCompatActivity() {

    private lateinit var binding : ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge() // 화면 전체 적용하는 함수같다
        setContentView(R.layout.activity_intro)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)

        //회원가입
        binding.joinBtn.setOnClickListener({
            var intent = Intent(this,JoinActivity::class.java)
            startActivity(intent)
        })

        //로그인
        binding.loginBtn.setOnClickListener({
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        })

        //비회원
        binding.noAccountBtn.setOnClickListener({

        })

    }
}