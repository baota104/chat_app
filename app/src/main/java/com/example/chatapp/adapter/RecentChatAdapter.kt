package com.example.chatapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.modal.RecentChat
import de.hdodenhof.circleimageview.CircleImageView

class RecentChatAdapter :RecyclerView.Adapter<RecentChatHolder>() {
    private var lisofchat = listOf<RecentChat>()
    private var listener: OnRecentChatClicked? = null
    private var recenchat = RecentChat()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recentchatlist,parent,false)
        return RecentChatHolder(view)
    }

    override fun getItemCount(): Int {
        return lisofchat.size
    }

    override fun onBindViewHolder(holder: RecentChatHolder, position: Int) {
        val recentchatlist = lisofchat[position]
        recenchat = recentchatlist

        holder.userName.setText(recentchatlist.name)

        val thememessage = recentchatlist.message!!.split("").take(4).joinToString("")
        val makelastmessage = "${recentchatlist.person}: ${thememessage}"
        holder.lastMessage.setText(makelastmessage)
        Glide.with(holder.itemView.context).load(recentchatlist.friendsimage).into(holder.imageView)
        holder.timeView.setText(recentchatlist.time!!.substring(0,5))

        holder.itemView.setOnClickListener {
            listener!!.getOnRecentChatClicked(position,recentchatlist)
        }
    }
    fun setOnlistener(listener: OnRecentChatClicked){
        this.listener = listener
    }
    fun setOnlist(list:List<RecentChat>){
        this.lisofchat = list
//        notifyDataSetChanged()
    }
}
class RecentChatHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
    val imageView : CircleImageView = itemview.findViewById(R.id.recentChatImageView)
    val userName :TextView = itemview.findViewById(R.id.recentChatTextName)
    val lastMessage :TextView= itemview.findViewById(R.id.recentChatTextLastMessage)
    val timeView :TextView= itemview.findViewById(R.id.recentChatTextTime)
}
interface OnRecentChatClicked{
    fun getOnRecentChatClicked(position: Int,recentChatlist: RecentChat)
}