  package com.example.meteo.city

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meteo.R
import kotlinx.android.synthetic.main.fragment_weather.*
import training.androidkotlin.weather.weather.WeatherActivity
import training.androidkotlin.weather.weather.WeatherFragment.Companion.CITY_NAME_EXTRA

class CityActivity : AppCompatActivity(), CityFragment.CityListener {

    lateinit var cityFragment: CityFragment
    var currentCity: City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        cityFragment = supportFragmentManager.findFragmentById(R.id.city_fragment) as CityFragment
        cityFragment.cityListener = this
    }

    override fun onCitySelected(city: City) {
        currentCity = city
        val intent = Intent(baseContext, WeatherActivity::class.java)
        intent.putExtra(CITY_NAME_EXTRA, city.name)
        startActivity(intent)
    }
}