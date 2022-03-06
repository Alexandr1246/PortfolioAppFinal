package com.example.appdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val signInLauncher =
        registerForActivityResult(      // Создает обьект авторизации екрана
            FirebaseAuthUIActivityResultContract()
        ) { res ->
            this.onSignInResult(res)                                 // запускает екран регистрации
        }

    private lateinit var database: DatabaseReference            // создали обьект для записи в базу данных

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // <== старт приложение указывается с какого активини программе начать рабоату
        Log.d("testLog", "in onCreate")

        Log.d("testLogs", "RegistrationActivity start registration")
        database = Firebase.database.reference                  // Инициализация базы данных

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )            // список модулей которые мы будем использовать для авторизации

        val signInIntent =
            // пишем логику для сбора данных, создаем поля для заполнения при ригистрации
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
        signInLauncher.launch(signInIntent)                     // запускает екран регистрации в auth

    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response =
            result.idpResponse                               // принимает результат с екрана FireBase auth
        if (result.resultCode == RESULT_OK) {                       // если результат регистрации успешный
            Log.d("testLogs", "RegistrationActivity registration success ${response?.email}")
            // Successfully signed in
            val authUser =
                FirebaseAuth.getInstance().currentUser   // тогда создаем пользователя в firebase auth
            authUser?.let {             //если он не 0 (существует) сохраняем его в real time database
                val email = it.email.toString() // извлекаем email нашего пользователя
                val uid = it.uid // извлекаем uid нашего пользователя
                val firebaseUser = User(email, uid) // создаем новый обьект User в database с параметрами email и uid
                Log.d("testLogs", "RegistrationActivity firebaseUser $firebaseUser")
                database.child("users").child(uid)
                    .setValue(firebaseUser)       // сохраняем нашего пользователя в FireBase RealTime

                val intentToAnotherScreen = Intent(
                    this@MainActivity,
                    MoviesActivity::class.java
                ) // <== отправляем пользователя на другой экран
                startActivity(intentToAnotherScreen) // <== отправляем пользователя на другой экран
                //Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show() // <== появляющееся сообщение о нажатии на кнопку
            }

        } else {    // если результат не "ок" должны обработать ошибку
            Log.d("testLogs", "RegistrationActivity registration failure")
            Toast.makeText(this@MainActivity, "Error with registration", Toast.LENGTH_SHORT).show() // <== появляющееся сообщение о нажатии на кнопку
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}