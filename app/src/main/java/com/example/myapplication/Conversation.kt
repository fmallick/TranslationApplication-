package com.example.myapplication

data class Conversation(
    val user1: String ,
    val user2: String ,
    val user1Language: String ,
    val user2Language: String,
    val translatedText: String,
    val rawText: String
)