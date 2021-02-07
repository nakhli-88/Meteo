package com.example.meteo.city

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.meteo.R

class CreateCityDialogFragment : DialogFragment() {

    interface actionAddCity {
        fun onPositiveAction(cityName: String)
        fun onNegativeAction()
    }

    var listener: actionAddCity? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val input = EditText(context)
        with(input) {
            inputType = InputType.TYPE_CLASS_TEXT
            hint = context.getString(R.string.create_hint)
        }

        val builder = AlertDialog.Builder(context!!)
        builder.setMessage(getString(R.string.title_dialog_create))
                .setView(input)
                .setPositiveButton(getString(R.string.yes_create_city)) { dialogInterface, i ->
                    listener?.onPositiveAction(input.text.toString())
                }
                .setNegativeButton(getString(R.string.cancel_create_city)) { dialogInterface, i ->
                    dialog?.cancel()
                    listener?.onNegativeAction()
                }
        return builder.create()
    }
}