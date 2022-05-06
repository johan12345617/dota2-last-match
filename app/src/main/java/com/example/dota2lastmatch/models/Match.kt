package com.example.dota2lastmatch.models

import com.google.gson.annotations.SerializedName

data class Match(
    @SerializedName("match_id") var matchId: String,
    @SerializedName("player_slot") var playerSlot: Int,
    @SerializedName("radiant_win") var radiantWin: Boolean,
    @SerializedName("duration") var duration: Int,
    @SerializedName("game_mode") var gameMode: Int,
    @SerializedName("lobby_type") var lobbyType: Int,
    @SerializedName("hero_id") var heroId: Int,
    @SerializedName("start_time") var startTime: Int,
    @SerializedName("version") var version: Int,
    @SerializedName("kills") var kills: Int,
    @SerializedName("deaths") var deaths: Int,
    @SerializedName("assists") var assists: Int,
    @SerializedName("skill") var skill: String,
    @SerializedName("xp_per_min") var xpPerMin: Int,
    @SerializedName("gold_per_min") var goldPerMin: Int,
    @SerializedName("hero_damage") var heroDamage: Int,
    @SerializedName("hero_healing") var heroHealing: Int,
    @SerializedName("last_hits") var lastHits: Int,
    @SerializedName("lane") var lane: Int,
    @SerializedName("lane_role") var laneRole: Int,
    @SerializedName("is_roaming") var isRoaming: Boolean,
    @SerializedName("cluster") var cluster: Int,
    @SerializedName("leaver_status") var leaverStatus: Int,
    @SerializedName("party_size") var partySize: Int)