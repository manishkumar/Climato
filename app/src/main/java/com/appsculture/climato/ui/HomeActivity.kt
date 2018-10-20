package com.appsculture.climato.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.appsculture.climato.R
import com.appsculture.climato.ui.map.MapsActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
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
