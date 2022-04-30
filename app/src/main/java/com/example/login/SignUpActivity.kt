package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.login.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.signUpButton.setOnClickListener {

            val mEmail = binding.emailEditText.text.toString()
            val mPassword = binding.passwordEditText.text.toString()
            val mRepeatPassword = binding.repeatPasswordEditText.text.toString()

            //val passwordRegex = Pattern.compile("^" +
                 //   "(?=.*[-@#$%^&+=])" +     // Al menos 1 carácter especial
                 //   ".{6,}" +                // Al menos 6 caracteres
                  //  "$")
            val passwordRegex = Pattern.compile(
                "^" +
                        "(?=.*[0-9])" +         //at least 1 digit
                        "(?=.*[a-z])" +        //at least 1 lower case letter
                        "(?=.*[A-Z])" +        //at least 1 upper case letter
                        "(?=.*[@#$%^&+=])" +    //at least 1 special character
                        "(?=\\S+$)" +           //no white spaces
                        ".{4,}" +               //at least 4 characters
                        "$"
            )


            if(mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
                Toast.makeText(baseContext, "Ingrese un email valido.",
                    Toast.LENGTH_SHORT).show()

            } else if (mPassword.isEmpty() || !passwordRegex.matcher(mPassword).matches()){
                Toast.makeText(baseContext, "La contraseña es debil .",
                    Toast.LENGTH_SHORT).show()
            } else if (mPassword !=mRepeatPassword){
                Toast.makeText(baseContext, "Confirmar Contraseña.",
                    Toast.LENGTH_SHORT).show()
            } else{
                createAccount(mEmail, mPassword)
            }

        }

        binding.backImageView.setOnClickListener {
            val intent = Intent(this, SignInActivity:: class.java )
            startActivity(intent)
        }

    }
    private fun  createAccount(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, CheckEmailActivity:: class.java )
                    startActivity(intent)

                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}