package com.example.policebharti

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.policebharti.databinding.ActivityDistrictSelectBinding

class DistrictSelectActivity : AppCompatActivity() {

    lateinit var bindig:ActivityDistrictSelectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindig=ActivityDistrictSelectBinding.inflate(layoutInflater)
        setContentView(bindig.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bindig.back.setOnClickListener{
            var intent= Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}