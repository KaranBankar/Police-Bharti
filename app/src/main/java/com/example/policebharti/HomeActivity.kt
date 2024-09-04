package com.example.policebharti

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
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

    lateinit var binding:ActivityHomeBinding

    lateinit var toggle:ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toggle=ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)

        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {

            if(it.itemId==R.id.nav_home){
                var intent=Intent(this,HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            else if(it.itemId==R.id.nav_profile){
                Toast.makeText(this,"Profile clicked",Toast.LENGTH_SHORT).show()
            }
            else if(it.itemId==R.id.nav_settings){
                Toast.makeText(this,"Settings clicked",Toast.LENGTH_SHORT).show()
            }
            else if(it.itemId==R.id.nav_aboutus){
//                var dialog=Dialog(this)
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//                dialog.setTitle("About US")
//                dialog.setContentView()
//                dialog.create()
            }
            true
        }

        binding.selctDistrictCard.setOnClickListener{
            var intent= Intent(this,DistrictSelectActivity::class.java)
            startActivity(intent)
        }

        binding.districtwisecard.setOnClickListener{
            var intent=Intent(this,YearSelectforDistrictwiseActivity::class.java)
            startActivity(intent)
        }

        binding.syllabusCard.setOnClickListener{
            var i=Intent(this,Syllabus_PDF_Activity::class.java)
            startActivity(i)
        }

        binding.questionpapercard.setOnClickListener{
            var i=Intent(this,Question_Papre_PDF_Activity::class.java)
            startActivity(i)
        }

        binding.testcard.setOnClickListener{
            var i=Intent(this,ChooseTestActivity::class.java)
            startActivity(i)
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}