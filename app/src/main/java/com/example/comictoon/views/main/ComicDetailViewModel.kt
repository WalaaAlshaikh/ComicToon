package com.example.comictoon.views.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictoon.model.comic.MarkedModel
import com.example.comictoon.model.comic.Result
import com.example.comictoon.repositories.FireBaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "ComicDetailViewModel"
class ComicDetailViewModel:ViewModel() {
    val fireBaseRepo = FireBaseRepo.get()
    var listOfResult: Result? = null

    val markedLiveData = MutableLiveData<String>()

    val comicDetailErrorLiveData=MutableLiveData<String>()
    var detailComicLiveData = MutableLiveData<Result>()


    fun sendComictoFireBase(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                if (listOfResult != null) {
                    val comicFav = hashMapOf<String, Any>(
                        "image" to listOfResult!!.image.originalUrl,
                        "title" to listOfResult!!.name,
                        "comicId" to listOfResult!!.id,
                        "personalNote" to ""

                    )
                    val response = fireBaseRepo.markedComic(userId, comicFav)
                    response.addOnSuccessListener() { documentReference ->
                        Log.d("repeat", "repeat")
                        markedLiveData.postValue("successfully added")

                    }.addOnFailureListener { e ->
                        Log.w("document adding", "Error adding document", e)
                    }
                } else {
                    comicDetailErrorLiveData.postValue("error")
                }


            } catch (e: Exception) {

                Log.d(TAG, e.message.toString())
                comicDetailErrorLiveData.postValue(e.message.toString())
            }
        }
    }

    //////////////////////////////////////////////////////////////////////


    fun getComicfromClickComic(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d(TAG, listOfResult!!.id.toString())

                val response = fireBaseRepo.markClick(userId, listOfResult!!.id)
                response.addOnSuccessListener {
                    if (it.count() > 0) {
                        comicDetailErrorLiveData.postValue("This comic has already been added")

                    } else {
                        sendComictoFireBase(userId)
                    }

                }.addOnFailureListener {
                    comicDetailErrorLiveData.postValue(it.message.toString())
                }


            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                comicDetailErrorLiveData.postValue(e.message)
            }
        }


    }


}