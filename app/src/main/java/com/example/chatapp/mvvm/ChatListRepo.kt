package com.example.chatapp.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.Utils
import com.example.chatapp.modal.RecentChat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ChatListRepo {
    private val firestore = FirebaseFirestore.getInstance()

    fun getAllchatlist() : LiveData<List<RecentChat>>{
        val mainchatlist = MutableLiveData<List<RecentChat>>()

        firestore.collection("Conversation${Utils.getUiLogedIn()}").orderBy("time",Query.Direction.DESCENDING).addSnapshotListener { value, error ->

            if(error != null) return@addSnapshotListener
            val chatlist = mutableListOf<RecentChat>()
            value?.forEach{documetn ->
                val recetnchatmodel = documetn.toObject(RecentChat::class.java)
                if(recetnchatmodel.sender.equals(Utils.getUiLogedIn())){
                    recetnchatmodel.let {
                        chatlist.add(it)
                    }
                }
            }
            mainchatlist.value = chatlist

        }
        Log.e("chatlistrepo",mainchatlist.toString())
        return mainchatlist
    }
}