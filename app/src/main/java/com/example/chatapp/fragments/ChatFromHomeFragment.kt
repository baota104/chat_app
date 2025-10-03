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
import com.example.chatapp.databinding.FragmentChatfromHomeBinding
import com.example.chatapp.modal.Messages
import com.example.chatapp.mvvm.ChatAppViewModel
import de.hdodenhof.circleimageview.CircleImageView

class ChatFromHomeFragment : Fragment() {

    private lateinit var args: ChatFromHomeFragmentArgs
    private lateinit var chatfromhomeBidingn: FragmentChatfromHomeBinding
    private lateinit var chatAppViewModel: ChatAppViewModel
    private lateinit var chattoolbar : Toolbar
    private lateinit var toolbarimageView: CircleImageView
    private lateinit var tvUsername: TextView
    private lateinit var tvStatus: TextView
    private lateinit var backbtn: ImageView
    private lateinit var messageAdapter: MessageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        chatfromhomeBidingn = DataBindingUtil.inflate(inflater,R.layout.fragment_chatfrom_home, container, false)
        return chatfromhomeBidingn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = ChatFromHomeFragmentArgs.fromBundle(requireArguments())
        chatAppViewModel = ViewModelProvider(this).get(ChatAppViewModel ::class.java)

        chattoolbar = view.findViewById(R.id.toolBarChat)
        toolbarimageView = chattoolbar.findViewById(R.id.chatImageViewUser)

        tvStatus = view.findViewById(R.id.chatUserStatus)
        tvUsername = view.findViewById(R.id.chatUserName)

        backbtn = chattoolbar.findViewById(R.id.chatBackBtn)

        chatfromhomeBidingn.viewModel= chatAppViewModel
        chatfromhomeBidingn.lifecycleOwner = viewLifecycleOwner

        backbtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_chatFromHomeFragment_to_homeFragment)
        }
        Glide.with(requireContext()).load(args.recentchats.friendsimage).into(toolbarimageView)
        tvStatus.setText(args.recentchats.status)
        tvUsername.setText(args.recentchats.name)


        chatfromhomeBidingn.sendBtn.setOnClickListener {
            chatAppViewModel.Sendmessage(Utils.getUiLogedIn(),args.recentchats.friendid!!,args.recentchats.name!!,args.recentchats.friendsimage!!)
        }
        chatAppViewModel.getMessages(args.recentchats.friendid!!).observe(viewLifecycleOwner, Observer {

            initRecycleView(it)
        })
    }

    private fun initRecycleView(it: List<Messages>) {
        messageAdapter = MessageAdapter()
        val layoutManager = LinearLayoutManager(context)
        chatfromhomeBidingn.messagesRecyclerView.layoutManager = layoutManager
        layoutManager.stackFromEnd = true
        chatfromhomeBidingn.messagesRecyclerView.adapter = messageAdapter
        messageAdapter.setMessageList(it)
        messageAdapter.notifyDataSetChanged()

    }

}