package com.example.myapplication1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

class SimpleDialog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_dialog)

        val closebutton:Button = findViewById(R.id.close)
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE) //去掉标题

        closebutton.setOnClickListener {
            finish()
        }



        //override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    //        return activity?.let {
    //            val builder = AlertDialog.Builder(it)
    //           builder.setMessage(R.string.simple_dialog)
    //               .setNeutralButton(R.string.close) { _, _ -> }
    //           builder.create()
    //       } ?: throw IllegalStateException("Activity cannot be null")
    }
}