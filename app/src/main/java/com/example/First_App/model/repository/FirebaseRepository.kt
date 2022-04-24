package com.example.First_App.model.repository

import com.example.First_App.data.User

interface FirebaseRepository {

    fun updateUserData(firebaseUser: User, uid: String)
}