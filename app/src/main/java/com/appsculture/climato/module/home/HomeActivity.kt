package com.appsculture.climato.module.home

import android.app.ProgressDialog
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
import com.appsculture.climato.module.detail.DetailActivity
import com.appsculture.climato.module.map.MapsActivity
import com.appsculture.climato.utils.WeatherDataFormatter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), View.OnClickListener,
    ForecastListAdapter.ItemClickListener {

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    @Inject
    lateinit var formatter: WeatherDataFormatter

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: ForecastListAdapter
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        AndroidInjection.inject(this)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        initRecyclerView()
        btnSearch.setOnClickListener(this)
        fabMapView.setOnClickListener(this)
        progressDialog = ProgressDialog(this)
    }

    private fun initRecyclerView() {
        adapter = ForecastListAdapter(ArrayList(), formatter, this)
        layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.rvForecast)
        recyclerView.layoutManager = layoutManager
    }

    private fun initViewModel() {
        homeViewModel =
                ViewModelProviders.of(this, homeViewModelFactory).get(HomeViewModel::class.java)

        homeViewModel.allForecastsResult.observe(this, Observer<List<Forecast>> {
            hideLoader()
            it?.let {
                adapter.addForecastToList(it)
                recyclerView.adapter = adapter
            }
        })

        homeViewModel.allForecastsError.observe(this, Observer<String> {
            hideLoader()
            Toast.makeText(
                this,
                resources.getString(R.string.forecast_loading_error) + it,
                Toast.LENGTH_LONG
            ).show()
        })

        homeViewModel.forecastSearchResult.observe(this, Observer<Forecast> {
            hideLoader()
            it?.let {
                showWeatherDetails(it)
            }

        })

        homeViewModel.forecastSearchError.observe(this, Observer<String> {
            hideLoader()
            Toast.makeText(
                this,
                resources.getString(R.string.forecast_loading_error) + it,
                Toast.LENGTH_LONG
            ).show()

        })

        homeViewModel.loadSavedForecasts()
    }

    override fun onClick(v: View?) {
        when (v?.getId()) {
            R.id.fabMapView -> switchToMapView()
            R.id.btnSearch -> {
                showLoader()
                homeViewModel.searchForecast(etSearch.text.toString())
            }
        }
    }

    private fun switchToMapView() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    private fun showWeatherDetails(forecast: Forecast) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.FORECAST, forecast)
        startActivity(intent)
    }

    private fun showLoader() {
        progressDialog = ProgressDialog(this)
        progressDialog.setIndeterminateDrawable(getDrawable(R.drawable.progress_loader))
        progressDialog.setMessage(getString(R.string.searching))
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    private fun hideLoader() {
        progressDialog.hide()
    }

    override fun clicked(item: Forecast) {
        showWeatherDetails(item)
    }

}
