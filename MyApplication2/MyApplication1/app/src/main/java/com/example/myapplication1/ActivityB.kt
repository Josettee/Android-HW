package com.example.myapplication1
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Lifecycle

class ActivityB : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        val startA: Button = findViewById(R.id.b_start_a)
        val startC: Button = findViewById(R.id.b_start_c)
        val FinishB: Button = findViewById(R.id.finish_b)
        val DialogButton: Button = findViewById(R.id.b_dialog)

        lifecycle.addObserver(
            Observer(
                resources.getString(R.string.B_text),
                findViewById(R.id.b_method_list),
                findViewById(R.id.b_status)
            )
        )

        startA.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            }



        startC.setOnClickListener {
            val intent = Intent(this, ActivityC::class.java)
            startActivity(intent)

        }

        FinishB.setOnClickListener {
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