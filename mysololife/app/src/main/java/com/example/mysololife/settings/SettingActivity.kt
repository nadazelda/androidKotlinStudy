package com.example.mysololife.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.mysololife.R
import com.example.mysololife.auth.introActivity
import com.example.mysololife.databinding.ActivitySettingBinding
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class SettingActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySettingBinding
    private var TAG :String = SettingActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_setting)
        binding.logoutBtn.setOnClickListener({
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "로그아웃", Toast.LENGTH_LONG).show()
            var intent =Intent(this, introActivity::class.java)
            //로그아웃을 하더라도 뒤로가기 누르면 모든 화면들이 살아있다. 이걸 지워줌
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        })
    }
}