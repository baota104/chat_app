@file:Suppress("DEPRECATION")

package com.example.chatapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.chatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {
    private lateinit var signUpBinding: ActivitySignUpBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var signUpauth :FirebaseAuth
    private lateinit var name :String
    private lateinit var email :String
    private lateinit var password :String
    private lateinit var SignUpPD : ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpBinding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        firestore = FirebaseFirestore.getInstance()
        signUpauth = FirebaseAuth.getInstance()
        SignUpPD = ProgressDialog(this)

        signUpBinding.signUpTextToSignIn.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }

        signUpBinding.signUpBtn.setOnClickListener {
            name = signUpBinding.signUpEtName.text.toString()
            email = signUpBinding.signUpEmail.text.toString()
            password = signUpBinding.signUpPassword.text.toString()

            if(name.isEmpty()){
                Toast.makeText(this,"name must be filled",Toast.LENGTH_SHORT).show()
            }
            if(email.isEmpty()){
                Toast.makeText(this,"email must be filled",Toast.LENGTH_SHORT).show()
            }
            if(password.isEmpty()){
                Toast.makeText(this,"password must be filled",Toast.LENGTH_SHORT).show()
            }

            if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                SignUpUser(name,email,password)
            }
        }
    }

    private fun SignUpUser(name: String, email: String, password: String) {
        SignUpPD.show()
        SignUpPD.setMessage("Signing up...")

        signUpauth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = signUpauth.currentUser
                    if (user != null) {
                        val hashmap = hashMapOf(
                            "userid" to user.uid,
                            "name" to name,
                            "email" to email,
                            "status" to "default",
                            "imageUrl" to "https://tse4.mm.bing.net/th/id/OIP.7WA6mH7oqsGTo_duc43GqwHaHa?pid=Api&P=0&h=220"
                        )

                        firestore.collection("Users")
                            .document(user.uid)
                            .set(hashmap)
                            .addOnSuccessListener {
                                SignUpPD.dismiss()
                                Toast.makeText(this, "User saved!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, SignInActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener { e ->
                                SignUpPD.dismiss()
                                Toast.makeText(this, "Firestore Error: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                    }
                } else {
                    SignUpPD.dismiss()
                    Toast.makeText(this, "Auth Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

}