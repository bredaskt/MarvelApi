package com.bbreda.marvelapi.repository

import androidx.lifecycle.LiveData
import com.bbreda.marvelapi.api.OnGetMarvelCallback
import com.bbreda.marvelapi.models.ReturnData

interface MarvelRepository{
    //name: String, apiKey: String, ts: String, hash: String,
    fun getCharacter(offset: Int, callback: OnGetMarvelCallback)
    fun getHeroes(): LiveData<ReturnData>
}
