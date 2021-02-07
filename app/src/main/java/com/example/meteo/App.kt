package com.example.meteo

import OpenWheatherMappper.OpenWeatherService
import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    companion object {
        lateinit var instance: App
        val dataBase: DataBase by lazy {
            DataBase(instance)
        }

        val okHttpLogger = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val retorphit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .client(okHttpLogger)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherService = retorphit.create(OpenWeatherService::class.java)
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}