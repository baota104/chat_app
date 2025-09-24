package com.example.chatapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.MyApplication
import com.example.chatapp.SharePrefs
import com.example.chatapp.Utils
import com.example.chatapp.modal.Messages
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
    val messageRepo = MessageRepo()
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
    // send message
    fun Sendmessage(sender:String,receiver:String,friendName:String,friendimage:String)= viewModelScope.launch(Dispatchers.IO) {

            val context = MyApplication.instanse.applicationContext
            val hashMap = hashMapOf<String,Any>(
                "sender" to sender,"receiver" to receiver,
                "message" to message.value!!,
                "time" to Utils.getTime()

            )

        val uniqueId = listOf(sender,receiver).sorted()
        uniqueId.joinToString  (separator = "")
        val namefriendsplit = friendName.split("\\s".toRegex())[0]

        val mysharepref = SharePrefs(context)
        mysharepref.setValue("friendid",receiver)
        mysharepref.setValue("chatroomid",uniqueId.toString())
        mysharepref.setValue("friendname",friendName)
        mysharepref.setValue("friendimage",friendimage)

        firestore.collection("Messages").document(uniqueId.toString()).
        collection("chats").document(Utils.getTime()).set(hashMap).addOnCompleteListener {task ->

               // all work for recent chat list
                val hashMapForRecent = hashMapOf<String,Any>(
                    "friendid" to receiver,
                    "time" to Utils.getTime(),
                    "sender" to Utils.getUiLogedIn(),
                    "message" to message.value!!,
                    "friendsimage" to friendimage,
                    "name" to friendName,
                    "person" to "you"
                )
            firestore.collection("Conversation${Utils.getUiLogedIn()}").document(receiver).set(hashMapForRecent)

            firestore.collection("Conversation${receiver}").document(Utils.getUiLogedIn()).update("message",message.value!!,"time",Utils.getTime(),"person",name.value!!)


            if(task.isSuccessful){
                message.value = ""
            }

        }


    }
    fun getMessages(friendid:String): LiveData<List<Messages>>{
        return messageRepo.getMessages(friendid)
    }
}