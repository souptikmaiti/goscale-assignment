package com.souptik.maiti.goscaleassignment.utils

import android.content.Context

object Toaster {
    fun showShort(context: Context, text: CharSequence) {
        val toast = android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT)
        toast.show()
    }

    fun showLong(context: Context, text: CharSequence) {
        val toast = android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_LONG)
        toast.show()
    }
}