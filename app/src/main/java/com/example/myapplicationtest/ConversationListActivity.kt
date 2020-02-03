package com.example.myapplicationtest

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_conversation_list.*

class ConversationsListActivity : AppCompatActivity() {

    // views
    private lateinit var listView : ListView

    // other members
    private lateinit var mPersistedConversations: MutableList<Conversation>
    private var mConversationDatabaseRef: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation_list)

        setSupportActionBar(toolbar)
        mConversationDatabaseRef = FirebaseDatabase.getInstance().getReference(CONVERSATION_NOTDE)

        listView = findViewById(R.id.listView)

        //When the user clicks on a specific distributor list item, show their biography/request page
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val conversation = mPersistedConversations[i]
                val intent = Intent(applicationContext, ConversationDetailsActivity::class.java)
                intent.putExtra(ConversationID, conversation.converstionId)
                startActivity(intent)
            }

        fetchConversations()
        fab.setOnClickListener {
            val intent = Intent(applicationContext, ConversationDetailsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchConversations() {

        mConversationDatabaseRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //populating the conversations array from the database
                mPersistedConversations =  ArrayList()
                for (postSnapshot in dataSnapshot.children) {
                    val conversation = postSnapshot.getValue<Conversation>(Conversation::class.java)
                    if(conversation != null) {
                        mPersistedConversations.add(conversation)
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                //Empty
            }
        })
    }



    override fun onStart() {
        super.onStart()
        //REQUESTS DATABASE
        mConversationDatabaseRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //clearing the previous distributors list
                mPersistedConversations.clear()

                //populating the distributors array from the database
                for (postSnapshot in dataSnapshot.children) {
                    val persistedRequest = postSnapshot.getValue<Conversation>(Conversation::class.java)
                    mPersistedConversations.add(persistedRequest!!)
                }

                var adapter = ConversationListAdapter(this@ConversationsListActivity, mPersistedConversations)
                listView.setAdapter(adapter)
                //Initializing listViewAdapter to customized DistributorList adapter

            }

            override fun onCancelled(p0: DatabaseError) {
                //Empty
            }
        })
    }

}
