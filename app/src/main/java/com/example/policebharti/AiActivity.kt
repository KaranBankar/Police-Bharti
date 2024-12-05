package com.example.policebharti

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.policebharti.databinding.ActivityAiBinding
import com.example.policebharti.databinding.ActivityQuestionPaprePdfBinding

class AiActivity : AppCompatActivity() {

    lateinit var binding: ActivityAiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val url = request.url.toString()
                if (url.endsWith(".pdf")) {
                    // Open PDF in Google Docs or external PDF viewer
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(Uri.parse(url), "application/pdf")
                    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                    try {
                        startActivity(intent)
                    } catch (e: Exception) {
                        // If no PDF viewer app is available, fall back to Google Docs
                        view.loadUrl("https://docs.google.com/viewer?url=$url")
                    }
                    return true
                }
                return false
            }
        }

        binding.webView.loadUrl("https://www.meta.ai/")

        binding.back.setOnClickListener {
            var i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var i= Intent(this,HomeActivity::class.java)
        startActivity(i)
        finish()
    }
}



