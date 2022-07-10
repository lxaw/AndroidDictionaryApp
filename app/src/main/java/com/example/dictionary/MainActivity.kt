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
import kotlinx.coroutines.Dispatchers.Main
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException
import kotlin.system.exitProcess
import kotlin.text.StringBuilder

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
        // for definitions
        var tvDefs = findViewById<TextView>(R.id.tvDefs)

        btnSearch.setOnClickListener{
            Toast.makeText(this@MainActivity,"検索中...",Toast.LENGTH_SHORT).show()
            // update the tv
            // 1: get the text from edittext
            //
            var strSearchText :String = etWordEntry.text.toString()
            tvEnteredWord.text = strSearchText

            CoroutineScope(Dispatchers.Default).launch{
                val strbldrRes:StringBuilder = sendWordToGoo(strSearchText)
                withContext(Main){
                    // update def text
                    //
                    tvDefs.text = strbldrRes.toString()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    // with help from https://www.tutorialspoint.com/how-to-parse-html-in-android-using-kotlin
    private suspend fun sendWordToGoo(strWord:String): StringBuilder{
        val strNewUrl =this.URL_BASE + strWord + "/"
        val strBuilder = StringBuilder()

        val arrstrResults = arrayListOf<String>()

        try{
            val doc: org.jsoup.nodes.Document = Jsoup.connect(strNewUrl).get()
            /*
             meaning is within <ol class = "meaning cx">
             under <li>
             under <p class = 'text'>
             */
            val classMeaning:Elements = doc.select(".meaning")
            for(ol in classMeaning){
                val classTexts = ol.select(".text")
                for(p in classTexts){
                    arrstrResults.add(p.text())
                }
            }

        }catch(e: IOException){
            println("io exception error")
            exitProcess(1)
        }

        // turn into set to remove any duplicates
        // and add to string builder
        //
        for(strDef in arrstrResults.toSet()){
            strBuilder.append(strDef).append("\n")
        }
        return strBuilder
    }
}