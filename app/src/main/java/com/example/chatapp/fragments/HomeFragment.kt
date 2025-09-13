package com.example.chatapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.adapter.UserAdapter
import com.example.chatapp.databinding.FragmentHomeBinding
import com.example.chatapp.mvvm.ChatAppViewModel


class HomeFragment : Fragment() {
    lateinit var rvUsers :RecyclerView
    lateinit var userAdapter: UserAdapter
    lateinit var userviewmodel: ChatAppViewModel
    lateinit var homebinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homebinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        return homebinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userviewmodel = ViewModelProvider(this).get(ChatAppViewModel ::class.java)
        userAdapter =  UserAdapter()
        rvUsers = view.findViewById(R.id.rvUsers)
        val layoutmanager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        rvUsers.layoutManager = layoutmanager
        userviewmodel.getUsers().observe(viewLifecycleOwner, Observer {
            userAdapter.setUserList(it)
            rvUsers.adapter = userAdapter
        })
    }


}