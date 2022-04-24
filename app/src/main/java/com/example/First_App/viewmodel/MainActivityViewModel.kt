package com.example.First_App.viewmodel

import com.example.First_App.data.User
import com.example.First_App.model.repository.FirebaseRepository
import com.example.First_App.model.repository.FirebaseRepositoryImpl

class MainActivityViewModel {

    private val mFirebaseRepository: FirebaseRepository = FirebaseRepositoryImpl()

    fun updateUserData(firebaseUser: User, uid: String){

        mFirebaseRepository.updateUserData(firebaseUser, uid)
    }

}