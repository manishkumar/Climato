package kt.appsculture.com.weatherforecast.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kt.appsculture.com.weatherforecast.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val launchMapsTextView = findViewById(R.id.launchMapsTextView) as TextView

        launchMapsTextView.setOnClickListener(onclickListener);
    }

    val onclickListener = View.OnClickListener { view ->
        when (view.getId()) {
            R.id.launchMapsTextView -> launchMapActivity()
        }
    }

    private fun launchMapActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
}
