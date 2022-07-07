package com.example.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    val URL_BASE : String = "https://dictionary.goo.ne.jp/word/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // look for edit text
        var etWordEntry = findViewById<EditText>(R.id.etWordEntry)
        // look for button press
        var btnSearch = findViewById<Button>(R.id.btnSearch)

        btnSearch.setOnClickListener{
            Toast.makeText(this@MainActivity,"検索中...",Toast.LENGTH_SHORT).show()
        }
    }
}