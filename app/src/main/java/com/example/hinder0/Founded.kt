package com.example.hinder0

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Founded : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_founded)
        val actionBar = supportActionBar
        actionBar!!.title = "New Activity"
        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
