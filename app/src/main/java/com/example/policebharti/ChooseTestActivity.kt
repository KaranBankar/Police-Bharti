package com.example.policebharti

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.policebharti.databinding.ActivityChooseTestBinding

class ChooseTestActivity : AppCompatActivity() {

    lateinit var binding:ActivityChooseTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityChooseTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.back.setOnClickListener {
            var i= Intent(this,HomeActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.test1.setOnClickListener{
            var i=Intent(this,TestOneActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.test2.setOnClickListener{
            var i=Intent(this,TestTwoActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.test3.setOnClickListener{
            var i=Intent(this,TestThreeActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.test4.setOnClickListener{
            var i=Intent(this,TestFourActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.test5.setOnClickListener{
            var i=Intent(this,TestFiveActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.test6.setOnClickListener{
            var i=Intent(this,TestSixActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var i=Intent(this,HomeActivity::class.java)
        startActivity(i)
        finish()
    }
}