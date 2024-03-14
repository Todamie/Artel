package com.example.artel.EtoBaza

import android.app.Application
import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    val requestQueue: RequestQueue? = null
        get(){
            if (field == null){
                Log.d("VolleySingleton", "field is not null")
                return Volley.newRequestQueue(applicationContext)
            }
            Log.d("VolleySingleton", "field is null")
            return field
        }

    fun <T> addToRequestQueue(request: Request<T>){
        request.tag = TAG
        Log.d("VolleySingleton", "addrequest")
        requestQueue?.add(request)

    }

    companion object{
        private val TAG = VolleySingleton::class.java.simpleName
        @get:Synchronized var instance: VolleySingleton? = null
        private set
    }

}