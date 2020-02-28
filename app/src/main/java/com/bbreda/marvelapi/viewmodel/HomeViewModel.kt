package com.bbreda.marvelapi.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bbreda.marvelapi.api.OnGetMarvelCallback
import com.bbreda.marvelapi.models.ReturnData
import com.bbreda.marvelapi.repository.MarvelRepository
import com.bbreda.marvelapi.repository.MarvelRepositoryImpl
import com.bbreda.marvelapi.ui.HomeActivity

class HomeViewModel(private val repository: MarvelRepository = MarvelRepositoryImpl())
    : ViewModel() {

    private val heroesList: MutableLiveData<ReturnData> = MutableLiveData()
    fun getHeroesList() = heroesList

    fun verifyConnectivity(activity: HomeActivity): Boolean {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun getHeroes(offset: Int) {
        repository.getCharacter(offset, object : OnGetMarvelCallback {

            override fun onSuccess(response: ReturnData) {
                Log.d("Reponse", "It's Ok!!!")
                heroesList.value = response
            }

            override fun onError() {
                Log.e("ErrorViewModel", "Error in viewmodel call")
            }
        })
    }
}

