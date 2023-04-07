package com.grigorev.and3

import android.app.Activity
import android.app.AlertDialog

class LoadingDialog(private val activity: Activity) {

    private lateinit var dialog: AlertDialog

    fun startDialog() {
        val builder = AlertDialog.Builder(activity)

        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.loading, null))
        builder.setCancelable(true)

        dialog = builder.create()
        dialog.show()
    }

    fun dismissDialog() {
        dialog.dismiss()
    }

}