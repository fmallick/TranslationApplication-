package com.example.myapplication

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

//Customized adapter for DonorSplashActivity
class ConversationListAdapter(val context:Activity, var conversations: List<Conversation>):
    ArrayAdapter<Conversation>(context,R.layout.conversation_list_item, conversations){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //inflating donor_splash_list_item layout
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.conversation_list_item, null, true)

        val userDetailView = listViewItem.findViewById<View>(R.id.user) as TextView
        val translatedConversationView = listViewItem.findViewById<View>(R.id.translated_converstaion) as TextView


        val conversation = conversations[position]
        //Populating 'name' and 'about' text fields for each list element
        userDetailView.text = conversation.user1
        translatedConversationView.text = conversation.translatedText


        return listViewItem
    }
}