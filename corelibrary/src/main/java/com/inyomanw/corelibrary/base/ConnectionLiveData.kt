package com.inyomanw.corelibrary.base

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo

@Suppress("DEPRECATION")
class ConnectionLiveData(private val context: Context) : LiveData<Boolean>(){

    private val networkReceiver: BroadcastReceiver by lazy {
        object : BroadcastReceiver(){
            @SuppressLint("MissingPermission")
            override fun onReceive(context: Context?, intent: Intent?) {
                val info = intent?.extras?.getParcelable<NetworkInfo>("networkInfo")
                val status = info?.state == NetworkInfo.State.CONNECTED
                value = status
            }
        }
    }

    override fun onActive() {
        super.onActive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkReceiver,filter)
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(networkReceiver)
    }
}