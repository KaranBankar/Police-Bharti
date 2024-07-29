package com.example.policebharti

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.telephony.SmsManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.transition.Visibility
import  android.Manifest
import android.net.Uri
import android.widget.Toast

class SignupActivity : AppCompatActivity() {
     lateinit var tv_login:TextView
     lateinit var et_mobile:EditText
    lateinit var tv_send_otp:TextView
    lateinit var btn_send_otp:Button
    lateinit var pb_send_otp:ProgressBar
    lateinit var layout_send_otp:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tv_login = findViewById(R.id.tv_login)
        et_mobile = findViewById(R.id.mobile)
        pb_send_otp = findViewById(R.id.pb_send_otp)
        tv_send_otp = findViewById(R.id.tv_send_otp)
        btn_send_otp = findViewById(R.id.btn_send_otp)
        layout_send_otp = findViewById(R.id.layout_send_otp)
        tv_login.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        et_mobile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                println("Text changed to: ${s.toString()}")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
              var str=et_mobile.text.toString()
              if(str.length==10){
                  layout_send_otp.visibility=View.VISIBLE

              }
                if(str.length!=10){
                    layout_send_otp.visibility=View.GONE
                    pb_send_otp.visibility=View.INVISIBLE
                    tv_send_otp.visibility=View.INVISIBLE

                }
            }
        })

        btn_send_otp.setOnClickListener {
            pb_send_otp.visibility=View.VISIBLE
            Handler().postDelayed(Runnable {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 1)
                } else {
                    sendSMS(et_mobile.text.toString(), "2020")
                }
                  tv_send_otp.visibility=View.VISIBLE
                pb_send_otp.visibility=View.INVISIBLE

            },2000)
        }

    }

    private fun sendSMS(phoneNumber: String, message: String) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "SMS failed to send: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}