@file:Suppress("DEPRECATION")

package com.example.chatapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chatapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class SignInActivity : AppCompatActivity() {


    private lateinit var email :String
    private lateinit var password :String
    private lateinit var auth :FirebaseAuth
    private lateinit var progressDialogSignIn :ProgressDialog
    private lateinit var signInBinding :ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        signInBinding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()

        // if user has already log in
        // just has to log in once

        if(auth.currentUser != null){
            startActivity(Intent(this,MainActivity ::class.java))
        }
        progressDialogSignIn = ProgressDialog(this)

        signInBinding.signInTextToSignUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity :: class.java))

        }

        signInBinding.loginButton.setOnClickListener {
            email = signInBinding.loginetemail.text.toString()
            password = signInBinding.loginetpassword.text.toString()

            if(email.isEmpty()){
                Toast.makeText(this,"Email cant be empty",Toast.LENGTH_SHORT).show()
            }
            if(password.isEmpty()){
                Toast.makeText(this,"Password cant be empty",Toast.LENGTH_SHORT).show()
            }

            if(email.isNotEmpty() && password.isNotEmpty()){
                Signinn(email,password)
            }

        }
    }
    private fun Signinn(email :String,password:String){
        progressDialogSignIn.show()
        progressDialogSignIn.setMessage("Signing in")

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
                progressDialogSignIn.dismiss()
                startActivity(Intent(this,MainActivity ::class.java))
            }
            else {
                progressDialogSignIn.dismiss()
                Toast.makeText(this,"Invalid User",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            exception ->
            when(exception){
                is FirebaseAuthInvalidCredentialsException->{
                    Toast.makeText(this,"Invalid Credential",Toast.LENGTH_SHORT).show()
                }
                else ->{
                    Toast.makeText(this,"Fireauth error",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        progressDialogSignIn.dismiss()
        finishAffinity()
    }

    override fun onDestroy() {
        super.onDestroy()
        progressDialogSignIn.dismiss()
    }
}