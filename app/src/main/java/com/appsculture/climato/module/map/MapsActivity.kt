package com.appsculture.climato.module.map

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.appsculture.climato.R
import com.appsculture.climato.model.Forecast
import com.appsculture.climato.utils.WeatherDataFormatter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.AndroidInjection
import javax.inject.Inject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    @Inject
    lateinit var mapViewModelFactory: MapViewModelFactory

    @Inject
    lateinit var formatter: WeatherDataFormatter

    private lateinit var mapViewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        AndroidInjection.inject(this)

        initViews()
        initViewModel()
        initMap()
    }

    override fun onResume() {
        super.onResume()
        mapViewModel.loadSavedForecasts()
    }


    private fun initViewModel() {
        mapViewModel =
                ViewModelProviders.of(this, mapViewModelFactory).get(MapViewModel::class.java)

        mapViewModel.allForecastsResult.observe(this, Observer<List<Forecast>> {
            it?.let {
                renderMarkers(it!!)
            }
        })

        mapViewModel.allForecastsError.observe(this, Observer<String> {
            Toast.makeText(
                this,
                resources.getString(R.string.forecast_loading_error) + it,
                Toast.LENGTH_LONG
            ).show()
        })
    }


    private fun initViews() {

    }

    private fun initMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    private fun renderMarkers(forecasts: List<Forecast>) {
        //Clears map markers
        map.clear();

        val infoWindow = MapMarkerInfo(this, formatter)
        map.setInfoWindowAdapter(infoWindow)

        for (forecast in forecasts) {
            var markerOption = MarkerOptions() as MarkerOptions
            forecast.coordinate.let {
                markerOption.position(LatLng(it!!.lat, it.lon))
                markerOption.title(forecast.name)
                var marker = map.addMarker(markerOption) as Marker
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
                marker.tag = forecast
            }

        }
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    forecasts.get(0).coordinate!!.lat,
                    forecasts.get(0).coordinate!!.lon
                ), 10f
            )
        )


    }

}
