package com.example.implicitintents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat

class MainActivity : AppCompatActivity() {
    private lateinit var websiteButton: Button
    private lateinit var locationButton: Button
    private lateinit var textButton: Button
    private lateinit var takePictureButton: Button
    private lateinit var websiteEdittext: EditText
    private lateinit var locationEdittext: EditText
    private lateinit var shareEdittext: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setViewsClickListeners()
    }

    private fun initViews() {
        websiteButton = findViewById(R.id.open_website_button)
        locationButton = findViewById(R.id.open_location_button)
        textButton = findViewById(R.id.share_text_button)
        takePictureButton = findViewById(R.id.take_picture_button)
        websiteEdittext = findViewById(R.id.website_edittext)
        locationEdittext = findViewById(R.id.location_edittext)
        shareEdittext = findViewById(R.id.share_edittext)
    }

    private fun setViewsClickListeners() {
        websiteButton.setOnClickListener {
            val webSiteIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(websiteEdittext.text.toString()))
            startActivityWithResolveCheck(webSiteIntent)
        }

        locationButton.setOnClickListener {
            val locationIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=" + locationEdittext.text.toString())
            )
            startActivityWithResolveCheck(locationIntent)
        }

        textButton.setOnClickListener {
            ShareCompat.IntentBuilder(this)
                .setType("text/plain")
                .setChooserTitle(R.string.share_text_with)
                .setText(shareEdittext.text.toString())
                .startChooser()
        }

        takePictureButton.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityWithResolveCheck(takePictureIntent)
        }
    }

    private fun startActivityWithResolveCheck(intent: Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Can't handle this intent!", Toast.LENGTH_SHORT).show()
        }
    }
}