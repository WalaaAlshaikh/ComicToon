package com.example.comictoon.views.identity

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictoon.repositories.FireBaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "UpdateProfileImageViewM"

class UpdateProfileImageViewModel:ViewModel() {
    private val firebaseRepo = FireBaseRepo.get()

    val updateImageLiveData = MutableLiveData<String>()
    val updateImageErrorLiveData = MutableLiveData<String>()



    // to upload Item Image to fireStorage
    fun updateItemImage(imageUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val response = firebaseRepo.uploadItemImage(imageUri)
                response.addOnCompleteListener {

                    if (it.isSuccessful) {
                        // post filename to use it in upload item info
                        updateImageLiveData.postValue(imageUri.toString())
                        Log.d(TAG, "Image upload success: $response")

                    } else {
                        Log.d(TAG, it.exception!!.message.toString())
                        updateImageErrorLiveData.postValue(it.exception!!.message)
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "Catch: ${e.message}")
                updateImageErrorLiveData.postValue(e.message)
            }
        }
    }
}