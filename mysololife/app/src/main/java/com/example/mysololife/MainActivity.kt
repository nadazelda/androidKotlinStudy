package com.example.mysololife

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity
import com.example.mysololife.settings.SettingActivity

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        findViewById<ImageView>(R.id.menu).setOnClickListener({
            startActivity(Intent(this, SettingActivity::class.java))
        })

//        binding.logoutBtn.setOnClickListener({
//            auth.signOut()
//
//            var intent = Intent(this, introActivity::class.java)
//            startActivity(intent);
//        })

    }
}