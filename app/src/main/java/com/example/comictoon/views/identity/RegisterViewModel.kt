package com.example.comictoon.views.identity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictoon.repositories.FireBaseRepo
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "RegisterViewModel"
class RegisterViewModel:ViewModel() {

    private val fireStoreRepo=FireBaseRepo.get()
     val registerLiveData=MutableLiveData<String>()
     val registerErrorLiveData=MutableLiveData<String>()



        fun userInfo(userId: String, user: HashMap<String,String>) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = fireStoreRepo.userInfo(userId, user)
                    response.addOnCompleteListener() {
                        if (it.isSuccessful) {
                            registerLiveData.postValue("Success")
                            Log.d(TAG, "Registered successfully: $response")
                        } else {
                            Log.d(TAG, "Failed register")
                            registerErrorLiveData.postValue(response.exception!!.message)
                        }
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "Catch: ${e.message}")
                    registerErrorLiveData.postValue(e.message)
                }
            }
        }




}