package com.example.chatapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.Utils
import com.example.chatapp.modal.Messages

class MessageAdapter : RecyclerView.Adapter<MessageHolder>() {

    private var listofmessages = listOf<Messages>()
    private var LEFT = 0
    private var RIGHT = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if(viewType == RIGHT){
            val view = inflater.inflate(R.layout.chatitemright,parent,false)
            MessageHolder(view)
        }
        else{
            val view = inflater.inflate(R.layout.chatitemleft,parent,false)
            MessageHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return listofmessages.size
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val message = listofmessages[position]

        holder.messagetext.visibility = View.VISIBLE
        holder.timeOfsent.visibility = View.VISIBLE

        holder.messagetext.setText(message.message)
        holder.timeOfsent.text = message.time?.substring(0,5) ?: ""
    }

    override fun getItemViewType(position: Int) =
        if(listofmessages[position].sender.equals(Utils.getUiLogedIn())) RIGHT else LEFT

    fun setMessageList(newlist: List<Messages>){
        this.listofmessages =newlist
    }


}
class MessageHolder(itemView:View): RecyclerView.ViewHolder(itemView){

    val messagetext : TextView = itemView.findViewById(R.id.show_message)
    val timeOfsent :TextView = itemView.findViewById(R.id.timeView)
}