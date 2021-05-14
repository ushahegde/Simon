package com.hegdeapps.simonsays

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartActivity2 : AppCompatActivity() {
    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }


    var btnHelp // , btnPictures;
            : Button? = null


    var mLevel = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first2)
        val btn = findViewById<Button>(R.id.start)
        btn.setOnClickListener { startGame() }
    }

    private fun startGame() {
        val intent = Intent()
        intent.setClass(this, SimonActivity::class.java)
        startActivity(intent)
    }


}