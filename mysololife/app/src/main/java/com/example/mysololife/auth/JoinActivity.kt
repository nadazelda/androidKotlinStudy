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
import com.example.mysololife.databinding.ActivityIntroBinding
import com.example.mysololife.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_join)

        auth = Firebase.auth

        var chekValue=true;

        //join버튼 클릭
        binding.joinBtn.setOnClickListener({
            var email = binding.idInput.text.toString()
            var password = binding.pwInput.text.toString()
            var passwordchk = binding.pwCheck.text.toString()
            //유효성 체크
            Toast.makeText(baseContext,"email = "+email+" password = "+password+" passwordchk = "+passwordchk,Toast.LENGTH_SHORT,).show()
            if( email.isEmpty() ){
                Toast.makeText(baseContext,"email을 입력하세요  ",Toast.LENGTH_SHORT,).show()
                chekValue = false
            }
            if( password.isEmpty() ){
                Toast.makeText(baseContext,"password 입력하세요  ",Toast.LENGTH_SHORT,).show()
                chekValue = false
            }
            if( passwordchk.isEmpty() ){
                Toast.makeText(baseContext,"passwordchk 입력하세요  ",Toast.LENGTH_SHORT,).show()
                chekValue = false
            }

            if( !password.equals(passwordchk)){
                Toast.makeText(baseContext,"비밀번호를 동일하게 입력하세요  ",Toast.LENGTH_SHORT,).show()
                chekValue = false
            }

            if (chekValue ){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(
                                baseContext,
                                "successssssss ",
                                Toast.LENGTH_SHORT,
                            ).show()

                            var intent = Intent(this, MainActivity::class.java)
                            startActivity(intent);


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(
                                baseContext,
                                "failfailfailfail",
                                Toast.LENGTH_SHORT,
                            ).show()

                        }
                    }
            }


        })



    }
}