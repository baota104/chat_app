package com.example.chatapp

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date

class Utils {
    companion object{

        private val auth = FirebaseAuth.getInstance()
        private var userid : String = ""

        fun getUiLogedIn(): String{
            if(auth.currentUser != null){
                userid = auth.currentUser!!.uid
            }
            return userid
        }
        fun getTime() : String{
            val formmater = SimpleDateFormat("HH:mm:ss")
            val date :Date = Date(System.currentTimeMillis())
            val stringdate = formmater.format(date)
            return stringdate
        }
    }
}