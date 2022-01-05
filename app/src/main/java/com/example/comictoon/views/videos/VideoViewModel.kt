package com.example.comictoon.views.videos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictoon.model.comic.ComicModel
import com.example.comictoon.model.videos.Result
import com.example.comictoon.model.videos.VideosModel
import com.example.comictoon.repositories.ApiReop

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

private const val TAG = "VideoViewModel"
class VideoViewModel:ViewModel() {

    val apiRepo = ApiReop.get()
    val videoLiveData = MutableLiveData<VideosModel>()
    val videoErrorLiveData = MutableLiveData<String>()
    val selectedLiveData=MutableLiveData<Result>()

    fun callvideos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.getVideos()



                    if (response.isSuccessful) {
                        response.body()?.run {

                            Log.d(TAG, response.body().toString())
                            videoLiveData.postValue(this)

                        }
                    } else {
                        Log.d(TAG, response.message())
                        videoErrorLiveData.postValue(response.message())

                    }

            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                videoErrorLiveData.postValue(e.message.toString())

            }
        }
    }
}