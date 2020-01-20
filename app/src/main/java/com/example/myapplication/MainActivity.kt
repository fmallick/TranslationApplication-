package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import android.icu.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {
    var currentSpeaker : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        secondSpeechButton.setOnClickListener { view ->
            startSpeechActivity(view, "en_US", user2Name.text.toString())
        }
        firstSpeechButton.setOnClickListener { view ->
            startSpeechActivity(view, "ar_IL", user1Name.text.toString())

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startSpeechActivity(view: View?, inputLanguage: String, speaker: String) {
        this.currentSpeaker = speaker
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, inputLanguage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, 10)
        } else {
            Toast.makeText(this, "Your Device Doesn't Support Speech Input", Toast.LENGTH_SHORT)
                .show()
        }
    }



    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            10 -> if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
                val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                val currentDateandTime = sdf.format(Date())
                var translatedResult = result[0]
                var speaker = this.currentSpeaker
                speechResultTextView.text = speechResultTextView.text.toString() + "\n$speaker: $translatedResult $currentDateandTime"
            }
        }
    }
}
