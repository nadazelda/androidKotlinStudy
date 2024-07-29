package com.example.mysololife.utils

import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class FBRef {
    companion object{
        val database = Firebase.database
        var bookmarkRef = database.getReference("bookmark_list")
        var contents = database.getReference("contents")
        var contents2 = database.getReference("contents2")

    }
}