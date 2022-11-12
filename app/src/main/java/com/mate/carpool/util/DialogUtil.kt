package com.mate.carpool.util

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.mate.carpool.R

object DialogUtil {
    
    fun showDialog(context: Context) {
        MaterialDialog(context).show {
            customView(R.layout.dialog_custom)
            val customView = getCustomView()
        }
    }
}