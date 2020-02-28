package com.bbreda.marvelapi.api

import com.bbreda.marvelapi.models.ReturnData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    fun getCharacters(
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        offset: String
    ): Call<ReturnData>

}

interface OnGetMarvelCallback {
    fun onSuccess(marvelResponse: ReturnData)
    fun onError()
}