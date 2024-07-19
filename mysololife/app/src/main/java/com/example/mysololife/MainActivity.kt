package com.example.mysololife

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.mysololife.auth.introActivity
import com.example.mysololife.databinding.ActivityLoginBinding
import com.example.mysololife.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        auth = Firebase.auth

        binding.logoutBtn.setOnClickListener({
            auth.signOut()

            var intent = Intent(this, introActivity::class.java)
            startActivity(intent);
        })

    }
}