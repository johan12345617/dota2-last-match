package com.example.dota2lastmatch.models

import android.util.Log
import com.example.dota2lastmatch.services.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LastMatch(){

    val heroImage: String = ""
    val itemListImage: String = ""
    val heroName: String = ""
    val modeGame: String = ""
    val timeGame: String = ""
    val gameResult: String = ""
    val kdaInfo: String = "" //kill/death/assisted = kda

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
                matches?.get(0)?.matchId?.let { getItemsInfo(it, matches[0].heroId) }
            }else{
                //show error
            }
        }
    }

    private fun getItemsInfo(matchId:String,heroId:Int){
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
                        val listItems = listOf( player.get("item_0").toString(),
                            player.get("item_1").toString(),
                            player.get("item_2").toString(),
                            player.get("item_3").toString(),
                            player.get("item_4").toString())
                        getItemsImage(listItems)
                    }
                }
            }else{
                Log.d("error","error")
            }
        }
    }

    private fun getItemsImage(items:List<String>){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getItemsInfo()
            if(call.isSuccessful){
                val response = call.body()
                val responseObject = response?.asJsonObject
                val result = responseObject?.get("result")?.asJsonObject
                val itemsArray = result?.get("items")?.asJsonArray
                for (i in 0 until itemsArray?.size()!!) {
                    val item = itemsArray.get(i).asJsonObject
                    //filter list
                }
            }else{
                Log.d("error","error")
            }
        }
    }

    private fun getHero(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getHeroesInfo()
            if(call.isSuccessful){

            }else{
                Log.d("error","error")
            }
        }
    }

    //save preferences
    private fun savePreferences(key:String,value:String){
/*  val sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(key,value)
    editor.apply()*/
    }
}