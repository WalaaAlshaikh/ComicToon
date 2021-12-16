package com.example.comictoon.views.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictoon.repositories.FireBaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "UpdateViewModel"
class UpdateViewModel: ViewModel() {

    val fireBaseRepo = FireBaseRepo.get()

    val updateErrorLiveData=MutableLiveData<String>()
    val updateStringLiveData=MutableLiveData<String>()

    fun updateNote(userId: String, data: String, docId: String) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                 val response = fireBaseRepo.updateItem(userId, data, docId)
                response.addOnCompleteListener {
                    if (it.isSuccessful) {
                        updateStringLiveData.postValue("updated Successful")
                    } else {
                        updateErrorLiveData.postValue(response.exception!!.message)
                    }
                }

            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                updateErrorLiveData.postValue(e.message.toString())
            }
        }
    }

}