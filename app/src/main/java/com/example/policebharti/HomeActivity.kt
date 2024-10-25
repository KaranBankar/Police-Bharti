package com.example.policebharti

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.ScrollIndicators
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.policebharti.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)

        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {

            if (it.itemId == R.id.nav_home) {
                var intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else if (it.itemId == R.id.nav_settings) {
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
            } else if (it.itemId == R.id.nav_aboutus) {
//                var dialog=Dialog(this)
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//                dialog.setTitle("About US")
//                dialog.setContentView()
//                dialog.create()
            }
            true
        }

        binding.SubjectWiseQuestion.setOnClickListener {
            var intent = Intent(this, SubjectWiseQuestionActivity::class.java)
            startActivity(intent)
        }


        binding.syllabusCard.setOnClickListener {
            var i = Intent(this, Syllabus_PDF_Activity::class.java)
            startActivity(i)
            finish()
        }

        binding.questionpapercard.setOnClickListener {
            var i = Intent(this, Question_Papre_PDF_Activity::class.java)
            startActivity(i)
            finish()
        }

        binding.testcard.setOnClickListener {
            var i = Intent(this, ChooseTestActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.whatsapp.setOnClickListener {
            openWhatsApp()
        }

        binding.linkdin.setOnClickListener {
            openLinkedIn()
        }

        binding.instagram.setOnClickListener {
            openInstagram()
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun openWhatsApp() {
        // Check if WhatsApp is installed
        val packageManager = packageManager
        val phoneNumber = "9322067937" // The phone number in international format

        try {
            // Create the intent to open WhatsApp with the phone number
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.me/$phoneNumber") // WhatsApp URL
            intent.setPackage("com.whatsapp") // Ensure it only opens in WhatsApp
            startActivity(intent)
        } catch (e: Exception) {
            // Handle the case where WhatsApp is not installed
            Toast.makeText(this, "WhatsApp not installed.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun openLinkedIn() {
        val linkedInUrl = "https://www.linkedin.com/in/karan-bankar-453b57252"
        val intent = packageManager.getLaunchIntentForPackage("com.linkedin.android")

        if (intent != null) {
            // If the LinkedIn app is installed, open the app
            intent.data = Uri.parse(linkedInUrl)
            startActivity(intent)
        } else {
            // If the app is not installed, open the URL in a browser
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl))
            startActivity(browserIntent)
        }
    }


    private fun openInstagram() {
        val instagramUrl =
            "https://www.instagram.com/karan_bankar_54?igsh=OGZoOHdzd3lmdnc3" // Your actual Instagram profile link
        val intent = packageManager.getLaunchIntentForPackage("com.instagram.android")

        if (intent != null) {
            // If the Instagram app is installed, open the app
            intent.data = Uri.parse(instagramUrl)
            startActivity(intent)
        } else {
            // If the app is not installed, open the URL in a browser
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
            startActivity(browserIntent)
        }
    }
}
