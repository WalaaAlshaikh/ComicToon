package com.example.comictoon.views.main

import android.app.DownloadManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictoon.model.comic.ComicModel
import com.example.comictoon.model.comic.MarkedModel
import com.example.comictoon.model.comic.Result
import com.example.comictoon.repositories.ApiReop
import com.example.comictoon.repositories.ApiReop.Companion.get
import com.example.comictoon.repositories.FireBaseRepo
import com.google.firebase.firestore.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

private const val TAG = "ComicViewModel"

class ComicViewModel : ViewModel() {

    val apiRepo = ApiReop.get()



    val comicLiveData = MutableLiveData<ComicModel>()
    val comicErrorLiveData = MutableLiveData<String>()

    fun callomics() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.getComics()

                withContext(Dispatchers.Main) {

                    if (response.isSuccessful) {
                        response.body()?.run {

                            Log.d(TAG, response.body().toString())
                            comicLiveData.postValue(this)


                        }
                    } else {
                        Log.d(TAG, response.message())
                        comicErrorLiveData.postValue(response.message())

                    }
                }


            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                comicErrorLiveData.postValue(e.message.toString())

            }
        }
    }

}

