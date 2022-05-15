package com.example.dota2lastmatch.services

import com.example.dota2lastmatch.BuildConfig
import com.example.dota2lastmatch.models.Match
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

const val STEAM_API_KEY = BuildConfig.STEAM_KEY


interface ApiService {


    @GET
    suspend fun getIdMatches(@Url url:String): Response<List<Match>>
    @GET
    suspend fun getMatchDetails(@Url url:String): Response<JsonElement>
    @GET("https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v1/?key=$STEAM_API_KEY")
    suspend fun getHeroesInfo(): Response<JsonElement>
    @GET("https://api.steampowered.com/IEconDOTA2_570/GetGameItems/v0001/?key=$STEAM_API_KEY")
    suspend fun getItemsInfo(): Response<JsonElement>
    @GET
    suspend fun getItemsImage(@Url url:String): Response<String>
}

