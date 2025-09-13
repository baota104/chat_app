package com.example.chatapp

import com.google.firebase.auth.FirebaseAuth

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
    }
}