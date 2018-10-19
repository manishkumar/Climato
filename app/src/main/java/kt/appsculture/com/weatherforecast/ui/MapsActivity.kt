package kt.appsculture.com.weatherforecast.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kt.appsculture.com.weatherforecast.R
import kt.appsculture.com.weatherforecast.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val cityList = arrayListOf<City>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        getCityList();
        val customInfoWindow = CustomMarkerInfo(this)
        googleMap.setInfoWindowAdapter(customInfoWindow)

        for (item in cityList) {
            val forecast = getWeatherConditionForCity(item);
            var markerOption = MarkerOptions() as MarkerOptions
            markerOption.position(LatLng(item.coordinates.latitude, item.coordinates.longitude))
            markerOption.title(item.name)
            markerOption.snippet(item.country)


            // Add a marker in City
            var marker = mMap.addMarker(markerOption) as Marker
            marker.tag = forecast
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(cityList[0].coordinates.latitude, cityList[0].coordinates.longitude), 10f))
    }

    private fun getWeatherConditionForCity(item: City): Forecast {
        //Get Details For Each city here

        when (item.id) {
            Integer(1) -> return Forecast(System.currentTimeMillis(), Temp(21.5, 29.5, 1017.0, 40), arrayListOf<Condition>(), Wind(11.0, 4.0))
            Integer(2) -> return Forecast(System.currentTimeMillis(), Temp(25.5, 37.8, 1020.0, 20), arrayListOf<Condition>(), Wind(15.0, 6.0))
            Integer(3) -> return Forecast(System.currentTimeMillis(), Temp(26.0, 33.4, 1030.0, 60), arrayListOf<Condition>(), Wind(17.0, 7.0))
            Integer(4) -> return Forecast(System.currentTimeMillis(), Temp(16.8, 24.6, 1040.0, 33), arrayListOf<Condition>(), Wind(14.0, 2.0))
            Integer(5) -> return Forecast(System.currentTimeMillis(), Temp(31.0, 42.6, 1050.0, 55), arrayListOf<Condition>(), Wind(21.0, 3.0))
            else -> return return Forecast(System.currentTimeMillis(), Temp(21.5, 29.5, 1017.0, 40), arrayListOf<Condition>(), Wind(11.0, 4.0))
        }
    }

    private fun getCityList() {
        //Fetch data from local storage.

        val chandniChowk = City(Integer(1), "Chandni Chowk", Coordinates(77.2303, 28.6506), "India");
        val lajpatNagar = City(Integer(2), "Lajpat Nagar", Coordinates(77.2364, 28.5705), "India");
        val sarojni = City(Integer(3), "Sarojni Nagar", Coordinates(77.1990, 28.5757), "India");
        val cp = City(Integer(4), "Connaught Place", Coordinates(77.2167, 28.6315), "India");
        val saket = City(Integer(5), "Saket", Coordinates(77.2015, 28.5205), "India");

        cityList.add(chandniChowk)
        cityList.add(lajpatNagar)
        cityList.add(sarojni)
        cityList.add(cp)
        cityList.add(saket)
    }
}
