package com.example.myapplication

import android.os.Bundle
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_conversation_list.*

class ConversationsListActivity : AppCompatActivity() {

    private  lateinit var listView : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation_list)
        setSupportActionBar(toolbar)

        var conversations = ArrayList<Conversation>()
        conversations.add(Conversation("John", "Doe", "English, US", "English, US","This is translated text",""))
        conversations.add(Conversation("John", "Doe", "English, US", "English, US","This is translated text",""))

        listView = findViewById(R.id.listView)
        var adapter = ConversationListAdapter(this, conversations)
        listView.setAdapter(adapter)

        fab.setOnClickListener { view ->
            startConverstaionDetailActivity()
        }
    }


    private fun startConverstaionDetailActivity() {
        val newIntent = Intent(this, ConversationDetailsActivity::class.java)
        startActivity(newIntent)
    }
}
