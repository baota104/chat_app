package com.example.chatapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.Utils
import com.example.chatapp.modal.Messages
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MessageRepo {
    private val firestore = FirebaseFirestore.getInstance()

    fun getMessages (friendid:String): LiveData<List<Messages>>{

        val messages = MutableLiveData<List<Messages>>()
        val uniqueid = listOf(Utils.getUiLogedIn(),friendid).sorted()
        uniqueid.joinToString(separator = "")
        firestore.collection("Messages").document(uniqueid.toString()).collection("chats").orderBy("time",Query.Direction.ASCENDING).addSnapshotListener { value, error ->

        if(error != null){
            return@addSnapshotListener
        }
            val messageList = mutableListOf<Messages>()
            if(!value!!.isEmpty){
                value.documents.forEach() { document->

                    val messagemodel = document.toObject(Messages::class.java)
                    if(messagemodel!!.sender.equals(Utils.getUiLogedIn()) && messagemodel!!.receiver.equals(friendid)
                        || messagemodel.sender.equals(friendid) && messagemodel!!.receiver.equals(Utils.getUiLogedIn())
                        ){

                        messagemodel.let {
                            messageList.add(it!!)
                        }
                    }
                }
                messages.value = messageList
            }

        }
        return messages
    }
}