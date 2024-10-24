package com.example.policebharti

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.policebharti.databinding.ActivityHomeBinding
import com.example.policebharti.databinding.ActivityQuestionPaprePdfBinding
import com.example.policebharti.databinding.ActivitySubjectWiseQuestionBinding

class SubjectWiseQuestionActivity : AppCompatActivity() {

    lateinit var binding: ActivitySubjectWiseQuestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivitySubjectWiseQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buddtimattaChachniCard.setOnClickListener{
            var i= Intent(this,BuddhimattaQuestionsActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.ganitCard.setOnClickListener{
            var i=Intent(this,GanitActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.marathiCard.setOnClickListener{
            var i=Intent(this,MarthiQuestionsActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.chaluGhadamodiCard.setOnClickListener{
            var i=Intent(this,Chalu_Ghada_Modi_Activity::class.java)
            startActivity(i)
            finish()
        }

        binding.bhumatiCard.setOnClickListener{
            var i=Intent(this,BhumetiActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.englishCard.setOnClickListener{
            var i=Intent(this,EnglishSubjectActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.back.setOnClickListener {
            var i=Intent(this,HomeActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}