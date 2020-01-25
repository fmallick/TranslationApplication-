package com.example.myapplication

import android.content.res.Resources
import android.os.StrictMode
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import java.io.IOException

object GoogleTranslateManager {

    // this is for the activity
     fun textToTranslate(inputText: String, sourceLang: String, targetLang: String, resources: Resources) :String {
        var traslatedText = ""
        try{
            traslatedText = translateText(
                inputText,
                sourceLang,
                targetLang,
                resources
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
        }


         return traslatedText
    }

    // this is for google translator cloud services

    private fun getTranslateService(resources: Resources): Translate? {
        var translate : Translate? = null
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        try {
            resources.openRawResource(R.raw.google_api_text_credentials).use { `is` ->
                val myCredentials = GoogleCredentials.fromStream(`is`)
                val translateOptions = TranslateOptions.newBuilder().setCredentials(myCredentials).build()
                translate = translateOptions.service

            }

            return translate

        } catch (ioe: IOException) {
            ioe.printStackTrace()
            return translate
        }
        return translate
    }

    private fun translateText(inputText: String, sourceLangCode: String, targetLangCode: String, resources: Resources): String  {

        var translatedText = ""

        var translator = getTranslateService(resources)

        val translation =  translator!!.translate(inputText, Translate.TranslateOption.targetLanguage(targetLangCode), Translate.TranslateOption.model("base"))
        return  translation.translatedText

    }




}