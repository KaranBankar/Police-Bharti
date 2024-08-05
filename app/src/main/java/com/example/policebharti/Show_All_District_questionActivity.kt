package com.example.policebharti

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.policebharti.databinding.ActivityHomeBinding
import com.example.policebharti.databinding.ActivityShowAllDistrictQuestionBinding

class Show_All_District_questionActivity : AppCompatActivity() {

    lateinit var binding: ActivityShowAllDistrictQuestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityShowAllDistrictQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.marathiCard.setOnClickListener{
            var i= Intent(this,Question_Screen_Activity::class.java)
            startActivity(i)
            finish()
        }
    }
}