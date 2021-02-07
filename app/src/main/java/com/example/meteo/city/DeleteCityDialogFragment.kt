package com.example.meteo.city

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.meteo.R

class DeleteCityDialogFragment : DialogFragment() {

    interface actionDeleteCity {
        fun onPositiveAction()
        fun onNegativeAction()
    }

    companion object {
        val CITY_NAME_EXTRA = "com.example.meteo.CITY_NAME"
        lateinit var mCityName: String
        fun newInstance(cityName: String): DeleteCityDialogFragment {
            val fragment = DeleteCityDialogFragment()
//            val bundle = Bundle()
//            bundle.putString(CITY_NAME_EXTRA, cityName)
//            fragment.arguments = bundle
                        //ouu bien
            fragment.arguments = Bundle().apply {
                putString(CITY_NAME_EXTRA, cityName)
            }
            return fragment
        }
    }

    var listener: actionDeleteCity? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mCityName = arguments!!.getString(CITY_NAME_EXTRA, "")

        val builder = AlertDialog.Builder(context!!)
        builder.setMessage(getString(R.string.title_dialog_delete) + " " + mCityName)
            .setPositiveButton(getString(R.string.title_dialog_delete)) { dialogInterface, i ->
                listener?.onPositiveAction()
            }
            .setNegativeButton(getString(R.string.cancel_create_city)) { dialogInterface, i ->
                dialog?.cancel()
                listener?.onNegativeAction()
            }
        return builder.create()
    }
}