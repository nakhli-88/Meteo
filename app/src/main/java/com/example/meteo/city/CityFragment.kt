package com.example.meteo.city

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meteo.App
import com.example.meteo.DataBase
import com.example.meteo.R

class CityFragment : Fragment(), CityAdapter.CityClickListener {

    interface CityListener {
        fun onCitySelected(city: City)
    }

    var cityListener: CityListener? = null

    lateinit var db: DataBase
    lateinit var listCityDB: MutableList<City>
    lateinit var citiesAdapter: CityAdapter
    lateinit var recycleListCities: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        db = App.dataBase
        listCityDB = mutableListOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listCityDB = App.dataBase.getAllCity()
        citiesAdapter = CityAdapter(listCityDB, this)
        recycleListCities.adapter = citiesAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_city, container, false)
        recycleListCities = view.findViewById(R.id.cities_recycler_view)
        recycleListCities.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_city, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_create_city -> {
                showCreateCity()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showCreateCity() {
        val creatCityFragment = CreateCityDialogFragment()
        creatCityFragment.listener = object : CreateCityDialogFragment.actionAddCity {
            override fun onPositiveAction(cityName: String) {
                Log.d("toto", cityName)
                saveCity(City(cityName))
            }

            override fun onNegativeAction() {
            }
        }
        fragmentManager?.let { creatCityFragment.show(it, "") }
    }


    private fun showDeleteDialog(city: City) {
        val deleteCityDialogFragment = DeleteCityDialogFragment.newInstance(city.name)
        deleteCityDialogFragment.listener = object : DeleteCityDialogFragment.actionDeleteCity {
            override fun onPositiveAction() {
                DeletCity(city)
            }

            override fun onNegativeAction() {
            }
        }
        fragmentManager?.let { deleteCityDialogFragment.show(it, "") }
    }

    private fun DeletCity(city: City) {
        if (db.deleteCity(city)) {
            listCityDB.remove(city)
            citiesAdapter.notifyDataSetChanged()
            Toast.makeText(context, "${city.name} has been deleted", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveCity(city: City) {
        if (db.AddCity(city)) {
            listCityDB.add(city)
            citiesAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(context, getString(R.string.msg_toast_addcity), Toast.LENGTH_LONG).show()
        }
    }

    override fun onDeleteListener(city: City) {
        showDeleteDialog(city)
    }

    override fun onSelectListener(city: City) {
        cityListener?.onCitySelected(city)
    }

}