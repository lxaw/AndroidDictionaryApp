package com.example.dictionary

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.w3c.dom.Document
import java.io.IOException
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    val URL_BASE : String = "https://dictionary.goo.ne.jp/word/"

    val BLANK_SEARCH : String = "..."

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // for entered text
        var tvEnteredWord = findViewById<TextView>(R.id.tvEnteredWord)
        // for edit text
        var etWordEntry = findViewById<EditText>(R.id.etWordEntry)
        // for button press
        var btnSearch = findViewById<Button>(R.id.btnSearch)

        btnSearch.setOnClickListener{
            Toast.makeText(this@MainActivity,"検索中...",Toast.LENGTH_SHORT).show()
            // update the tv
            // 1: get the text from edittext
            //
            var strSearchText :String = etWordEntry.text.toString()
            tvEnteredWord.text = strSearchText

            CoroutineScope(Dispatchers.Default).launch{
                sendWordToGoo(strSearchText)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    // with help from https://www.tutorialspoint.com/how-to-parse-html-in-android-using-kotlin
    private suspend fun sendWordToGoo(strWord:String){
        val strNewUrl =this.URL_BASE + strWord + "/"
        val strBuilder = StringBuilder()
        try{
            val doc: org.jsoup.nodes.Document = Jsoup.connect(strNewUrl).get()
            /*
             meaning is within <ol class = "meaning cx">
             under <li>
             under <p class = 'text'>
             */
            val meanings :Elements = doc.select("")

        }catch(e: IOException){

        }

    }
}