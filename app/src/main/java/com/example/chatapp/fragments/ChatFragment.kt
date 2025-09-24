package com.example.chatapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.Utils
import com.example.chatapp.adapter.MessageAdapter
import com.example.chatapp.databinding.FragmentChatBinding
import com.example.chatapp.modal.Messages
import com.example.chatapp.mvvm.ChatAppViewModel
import de.hdodenhof.circleimageview.CircleImageView

class ChatFragment : Fragment() {

    private lateinit var args: ChatFragmentArgs
    private lateinit var chatBinding: FragmentChatBinding
    private lateinit var chatAppViewModel: ChatAppViewModel
    private lateinit var chattoolbar :Toolbar
    private lateinit var toolbarimageView: CircleImageView
    private lateinit var tvUsername:TextView
    private lateinit var tvStatus:TextView
    private lateinit var backbtn: ImageView
    private lateinit var messageAdapter: MessageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        chatBinding =DataBindingUtil.inflate( inflater,R.layout.fragment_chat, container, false)
        return chatBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         args = ChatFragmentArgs.fromBundle(requireArguments())
        chatAppViewModel = ViewModelProvider(this).get(ChatAppViewModel ::class.java)

        chattoolbar = view.findViewById(R.id.toolBarChat)
        toolbarimageView = chattoolbar.findViewById(R.id.chatImageViewUser)

        tvStatus = view.findViewById(R.id.chatUserStatus)
        tvUsername = view.findViewById(R.id.chatUserName)

        backbtn = chattoolbar.findViewById(R.id.chatBackBtn)

        chatBinding.viewModel= chatAppViewModel
        chatBinding.lifecycleOwner = viewLifecycleOwner

       backbtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_chatFragment_to_homeFragment)
        }
        Glide.with(requireContext()).load(args.users.imageUrl).into(toolbarimageView)
        tvStatus.setText(args.users.status)
        tvUsername.setText(args.users.name)


        chatBinding.sendBtn.setOnClickListener {
            chatAppViewModel.Sendmessage(Utils.getUiLogedIn(),args.users.userid!!,args.users.name!!,args.users.imageUrl!!)
        }
        chatAppViewModel.getMessages(args.users.userid!!).observe(viewLifecycleOwner, Observer {

            initRecycleView(it)
        })
    }

    private fun initRecycleView(it: List<Messages>) {
        messageAdapter = MessageAdapter()
        val layoutManager = LinearLayoutManager(context)
        chatBinding.messagesRecyclerView.layoutManager = layoutManager
        layoutManager.stackFromEnd = true
        messageAdapter.setMessageList(it)
        messageAdapter.notifyDataSetChanged()
        chatBinding.messagesRecyclerView.adapter = messageAdapter
    }


}