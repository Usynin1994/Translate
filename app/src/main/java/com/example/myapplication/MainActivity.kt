package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
//import android.view.translation.TranslationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.myapplication.TranslationResponse

private val translateBaseUrl = "https://api.funtranslations.com"

private val retrofit = Retrofit.Builder()
    .baseUrl(translateBaseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val translationService = retrofit.create(TranslationApi::class.java)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun yodaTranslate(text: String) {
        translationService
            .translateToYoda(TranslationRequest(text))
            .enqueue(object : Callback<TranslationResponse> {
                override fun onResponse(call: Call<TranslationResponse>,
                                        response: Response<TranslationResponse>
                ) {
                    Log.d("TRANSLATION_LOG", "Yoda says: ${response.body()?.contents?.translated}")
                }

                override fun onFailure(call: Call<TranslationResponse>, t: Throwable) {
                }

            })
    }

    fun morseTranslate(text: String) {
        translationService
            .translateToMorse(TranslationRequest(text))
            .enqueue(object : Callback<TranslationResponse> {
                override fun onResponse(call: Call<TranslationResponse>,
                                        response: Response<TranslationResponse>) {
                    Log.d("TRANSLATION_LOG", "${response.body()?.contents?.text} in Morse code: ${response.body()?.contents?.translated}")
                }

                override fun onFailure(call: Call<TranslationResponse>, t: Throwable) {
                }

            })
    }
}