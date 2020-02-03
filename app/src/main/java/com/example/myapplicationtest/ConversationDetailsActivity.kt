package com.example.myapplicationtest

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import java.util.*
import android.icu.text.SimpleDateFormat
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ConversationDetailsActivity  : AppCompatActivity() {

    // Views
    private lateinit var firstSpeechButton: ImageView
    private lateinit var secondSpeechButton: ImageView
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var user1Name: EditText
    private lateinit var user2Name: EditText
    private lateinit var speechResultTextView: TextView
    private lateinit var saveButton: Button

    // other members
    var mConversationID : String = ""
    var mCurrentSpeaker : String = ""
    var mSourceLanguageCode : String = ""
    var mTargetLanguageCode : String = ""
    var mLanguageValues = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation_details)

       initViews()

        // load from firebase
        fetchConversation(intent.getStringExtra(ConversationID)?:"")

        firstSpeechButton.setOnClickListener { view ->
            val language1Selected = spinner1.selectedItem.toString()
            mSourceLanguageCode = LanguageDataStore.fetchCodeForGoogleTranslator(language1Selected)

            val language2Selected = spinner2.selectedItem.toString()
            mTargetLanguageCode = LanguageDataStore.fetchCodeForGoogleTranslator(language2Selected)

            val languageCodeForSpeech =  LanguageDataStore.fetchCodeForSpeechRecognizer(language1Selected)
            startSpeechActivity(view, languageCodeForSpeech, user1Name.text.toString())
        }
        secondSpeechButton.setOnClickListener { view ->
            val language2Selected = spinner2.selectedItem.toString()
            mSourceLanguageCode = LanguageDataStore.fetchCodeForGoogleTranslator(language2Selected)

            val language1Selected = spinner1.selectedItem.toString()
            mTargetLanguageCode = LanguageDataStore.fetchCodeForGoogleTranslator(language1Selected)

            val languageCodeForSpeech =  LanguageDataStore.fetchCodeForSpeechRecognizer(language2Selected)
            startSpeechActivity(view, languageCodeForSpeech, user2Name.text.toString())
        }

        saveButton.setOnClickListener{ view ->

            val conversationDatabase = FirebaseDatabase.getInstance().getReference(CONVERSATION_NOTDE)

            if(mConversationID == null || mConversationID.length == 0) {
                mConversationID = (conversationDatabase.push()).key.toString()
            }

            var conversation = createConversationModel(mConversationID)
            conversationDatabase.child(mConversationID).setValue(conversation)

            // Start List view Activity
            var intent = Intent(this, ConversationsListActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initViews() {
        firstSpeechButton = findViewById(R.id.firstSpeechButton)
        secondSpeechButton = findViewById(R.id.secondSpeechButton)
        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)
        user1Name = findViewById(R.id.user1Name)
        user2Name = findViewById(R.id.user2Name)
        speechResultTextView = findViewById(R.id.speechResultTextView)
        saveButton = findViewById(R.id.saveButton)

        // papulating data
        for( value in resources.getStringArray(R.array.languages)) {
            mLanguageValues.add(value)
        }

        var converstaion = Conversation("", USERNAME1,USERNAME2,USER1_LANG, USER2_LANG,"","")
        loadUI(converstaion)

    }


    private fun fetchConversation(conversationID: String) {
        mConversationID = conversationID
        val conversationDatabase = FirebaseDatabase.getInstance().getReference(CONVERSATION_NOTDE).child(mConversationID)

        conversationDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val conversation = dataSnapshot.getValue<Conversation>(Conversation::class.java)
                if(conversation != null) {
                    loadUI(conversation)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun loadUI(conversation: Conversation) {
        // TODO update the conversation data to UI
        user1Name.setText(conversation.user1)
        user2Name.setText(conversation.user2)
        speechResultTextView.text = conversation.translatedText
        spinner1.setSelection(mLanguageValues.indexOf(conversation.user1Language))
        spinner2.setSelection(mLanguageValues.indexOf(conversation.user2Language))

    }

    private fun createConversationModel(conversationID: String = "") : Conversation {

        val language1 = spinner1.selectedItem.toString()
        val language2 = spinner2.selectedItem.toString()
        val user1 = user1Name.text.toString()?: "User1"
        val user2 = user2Name.text.toString() ?: "User2"
        val translatedText = speechResultTextView.text.toString() ?: ""

        return Conversation(conversationID, user1,user2,language1,language2,translatedText,"")
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
        this.mCurrentSpeaker = speaker
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
                val currentTime = sdf.format(Date())
                var translatedResult = GoogleTranslateManager.textToTranslate(result[0],this.mSourceLanguageCode,this.mTargetLanguageCode, resources )


                var speaker = this.mCurrentSpeaker
                speechResultTextView.text = speechResultTextView.text.toString() + "\n$speaker ($currentTime): $translatedResult "
            }
        }
    }

    companion object{

        const val USERNAME1 = "John Doe"
        const val USERNAME2 = "John Fella"
        const val USER1_LANG = "English, US"
        const val USER2_LANG = "English, US"

    }
}
