package com.example.myapplication1

import android.app.Dialog
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer as LifecycleObserver



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val StartB:Button = findViewById(R.id.a_start_b)
        val StartC:Button = findViewById(R.id.a_start_c)
        val FinishA:Button = findViewById(R.id.finish_a)
        val DialogButton:Button = findViewById(R.id.a_dialog)


        //绑定Observer
        lifecycle.addObserver(
            Observer(
                resources.getString(R.string.A_text),
                findViewById(R.id.a_method_list),
                findViewById(R.id.a_status)
            )
        )

        //添加点击按钮的事件
        StartB.setOnClickListener{
            val intent = Intent(this, ActivityB::class.java)
            startActivity(intent)
        }
        
        StartC.setOnClickListener {
            val intent = Intent(this, ActivityC::class.java)
            startActivity(intent)
            //stopupdate.update("%s.onStop()\n", "Stopped")
        }

        FinishA.setOnClickListener {
            finish()
        }

        DialogButton.setOnClickListener {
            val intent = Intent(this, SimpleDialog::class.java)
            startActivity(intent)
            //val dialog = SimpleDialog()
            //dialog.show(supportFragmentManager, "dialog")
        }

    }


    override fun onStop() {
        super.onStop()
        println("This has been called")
    }
}


