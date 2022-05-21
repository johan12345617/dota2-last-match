package com.example.dota2lastmatch.models

import android.util.Log
import com.example.dota2lastmatch.services.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LastMatch(){

    var heroImage: String = ""
    var itemListImage: MutableList<String> = mutableListOf()
    var heroName: String = ""
    var modeGame: String = ""
    var timeGame: String = "" //seconds
    var gameResult: String = ""
    var kdaInfo: String = "" //kill/death/assisted = kda

    init {
        getMatchInfo("336204238")
    }


    //get retrofit instance
    private fun getRetrofitOpenDota(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.opendota.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //get dota 2 game
    private fun getMatchInfo(userId:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofitOpenDota().create(ApiService::class.java).getIdMatches("players/$userId/"+
                    "recentMatches")
            val matches = call.body()
            if(call.isSuccessful){
                matches?.get(0)?.matchId?.let { getItemsInfo(it, matches[0].heroId) }
                timeGame = matches?.get(0)?.duration.toString()
                kdaInfo = matches?.get(0)?.kills.toString() + "/" + matches?.get(0)?.deaths.toString() + "/" +
                        matches?.get(0)?.assists.toString()
                modeGame = matches?.get(0)?.let { getLobbyName(it.lobbyType.toString()) }.toString()
                getHeroImages(matches?.get(0)?.heroId.toString())
            }else{
                //show error
            }
        }
    }

    private fun getItemsInfo(matchId:String,heroId:Int){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofitOpenDota().create(ApiService::class.java).getMatchDetails("matches/$matchId")
            if(call.isSuccessful){
                val matches = call.body()
                val matchesObject = matches?.asJsonObject
                val players = matchesObject?.get("players")
                val isRadiantVictory = matchesObject?.get("radiant_win")?.asBoolean
                val playersArray = players?.asJsonArray
                for (i in 0 until playersArray?.size()!!) {
                    val player = playersArray.get(i).asJsonObject
                    if(player.get("hero_id").toString() == heroId.toString()){
                        val listItems = listOf( player.get("item_0").toString(),
                            player.get("item_1").toString(),
                            player.get("item_2").toString(),
                            player.get("item_3").toString(),
                            player.get("item_4").toString(),
                            player.get("item_5").toString(),
                            player.get("backpack_0").toString(),
                            player.get("backpack_1").toString(),
                            player.get("backpack_2").toString(),
                            player.get("item_neutral").toString())
                        getItemsName(listItems)
                        player.get("isRadiant").asBoolean
                        gameResult = if (isRadiantVictory == true) {
                            if(player.get("isRadiant").asBoolean) {
                                "Win"
                            }else{
                                "Lose"
                            }
                        } else {
                            if(player.get("isRadiant").asBoolean) {
                                "Lose"
                            }else{
                                "Win"
                            }
                        }
                        break
                    }
                }
            }else{
                Log.d("error",call.errorBody().toString())
            }
        }
    }

    private fun getItemsName(items:List<String>){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofitOpenDota().create(ApiService::class.java).getItemsInfo()
            if(call.isSuccessful){
                val response = call.body()
                val responseObject = response?.asJsonObject
                val result = responseObject?.get("result")?.asJsonObject
                val itemsArray = result?.get("items")?.asJsonArray
                val nameItems = itemsArray?.filter { it.asJsonObject.get("id").toString() in items }
                for (item in nameItems!!){
                    val name = item.asJsonObject.get("name").toString().substringAfter("item_").replace("\"","")
                    val itemUrlImage = "https://cdn.dota2.com/apps/dota2/images/" +"items/"+name+"_lg.png"
                    itemListImage.add(itemUrlImage)
                }
                Log.d("itemListImage",itemListImage.toString())
            }else{
                Log.d("error","error")
            }
        }
    }

    private fun getHeroImages(id:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofitOpenDota().create(ApiService::class.java).getHeroesInfo()
            if(call.isSuccessful){
                val response = call.body()
                val responseObject = response?.asJsonObject
                val result = responseObject?.get("result")?.asJsonObject
                val heroes = result?.get("heroes")?.asJsonArray
                val heroesArray = heroes?.asJsonArray
                for (i in 0 until heroesArray?.size()!!) {
                    val hero = heroesArray.get(i).asJsonObject
                    if(hero.get("id").toString() == id){
                        val heroNameParsed = hero.get("name").toString().substringAfter("npc_dota_hero_").replace("\"","")
                        val heroUrlImage = "https://cdn.dota2.com/apps/dota2/images/heroes/"+heroNameParsed+"_full.png"
                        heroImage = heroUrlImage
                        heroName = heroNameParsed
                        break
                    }
                }
            }else{
                Log.d("error","error")
            }
        }
    }

    private fun getLobbyName(lobbyId: String): String {
        return when (lobbyId){
            "-1" -> "Invalid"
            "0" -> "Public matchmaking"
            "1" -> "Practice"
            "2" -> "Tournament"
            "3" -> "Tutorial"
            "4" -> "Co-op with bots"
            "5" -> "Team match"
            "6" -> "Solo Queue"
            "7" -> "Ranked"
            "8" -> "1v1 Mid"
            else -> "Invalid"
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