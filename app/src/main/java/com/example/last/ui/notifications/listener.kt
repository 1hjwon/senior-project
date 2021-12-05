package com.example.last.ui.notifications

import android.content.Context
import android.view.View
import android.widget.Toast

interface listener: View.OnClickListener {
    //val context = context
    //val view = view
    override fun onClick(v: View?) {
        /*v?.setOnClickListener {
            Toast.makeText(context, "리스너~?", Toast.LENGTH_SHORT)

        }*/
    }
}