package com.example.First_App.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.example.First_App.R
import com.example.First_App.data.User
import com.example.First_App.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private val mMainActivityViewModel: MainActivityViewModel = MainActivityViewModel()

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { resultCallback ->
         this.onSignInResult(resultCallback)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openRegistrationScreen()
    }

    private fun openRegistrationScreen() {
        val intentToAnotherScreen = Intent(this, MoviesActivity::class.java)
        startActivity(intentToAnotherScreen)

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        val signInIntent =

            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
        signInLauncher.launch(signInIntent)

    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        when (result.resultCode) {
            RESULT_OK -> {
                val authUser = FirebaseAuth.getInstance().currentUser
                authUser?.let {
                    val email = it.email.toString()
                    val uid = it.uid
                    val firebaseUser = User(email, uid)

                    mMainActivityViewModel.updateUserData(firebaseUser, uid)

                    val intentToAnotherScreen = Intent(this, MoviesActivity::class.java)
                    startActivity(intentToAnotherScreen)
                }
            }
            RESULT_CANCELED -> {
                Toast.makeText(this@MainActivity, "Something wrong with registration", Toast.LENGTH_SHORT).show()
            }

            else -> {
                // to do anything

            }
        }
    }
}
