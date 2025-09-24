package com.example.chatapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.MyApplication
import com.example.chatapp.SharePrefs
import com.example.chatapp.Utils
import com.example.chatapp.modal.Users
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatAppViewModel :ViewModel() {
    val message = MutableLiveData<String>()
    val firestore = FirebaseFirestore.getInstance()
    val name = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()


    val userRepo = UserRepo()
    init {
        getCurrentUser()
    }

    fun getUsers() : LiveData<List<Users>> {
        return userRepo.getUsers()
    }
    fun getCurrentUser() = viewModelScope.launch (Dispatchers.IO){
        val context = MyApplication.instanse.applicationContext

        firestore.collection("Users").document(Utils.getUiLogedIn()).addSnapshotListener { value, error ->
            if(value!!.exists() && value != null){
                    val user = value.toObject(Users::class.java)
                    name.value = user?.name!!
                    imageUrl.value = user?.imageUrl!!

                    val mySharePrefs = SharePrefs(context)
                    mySharePrefs.setValue("username",user.name!!)
            }


        }
    }
}