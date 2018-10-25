package com.appsculture.climato.module.home

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.appsculture.climato.R
import com.appsculture.climato.model.Forecast
import com.appsculture.climato.module.detail.DetailActivity
import com.appsculture.climato.module.map.MapsActivity
import com.appsculture.climato.module.settings.SettingsActivity
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

    override fun onResume() {
        super.onResume()
        homeViewModel.loadSavedForecasts()
    }

    private fun initViews() {
        initRecyclerView()
        configureNavigationDrawer()
        configureToolbar()
        btnSearch.setOnClickListener(this)
        fabMapView.setOnClickListener(this)
        progressDialog = ProgressDialog(this)
    }

    private fun configureNavigationDrawer() {
        navigation.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            drawer_layout.closeDrawers();
            when (menuItem.itemId) {
                R.id.item_settings -> {
                    showSettings()
                    true
                }
            }

            return@OnNavigationItemSelectedListener false
        })
    }

    private fun configureToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            drawer_layout.openDrawer(GravityCompat.START)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
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

    private fun showSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
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
