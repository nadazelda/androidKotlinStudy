package com.example.mysololife.utils

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.logging.SimpleFormatter

class FBAuth {
    companion object{
        private lateinit var auth: FirebaseAuth
        fun getUid() : String{
            auth = FirebaseAuth.getInstance()
            return auth.currentUser?.uid.toString()
        }

        fun getTime() : String{
            var currentDateTime = Calendar.getInstance().time
            var dataformat = SimpleDateFormat("yyyy.mm.dd HH:mm:ss", Locale.KOREA).format(currentDateTime)
            return dataformat
        }

    }

}