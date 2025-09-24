package com.example.chatapp

import android.app.Application

class MyApplication :Application() {
    companion object{
        lateinit var instanse : MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instanse = this
    }
}