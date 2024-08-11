package com.example.policebharti

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.policebharti.databinding.ActivityHomeBinding
import com.example.policebharti.databinding.ActivityYearSelectforDistrictwiseBinding

class YearSelectforDistrictwiseActivity : AppCompatActivity() {

    lateinit var binding: ActivityYearSelectforDistrictwiseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityYearSelectforDistrictwiseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.card2019.setOnClickListener{
            var i= Intent(this,Show_All_District_questionActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.back.setOnClickListener{
            var i=Intent(this,HomeActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}