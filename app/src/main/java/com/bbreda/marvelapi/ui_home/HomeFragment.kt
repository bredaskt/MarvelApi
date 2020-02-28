package com.bbreda.marvelapi.ui_home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.bbreda.marvelapi.R
import com.bbreda.marvelapi.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(), MarvelCharacterAdapter.AdapterListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var viewModel: HomeViewModel = HomeViewModel()
    private lateinit var adapter: MarvelCharacterAdapter

    private var offset: Int = 150

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = MarvelCharacterAdapter(mutableListOf(), this)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        recycler_view_characters.layoutManager = GridLayoutManager(context, 2)
        recycler_view_characters.itemAnimator = DefaultItemAnimator()
        recycler_view_characters.adapter = adapter

        viewModel.getHeroes(offset)
        configureObservers()
    }

    fun configureObservers(){
        showLoading()

        viewModel.getHeroesList().observe(this, Observer { heroes ->
            heroes?.let {
                adapter.updateList(heroes.data.results as MutableList<Result>)
                if (heroes.data.count > 0){
                    //updateOffset()
                }
            }
        })
        hideLoading()
    }

    private fun updateOffset() {
        offset += 100
        viewModel.getHeroes(offset)
    }

    fun showLoading(){
        progressbar_home.visibility = View.VISIBLE
    }

    fun hideLoading(){
        progressbar_home.visibility = View.GONE
    }

    override fun showDetails(char: Result) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra("image", char.thumbnail.path + "." + char.thumbnail.extension)
        intent.putExtra("name", char.name)
        intent.putExtra("description", char.description)
        intent.putExtra("comics", char.comics.available.toString())
        intent.putExtra("series", char.series.available.toString())
        intent.putExtra("stories", char.stories.available.toString())
        intent.putExtra("events", char.events.available.toString())

        char.urls.forEach{
            intent.putExtra(it.type, it.url)
        }


        startActivity(intent)
        Log.d("CLick", "clicado no item")
    }

}
