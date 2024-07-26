package com.example.mysololife.contentsList

import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mysololife.R

class ContentShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_content_show)

        var url = intent.getStringExtra("url")
        //Toast.makeText(baseContext, url, Toast.LENGTH_SHORT).show()

        val webView :WebView = findViewById(R.id.webView)
        webView.loadUrl(url.toString());

    }
}