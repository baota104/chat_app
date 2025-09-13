package com.example.chatapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.Utils
import com.example.chatapp.modal.Users
import com.google.firebase.firestore.FirebaseFirestore

class UserRepo {

    private var firestore = FirebaseFirestore.getInstance()

    fun getUsers() : LiveData<List<Users>>{
        val users = MutableLiveData<List<Users>>()
        
        firestore.collection("Users").addSnapshotListener { snapshot, exception ->

            if(exception != null){
                return@addSnapshotListener
            }
            val userList = mutableListOf<Users>()
            snapshot?.documents?.forEach{
                document ->
                val user = document.toObject(Users :: class.java)

                if(user!!.userid != Utils.getUiLogedIn()){
                    user.let {
                        userList.add(it)
                    }
                }
                users.value = userList
            }

        }
        return users
    }
}