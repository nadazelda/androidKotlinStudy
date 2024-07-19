package com.example.mysololife.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.mysololife.MainActivity
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        auth = Firebase.auth


        binding.loginBtn.setOnClickListener({
            var email = binding.idInput.text.toString()
            var password = binding.pwInput.text.toString()



            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(baseContext,"로그인 성공", Toast.LENGTH_SHORT,).show()
                        //로그인 성공시 메ㄴ페이지로
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent);

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext,"email을 입력하세요  ",Toast.LENGTH_SHORT,).show()

                    }
                }
        })


    }
}