package com.example.policebharti

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.policebharti.databinding.ActivitySyllabusPdfBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class Syllabus_PDF_Activity : AppCompatActivity() {

    lateinit var binding:ActivitySyllabusPdfBinding
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivitySyllabusPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.back.setOnClickListener{
            var i= Intent(this,HomeActivity::class.java)
            startActivity(i)
            finish()
        }

        //Adding Syllabus Download Logic

        binding.syllabusDownload.setOnClickListener {
            val inputStream = resources.openRawResource(R.raw.syllabus)

            // Prepare to save the file in the Downloads folder
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, "syllabus.pdf")
                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            // Insert the file into MediaStore
            val resolver = contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            uri?.let {
                val outputStream: OutputStream? = resolver.openOutputStream(it)
                outputStream?.use { stream ->
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (inputStream.read(buffer).also { length = it } != -1) {
                        stream.write(buffer, 0, length)
                    }
                }
                Toast.makeText(this, "PDF downloaded to Downloads", Toast.LENGTH_SHORT).show()

                //Open PDF USing Intent
                // Close the input stream
                inputStream.close()

                // Open the PDF after download
                val openPdfIntent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(it, "application/pdf")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }

                try {
                    startActivity(openPdfIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, "No app found to open PDF", Toast.LENGTH_SHORT).show()
                }

            } ?: run {
                Toast.makeText(this, "Failed to download PDF", Toast.LENGTH_SHORT).show()
            }

            Toast.makeText(this,"Syllabus Download",Toast.LENGTH_SHORT).show();
        }
    }
}