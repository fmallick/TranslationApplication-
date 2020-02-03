package com.example.myapplicationtest

data class Conversation(
    var converstionId: String = "",
    var user1: String = "" ,
    var user2: String = "" ,
    var user1Language: String ="" ,
    var user2Language: String ="",
    var translatedText: String = "",
    var rawText: String = ""
)