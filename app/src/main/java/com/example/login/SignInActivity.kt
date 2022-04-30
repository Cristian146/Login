package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.login.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.signInAppCompatButton.setOnClickListener {
            val mEmail= binding.emailEditText.text.toString()
            val mPassword=binding.passwordEditText.text.toString()
            when {
                mPassword.isEmpty() || mEmail.isEmpty() -> {
                    Toast.makeText(this, "Email o contraseña o incorrectos.",
                        Toast.LENGTH_SHORT).show()
                }
                else -> {
                    signIn(mEmail, mPassword)
                }
            }

        }
        binding.signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d( "TAG", "signInWithEmail:success")
                    reload()
                } else {
                    Log.w(  "TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Correo o contraseña incorrectos.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }
    private fun reload(){
        val intent  = Intent ( this, MainActivity::class.java)
        this.startActivity(intent)
    }
}