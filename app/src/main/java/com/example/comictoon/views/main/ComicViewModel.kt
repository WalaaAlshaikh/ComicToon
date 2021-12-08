package com.example.comictoon.views.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictoon.model.comic.ComicModel
import com.example.comictoon.repositories.ApiReop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

private const val TAG = "ComicViewModel"

class ComicViewModel:ViewModel() {

    val apiRepo=ApiReop.get()

    val comicLiveData= MutableLiveData<ComicModel>()

    fun callomics(){
        // Log.d(TAG,"Access to viewModel ")
        viewModelScope.launch (Dispatchers.IO){
            try {
                val response=apiRepo.getComics()

                withContext(Dispatchers.Main) {

                    if (response.isSuccessful){
                        response.body()?.run {

                            Log.d(TAG,response.body().toString())
                            comicLiveData.postValue(this)


                        }
                    }else{
                        Log.d(TAG,response.message())

                    }
                }



            }catch (e: Exception){
                Log.d(TAG,e.message.toString())

            }
        }
    }
}