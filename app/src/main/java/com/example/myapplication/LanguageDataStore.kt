package com.example.myapplication

 object LanguageDataStore {

     private var supportedSpeechRecognizerLanguages: HashMap<String, String>
     private var supportedGoogleLanguages: HashMap<String, String>

     init {
         supportedSpeechRecognizerLanguages = initializeSupportedSpeechRecognizerLanguages()
         supportedGoogleLanguages = initializeSupportedGoogleLanguages()
     }

    fun fetchCodeForSpeechRecognizer(languageKey : String) : String{
        val value = supportedSpeechRecognizerLanguages[languageKey]
        return value ?: "en_US"
    }

     fun fetchCodeForGoogleTranslator(languageKey : String) : String{
        val value = supportedGoogleLanguages[languageKey]
        return value ?: "en"
    }
    private fun initializeSupportedSpeechRecognizerLanguages() : HashMap<String, String> {

        return mapOf(
            "English, US" to "en_US",
            "English, Australia" to "en_AU",
            "English, Britain" to "en_GB",
            "English, Canada" to "en_CA",
            "English, New Zealand" to "en_NZ",
            "English, Singapore" to "en_SG",
            "English, India" to "en_IN",
            "English, Ireland" to "en_IE",
            "English, Zimbabwe" to "en_ZA",
            "German, Germany" to "de_DE",
            "Chinese, PRC" to "zh_CN",
            "Chinese, Taiwan" to "zh_TW",
            "Czech, Czech Republic" to "cs_CZ",
            "Dutch, Belgium" to "nl_BE",
            "Dutch, Netherlands" to "nl_NL",
            "French, Belgium" to "fr_BE",
            "French, Canada" to "fr_CA",
            "French, France" to "fr_FR",
            "French, Switzerland" to "fr_CH",
            "German, Austria" to "de_AT",
            "German, Liechtenstein" to "de_LI",
            "German, Switzerland" to "de_CH",
            "Italian, Italy" to "it_IT",
            "Italian, Switzerland" to "it_CH",
            "Japanese" to "ja_JP",
            "Korean" to "ko_KR",
            "Polish" to "pl_PL",
            "Russian" to "ru_RU",
            "Spanish" to "es_ES",
            "Arabic, Egypt" to "ar_EG",
            "Arabic, Israel" to "ar_IL",
            "Bulgarian, Bulgaria" to "bg_BG",
            "Catalan, Spain" to "ca_ES",
            "Croatian, Croatia" to "hr_HR",
            "Danish, Denmark" to "da_DK",
            "Finnish, Finland" to "fi_FI",
            "Greek, Greece" to "el_GR",
            "Hebrew, Israel" to "iw_IL",
            "Hindi, India" to "hi_IN",
            "Hungarian, Hungary" to "hu_HU",
            "Indonesian, Indonesia" to "in_ID",
            "Latvian, Latvia" to "lv_LV",
            "Lithuanian, Lithuania" to "lt_LT",
            "Norwegian-Bokmol, Norway" to "nb_NO",
            "Portuguese, Brazil" to "pt_BR",
            "Portuguese, Portugal" to "pt_PT",
            "Romanian, Romania" to "ro_RO",
            "Serbian" to "sr_RS",
            "Slovak, Slovakia" to "sk_SK",
            "Slovenian, Slovenia" to "sl_SI",
            "Spanish, US" to "es_US",
            "Swedish, Sweden" to "sv_SE",
            "Tagalog, Philippines" to "tl_PH",
            "Thai, Thailand" to "th_TH",
            "Turkish, Turkey" to "tr_TR",
            "Ukrainian, Ukraine" to "uk_UA",
             "Vietnamese, Vietnam" to "vi_VN") as HashMap<String, String>
    }

    private fun initializeSupportedGoogleLanguages(): HashMap<String, String> {
        return mapOf(
            "English, US" to "en",
            "English, Australia" to "en",
            "English, Britain" to "en",
            "English, Canada" to "en",
            "English, New Zealand" to "en",
            "English, Singapore" to "en",
            "English, India" to "en",
            "English, Ireland" to "en",
            "English, Zimbabwe" to "en",
            "German, Germany" to "de",
            "Chinese, PRC" to "zh",
            "Chinese, Taiwan" to "zh",
            "Czech, Czech Republic" to "cs",
            "Dutch, Belgium" to "nl",
            "Dutch, Netherlands" to "nl",
            "French, Belgium" to "fr",
            "French, Canada" to "fr",
            "French, France" to "fr",
            "French, Switzerland" to "fr",
            "German, Austria" to "de",
            "German, Liechtenstein" to "de",
            "German, Switzerland" to "de",
            "Italian, Italy" to "it",
            "Italian, Switzerland" to "it",
            "Japanese" to "ja",
            "Korean" to "ko",
            "Polish" to "pl",
            "Russian" to "ru",
            "Spanish" to "es",
            "Arabic, Egypt" to "ar",
            "Arabic, Israel" to "ar",
            "Bulgarian, Bulgaria" to "bg",
            "Catalan, Spain" to "ca",
            "Croatian, Croatia" to "hr",
            "Danish, Denmark" to "da",
            "Finnish, Finland" to "fi",
            "Greek, Greece" to "el",
            "Hebrew, Israel" to "iw",
            "Hindi, India" to "hi",
            "Hungarian, Hungary" to "hu",
            "Indonesian, Indonesia" to "in",
            "Latvian, Latvia" to "lv",
            "Lithuanian, Lithuania" to "lt",
            "Norwegian-Bokmol, Norway" to "nb",
            "Portuguese, Brazil" to "pt",
            "Portuguese, Portugal" to "pt",
            "Romanian, Romania" to "ro",
            "Serbian" to "sr",
            "Slovak, Slovakia" to "sk",
            "Slovenian, Slovenia" to "sl",
            "Spanish, US" to "es",
            "Swedish, Sweden" to "sv",
            "Tagalog, Philippines" to "tl",
            "Thai, Thailand" to "th",
            "Turkish, Turkey" to "tr",
            "Ukrainian, Ukraine" to "uk",
            "Vietnamese, Vietnam" to "vi") as HashMap<String, String>
    }
}