package com.app.testexample.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class UtilFunctions {

    companion object {
        fun showToastShort(msg: String?, context: Context) {
            if (!(msg.isNullOrEmpty())) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }

        fun showToastLong(msg: String?, context: Context) {
            if (!(msg.isNullOrEmpty())) {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            }
        }

        fun showSnackShort(msg: String?, view: View) {
            if (!(msg.isNullOrEmpty())) {
                val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                snackBar.apply { setAction("Cancel") { dismiss() } }.show()
            }
        }

        fun showSnackLong(msg: String?, view: View) {
            if (!(msg.isNullOrEmpty())) {
                val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                snackBar.apply { setAction("Cancel") { dismiss() } }.show()
            }
        }
    }
}
