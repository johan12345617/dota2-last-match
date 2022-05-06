package com.example.dota2lastmatch

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dota2lastmatch.services.ApiService
import com.example.dota2lastmatch.ui.theme.Dota2LastmatchTheme
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.HashMap


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Dota2LastmatchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
        getMatchInfo("336204238")
    }
}

//get retrofit instance
private fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.opendota.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

//get dota 2 game
private fun getMatchInfo(userId:String){
    CoroutineScope(Dispatchers.IO).launch {
        val call = getRetrofit().create(ApiService::class.java).getIdMatches("players/$userId/"+
                                                                                 "recentMatches")
        val matches = call.body()
        if(call.isSuccessful){
            matches?.get(0)?.matchId?.let { getItems(it, matches[0].heroId) }
        }else{
            //show error
        }
    }
}

private fun getItems(matchId:String,heroId:Int){
    CoroutineScope(Dispatchers.IO).launch {
        val call = getRetrofit().create(ApiService::class.java).getMatchDetails("matches/$matchId")
        if(call.isSuccessful){
            val matches = call.body()
            val matchesObject = matches?.asJsonObject
            val players = matchesObject?.get("players")
            val playersArray = players?.asJsonArray
            for (i in 0 until playersArray?.size()!!) {
                val player = playersArray.get(i).asJsonObject
                if(player.get("hero_id").toString() == heroId.toString()){
                    Log.d("gozu","encontrado")
                }
            }
        }else{
            Log.d("error","error")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Dota2LastmatchTheme {
        Greeting("Android")
    }
}