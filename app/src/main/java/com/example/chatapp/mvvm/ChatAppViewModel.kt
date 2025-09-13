package com.example.chatapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.modal.Users
import com.google.firebase.firestore.FirebaseFirestore

class ChatAppViewModel :ViewModel() {
    val message = MutableLiveData<String>()
    val firestore = FirebaseFirestore.getInstance()
    val name = MutableLiveData<String>()

    val userRepo = UserRepo()

    fun getUsers() : LiveData<List<Users>> {
        return userRepo.getUsers()
    }
}