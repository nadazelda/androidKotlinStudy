package com.example.mysololife

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)


//        binding.logoutBtn.setOnClickListener({
//            auth.signOut()
//
//            var intent = Intent(this, introActivity::class.java)
//            startActivity(intent);
//        })

    }
}