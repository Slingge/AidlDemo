package com.lxkj.aidldemo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ProgressBar

@SuppressLint("StaticFieldLeak")
/**
 * Created by Slingge on 2018/2/7 0007.
 */
object Progress {

    private var builder: AlertDialog? = null

    fun dialog(context: Context) {
        if (builder == null) {
            builder = AlertDialog.Builder(context).create()
            builder!!.show()
            val view = LayoutInflater.from(context).inflate(R.layout.progress, null)
            builder!!.window.setContentView(view)
        } else {
            builder!!.show()
        }
    }


    fun dissBuilder() {
        if (builder != null && builder!!.isShowing) {
            builder!!.dismiss()
            builder = null
        }
    }


}