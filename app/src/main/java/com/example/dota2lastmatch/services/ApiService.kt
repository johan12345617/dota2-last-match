package com.example.dota2lastmatch.services

import com.example.dota2lastmatch.models.Match
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getIdMatches(@Url url:String): Response<List<Match>>
    @GET
    suspend fun getMatchDetails(@Url url:String): Response<JsonElement>
}