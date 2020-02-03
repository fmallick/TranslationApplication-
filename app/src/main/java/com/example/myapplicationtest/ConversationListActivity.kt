package com.example.myapplicationtest

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_conversation_list.*

class ConversationsListActivity : AppCompatActivity() {

    private lateinit var sharedPreferences : SharedPreferences


    var conversationDatabase: DatabaseReference? = null
    private lateinit var persistedConversations: MutableList<Conversation>
    private  lateinit var listView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation_list)

        setSupportActionBar(toolbar)
        conversationDatabase = FirebaseDatabase.getInstance().getReference("conversations")

        sharedPreferences  = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
        var email = sharedPreferences.getString(EMAIL, null)
        var password = sharedPreferences.getString(PASSWORD, null)


        // AutoSign In for Development purpose and comment them out after testing
//        if(email != null && password != null) {
//
//            loginUserAccount(email,password)
//        } else {
//            createUserAndThenLogin("test@test.com","password")
//        }

        listView = findViewById(R.id.listView)

        //When the user clicks on a specific distributor list item, show their biography/request page
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val conversation = persistedConversations[i]
                val intent = Intent(applicationContext, ConversationDetailsActivity::class.java)
                intent.putExtra(ConversationID, conversation.converstionId)
                startActivity(intent)
            }

        getDistributorValues()
        fab.setOnClickListener { view ->
            startConverstaionDetailActivity()
        }
    }

    private fun getDistributorValues() {

        conversationDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //populating the distributors array from the database
                persistedConversations =  ArrayList()
                for (postSnapshot in dataSnapshot.children) {
                    val conversation = postSnapshot.getValue<Conversation>(Conversation::class.java)
                    if(conversation != null) {
                        persistedConversations.add(conversation)
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
        conversationDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //clearing the previous distributors list
                persistedConversations.clear()

                //populating the distributors array from the database
                for (postSnapshot in dataSnapshot.children) {
                    val persistedRequest = postSnapshot.getValue<Conversation>(Conversation::class.java)
                    persistedConversations.add(persistedRequest!!)
                }

                var adapter = ConversationListAdapter(this@ConversationsListActivity, persistedConversations)
                listView.setAdapter(adapter)
                //Initializing listViewAdapter to customized DistributorList adapter

            }

            override fun onCancelled(p0: DatabaseError) {
                //Empty
            }
        })
    }

//    private fun createUserAndThenLogin(email: String, password: String) {
//        val mAuth = FirebaseAuth.getInstance()
//        mAuth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val userID = mAuth.currentUser!!.uid
//                    sharedPreferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
//                    val editor = sharedPreferences.edit()
//                    editor.putString(EMAIL, email)
//                    editor.putString(USERID, userID)
//                    editor.putString(PASSWORD, password)
//                    editor.apply()
//                    editor.commit()
//                    loginUserAccount(email, password)
//                }
//}
//    }


    private fun startConverstaionDetailActivity() {
        val newIntent = Intent(this, ConversationDetailsActivity::class.java)
        startActivity(newIntent)
    }


//    private fun loginUserAccount(email:String, password:String) {
//
//        val mAuth = FirebaseAuth.getInstance()
//        mAuth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val userID = mAuth.currentUser!!.uid
//                    sharedPreferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
//                    val editor = sharedPreferences.edit()
//                    editor.putString(EMAIL, email)
//                    editor.putString(USERID, userID)
//                    editor.putString(PASSWORD, password)
//                    editor.apply()
//                    editor.commit()
//                }
//            }
//    }


    companion object{

        const val USERNAME = "Username"
        const val PASSWORD = "Password"
        const val PREFERENCE = "MY_PREFERENCE"
        const val ConversationID = "ConversationID"
        const val EMAIL = "Email"
        const val USERID = "UserID"

    }
}
