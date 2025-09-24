@file:Suppress("DEPRECATION")

package com.example.chatapp.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.adapter.OnUserClickListener
import com.example.chatapp.adapter.UserAdapter
import com.example.chatapp.databinding.FragmentHomeBinding
import com.example.chatapp.modal.Users
import com.example.chatapp.mvvm.ChatAppViewModel
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView


@Suppress("DEPRECATION")
class HomeFragment : Fragment(),OnUserClickListener {
    lateinit var rvUsers :RecyclerView
    lateinit var userAdapter: UserAdapter
    lateinit var userviewmodel: ChatAppViewModel
    lateinit var homebinding: FragmentHomeBinding
    lateinit var fbauth: FirebaseAuth;
    lateinit var homepd:ProgressDialog
    lateinit var toolbar: Toolbar
    lateinit var circleImageView: CircleImageView
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
        fbauth = FirebaseAuth.getInstance()
        homepd = ProgressDialog(activity)
        userAdapter =  UserAdapter()

        toolbar = view.findViewById(R.id.toolbarMain)
        circleImageView = view.findViewById(R.id.tlImage)

        rvUsers = view.findViewById(R.id.rvUsers)


        val layoutmanager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        rvUsers.layoutManager = layoutmanager

        userviewmodel.getUsers().observe(viewLifecycleOwner, Observer {
            userAdapter.setUserList(it)
            userAdapter.setOnUserClickListener(this)
            rvUsers.adapter = userAdapter
        })
        homebinding.logOut.setOnClickListener{
            fbauth.signOut()
        }
        userviewmodel.imageUrl.observe(viewLifecycleOwner,Observer{
            Glide.with(requireContext()).load(it).into(circleImageView)
        })
    }

    override fun onUserSelected(position: Int, users: Users) {
        TODO("Not yet implemented")
    }


}