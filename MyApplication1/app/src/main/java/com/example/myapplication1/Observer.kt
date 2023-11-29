package com.example.myapplication1

import android.widget.TextView
import androidx.lifecycle.*
import android.util.Log
import android.view.inspector.InspectionCompanion


class Observer(

    private val name: String,
    private val methodList: TextView,
    private val status: TextView

) : LifecycleEventObserver {

    private val repository = Repository


    private fun update(format: String, status: String) {
        // 并把新发生的生命周期事件刷新到全局类Repository的methodList和status中
        repository.methodList.insert(
            0,
            String.format(
                format,
                name
            )
        )
        repository.status[name] = status
    }



    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {

                Lifecycle.Event.ON_CREATE -> update("%s.onCreate()\n", "Created")
                Lifecycle.Event.ON_START -> update("%s.onStart()\n", "Started")
                Lifecycle.Event.ON_RESUME -> update("%s.onResume()\n", "Resumed")
                Lifecycle.Event.ON_PAUSE -> update("%s.onPause()\n", "Paused")
                //Lifecycle.Event.ON_STOP -> null
                Lifecycle.Event.ON_STOP -> update("%s.onStop()\n", "Stopped")
                Lifecycle.Event.ON_STOP -> update("%s.onStop()\n", "Stopped")
                Lifecycle.Event.ON_DESTROY -> update("%s.onDestroy()\n", "Destroyed")
                Lifecycle.Event.ON_ANY -> {}

        }

        // 并把新发生的生命周期事件刷新到全局类Repository的methodList和status中
        methodList.text = repository.methodList
        val statusBuilder = StringBuilder()

        repository.status.forEach { (k, v) ->
            statusBuilder.append(String.format("%s: %s\n", k, v))
        }

        //刷新到 component的TextViewstatus和statusBuilder中
        methodList.text = repository.methodList
        status.text = statusBuilder
    }

}