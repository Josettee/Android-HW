package com.example.myapplication1
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        val startA: Button = findViewById(R.id.c_start_a)
        val startB: Button = findViewById(R.id.c_start_b)
        val FinishC: Button = findViewById(R.id.finish_c)
        val DialogButton: Button = findViewById(R.id.c_dialog)

        lifecycle.addObserver(
            Observer(
                resources.getString(R.string.C_text),
                findViewById(R.id.c_method_list),
                findViewById(R.id.c_status)
            )
        )

        startA.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        startB.setOnClickListener {
            val intent = Intent(this, ActivityB::class.java)
            startActivity(intent)
        }

        FinishC.setOnClickListener {
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