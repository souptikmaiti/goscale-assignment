package com.souptik.maiti.goscaleassignment.utils

import android.content.Context
import android.graphics.PorterDuff
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.souptik.maiti.goscaleassignment.R

object Toaster {
    fun showShort(context: Context, text: CharSequence) {
        val toast = android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT)

        toast.view.background.setColorFilter(
            ContextCompat.getColor(context, R.color.black), PorterDuff.Mode.SRC_IN
        )

        val textView = toast.view.findViewById(android.R.id.message) as TextView
        textView.setTextColor(ContextCompat.getColor(context, R.color.white))

        toast.show()
    }

    fun showLong(context: Context, text: CharSequence) {
        val toast = android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_LONG)

        toast.view.background.setColorFilter(
            ContextCompat.getColor(context, R.color.black), PorterDuff.Mode.SRC_IN
        )

        val textView = toast.view.findViewById(android.R.id.message) as TextView
        textView.setTextColor(ContextCompat.getColor(context, R.color.white))

        toast.show()
    }
}