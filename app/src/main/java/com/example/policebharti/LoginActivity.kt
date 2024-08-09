package com.example.policebharti

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.policebharti.databinding.ActivityLoginBinding
import com.example.policebharti.databinding.ActivitySplashBinding


class LoginActivity : AppCompatActivity() {

    lateinit var binding :ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadData()
        if(loadData()==true){
            var intent=Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 1)
        }
        binding.tvSignUp.setOnClickListener {
            val intent= Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener{
            saveData()
            var intent=Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    
    fun saveData(){
        var preferences:SharedPreferences=getSharedPreferences("sharedPref", MODE_PRIVATE)
        var editor=preferences.edit()
        
        editor.apply()
        {
            putBoolean("key",true)
        }.apply()
    }

    fun loadData():Boolean{
        var preferences:SharedPreferences=getSharedPreferences("sharedPref", MODE_PRIVATE)
        var myKey=preferences.getBoolean("key",false)
        return myKey
    }
}