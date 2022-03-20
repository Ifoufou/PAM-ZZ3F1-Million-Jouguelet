package com.example.tp2v1.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONException
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private val touhouGames : MutableLiveData<MutableList<TouhouGame>> by lazy {
        MutableLiveData<MutableList<TouhouGame>>().also {
            this.loadGames()
        }
    }

    private fun loadGames() {
        viewModelScope.launch() {
            val client = HttpClient(CIO)
            val bytes: ByteArray
            val response: HttpResponse = client.request("https://jsonkeeper.com/b/4MQB") {
                // Configure request parameters exposed by HttpRequestBuilder
            }
            bytes = response.receive()
            val stringJSON: String = String(bytes)
            val jObject = JSONObject(stringJSON)
            val jArray = jObject.getJSONArray("games")
            var l =  mutableListOf<TouhouGame>();
            for (i in 0 until jArray.length()) {
                try {
                    val game = jArray.getJSONObject(i)
                    l.add(Json.decodeFromString<TouhouGame>(game.toString()))
                } catch (e: JSONException) {
                    Log.e("MainViewModel", "crach JSON", e)
                }
            }
            touhouGames.setValue(l)
            client.close()
        }
    }

    public fun touhouGames() : LiveData<MutableList<TouhouGame>> {
        return touhouGames;
    }
}