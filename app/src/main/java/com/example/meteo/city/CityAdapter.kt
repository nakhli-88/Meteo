package com.example.meteo.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.meteo.R

class CityAdapter(
    val listCities: List<City>,
    val listener: CityClickListener
) :
    RecyclerView.Adapter<CityAdapter.MyViewHolder>(), View.OnClickListener {

    interface CityClickListener {
        fun onDeleteListener(city:City)
        fun onSelectListener(city:City)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listCities.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val city = listCities[position]
        with(holder) {
            nameCity.text = city.name
            carView.tag = city
            carView.setOnClickListener(this@CityAdapter)
            deleteCity.tag = city
            deleteCity.setOnClickListener(this@CityAdapter)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameCity = itemView.findViewById<TextView>(R.id.name)
        val carView = itemView.findViewById<CardView>(R.id.card_view)
        val deleteCity = itemView.findViewById<ImageView>(R.id.delete)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.card_view -> listener.onSelectListener(v.tag as City)
            R.id.delete -> listener.onDeleteListener(v.tag as City)
        }
    }
}

