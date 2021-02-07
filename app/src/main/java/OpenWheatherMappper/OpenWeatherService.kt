package OpenWheatherMappper

import WeatherWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val KEY_API = "a2010d37bd3573c0ed7b6f293790532a"

interface OpenWeatherService {
    @GET("data/2.5/weather?units=metric&lang=fr")
    fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = KEY_API
    ): Call<WeatherWrapper>
}