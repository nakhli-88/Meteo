package com.example.meteo

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.meteo.city.City

private const val DATABASE_NAME = "weather.db"
private const val VERSION_NUMBER = 1
private const val TABLE_CITY_NAME = "city"
private const val CITY_KEY_ID = "id"
private const val CITY_KEY_NAME = "name"
private const val CITY_CREATE_TABlE = """
    CREATE TABLE $TABLE_CITY_NAME (
    $CITY_KEY_ID INTEGER PRIMARY KEY , 
    $CITY_KEY_NAME TEXT)
    """

class DataBase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION_NUMBER) {

    val TAG = DataBase::class.java.simpleName
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CITY_CREATE_TABlE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun AddCity(city: City): Boolean {
        val values = ContentValues()
        values.put(CITY_KEY_NAME, city.name)
        Log.d(TAG, "City created = $values")
        val id = writableDatabase.insert(TABLE_CITY_NAME, null, values)
        city.id = id
        return id > 0
    }

    fun deleteCity(city: City): Boolean {
        val deleteCount = writableDatabase.delete(TABLE_CITY_NAME,
            "$CITY_KEY_ID = ?",
            arrayOf("${city.id}"))
        return deleteCount == 1
    }

    fun getCitysCount(): Int =
        DatabaseUtils.queryNumEntries(readableDatabase, TABLE_CITY_NAME).toInt()

    fun getAllCity(): MutableList<City> {
        val cityList = mutableListOf<City>()
        val cursor =
            readableDatabase.rawQuery("SELECT * FROM $TABLE_CITY_NAME", null).use { cursor ->
                while (cursor.moveToNext()) {
                    val city = City(
                        cursor.getLong(cursor.getColumnIndex(CITY_KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(CITY_KEY_NAME))
                    )
                    cityList.add(city)
                }
            }
        return cityList
    }
}