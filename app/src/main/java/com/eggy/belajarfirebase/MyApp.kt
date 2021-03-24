package com.eggy.belajarfirebase

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        getDatabase()

    }

    fun getDatabase(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

    }

}