package com.example.contact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var progress = findViewById<ProgressBar>(R.id.progressBar)
        progress.visibility = View.VISIBLE

        Handler().postDelayed({

            progress.visibility = View.GONE

            Intent(this,listecontact::class.java).also {
                startActivity(it)
            }
            finish()

        },5000)
    }
}
