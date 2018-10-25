package com.appsculture.climato.module.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.appsculture.climato.R
import com.appsculture.climato.model.Forecast
import com.appsculture.climato.module.map.MapsActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private var adapter = ForecastListAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        AndroidInjection.inject(this)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        initRecyclerView()
        btnAPI.setOnClickListener(this)
        btnDB.setOnClickListener(this)
        fabMapView.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.rvForecast)
        recyclerView.layoutManager = layoutManager
    }


    private fun initViewModel() {
        homeViewModel = ViewModelProviders.of(this, homeViewModelFactory).get(
                HomeViewModel::class.java)

        homeViewModel.forecastsResult().observe(this,
                Observer<List<Forecast>> {
                    val forecasts = it.let { it!! }
                    adapter.addForecastToList(forecasts)
                    recyclerView.adapter = adapter
                })

        homeViewModel.forecastsError().observe(this, Observer<String> {
            if (it != null) {
                Toast.makeText(this, resources.getString(R.string.forecast_loading_error) + it,
                        Toast.LENGTH_SHORT).show()
            }
        })

        homeViewModel.forecastsLoader().observe(this, Observer<Boolean> {
            //if (it == false) progressBar.visibility = View.GONE
        })
    }

    override fun onClick(v: View?) {
        when (v?.getId()) {
            R.id.fabMapView -> switchToMapView()
            R.id.btnAPI -> homeViewModel.searchForecast("Delhi")
            R.id.btnDB -> homeViewModel.loadSavedForecasts()
        }
    }

    private fun switchToMapView() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }


}
