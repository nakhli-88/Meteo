package training.androidkotlin.weather.weather

import OpenWheatherMappper.WheatherMapper
import WeatherWrapper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.meteo.App
import com.example.meteo.R
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment : Fragment() {

    companion object {
        val CITY_NAME_EXTRA = "com.example.meto.CITY_NAME"
        fun newInstance() = WeatherFragment()
    }

    private lateinit var cityName: String

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var city: TextView
    private lateinit var weatherIcon: ImageView
    private lateinit var weatherDescription: TextView
    private lateinit var temperature: TextView
    private lateinit var humidity: TextView
    private lateinit var pressure: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        refreshLayout = view.findViewById(R.id.swipe_refresh)
        city = view.findViewById(R.id.city)
        weatherIcon = view.findViewById(R.id.weather_icon)
        weatherDescription = view.findViewById(R.id.weather_description)
        temperature = view.findViewById(R.id.temperature)
        humidity = view.findViewById(R.id.humidity)
        pressure = view.findViewById(R.id.pressure)

        refreshLayout.setOnRefreshListener { refreshWeather() }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity?.intent!!.hasExtra(CITY_NAME_EXTRA)) {
            cityName = activity!!.intent.getStringExtra(CITY_NAME_EXTRA)
            Log.d("toto", cityName)
        }
        getWeather(cityName)
    }

    fun getWeather(cityName: String) {
        this.cityName = cityName
        if (!refreshLayout.isRefreshing) {
            refreshLayout.isRefreshing = true
        }

        val getWeather = App.weatherService.getWeather("$cityName,fr")
        getWeather.enqueue(object : Callback<WeatherWrapper> {
            override fun onResponse(
                call: Call<WeatherWrapper>,
                response: Response<WeatherWrapper>
            ) {
                Log.d("toto", response.body().toString())
                response.body()?.let {
                    updateUi(WheatherMapper(it))
                }
                refreshLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                Log.d("toto", "could not get response $t")

                refreshLayout.isRefreshing = false

            }
        })
    }

    fun updateUi(weather: WeatherObject) {
        Picasso.get()
            .load(weather.iconUrl)
            .placeholder(R.drawable.ic_cloud_off_black_24dp)
            .into(weatherIcon)

        weatherDescription.text = weather.description
        temperature.text =
            getString(R.string.weather_temperature_value, weather.temperature.toInt())
        humidity.text = getString(R.string.weather_humidity_value, weather.humidity)
        pressure.text = getString(R.string.weather_pressure_value, weather.pressure)
    }

    private fun refreshWeather() {
        getWeather(cityName)
    }
}