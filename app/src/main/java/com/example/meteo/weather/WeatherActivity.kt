package training.androidkotlin.weather.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, WeatherFragment.newInstance())
                .commit()
    }
}