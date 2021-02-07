package OpenWheatherMappper

import WeatherWrapper
import training.androidkotlin.weather.weather.WeatherObject

fun WheatherMapper(weatherWrapper: WeatherWrapper): WeatherObject {
    val weatherDataFirst = weatherWrapper.weather.first()
    return WeatherObject(
        description = weatherDataFirst.description,
        temperature = weatherWrapper.main.temp,
        humidity = weatherWrapper.main.humidity,
        pressure = weatherWrapper.main.pressure,
        iconUrl = "https://openweathermap.org/img/w/${weatherDataFirst.icon}.png"
    )
}

