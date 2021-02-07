package training.androidkotlin.weather.weather

data class WeatherObject(val description: String,
                   val temperature: Float,
                   val humidity: Int,
                   val pressure: Int,
                   val iconUrl: String)
