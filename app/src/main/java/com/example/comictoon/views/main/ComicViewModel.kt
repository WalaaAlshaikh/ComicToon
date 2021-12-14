package com.example.comictoon.views.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictoon.model.comic.ComicModel
import com.example.comictoon.model.comic.Result
import com.example.comictoon.repositories.ApiReop
import com.example.comictoon.repositories.ApiReop.Companion.get
import com.example.comictoon.repositories.FireBaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

private const val TAG = "ComicViewModel"

class ComicViewModel:ViewModel() {

    val apiRepo=ApiReop.get()

    val fireBasePero=FireBaseRepo.get()

    val comicLiveData= MutableLiveData<ComicModel>()

    val detailComicLiveData=MutableLiveData<Result>()
    val comicMarkedErrorLiveData=MutableLiveData<String>()
    var lisrOfResult:Result?= null

        ////////////////////////////////////////////////////////

    val markedLiveData=MutableLiveData<String>()


    fun sendComictoFireBase(userId:String){
        viewModelScope.launch(Dispatchers.IO) {

            try {
                if(lisrOfResult!=null){
                    val comicFav = hashMapOf<String, Any>(
                        "image" to lisrOfResult!!.image.originalUrl,
                        "title" to lisrOfResult!!.name,
                        "comicId" to lisrOfResult!!.id

                    )
                    val response=fireBasePero.markedComic(userId,comicFav)
                    response.addOnSuccessListener(){
                            documentReference ->
                        Log.d("repeat","repeat")
                        markedLiveData.postValue("successful")

                    }.addOnFailureListener {
                            e ->
                        Log.w("document adding", "Error adding document", e)
                    }
                }



            }catch (e: Exception){

                Log.d(TAG,e.message.toString())
            }
        }
    }

    //////////////////////////////////////////////////////////////////////

    val clickMarkLifeData=MutableLiveData<String>()

    fun getComicfromClickComic(userId:String){

        try {
            Log.d(TAG,lisrOfResult!!.id.toString())

            val response=fireBasePero.markClick(userId, lisrOfResult!!.id)
            response.addOnSuccessListener {
                if(it.count()>0){
                    comicMarkedErrorLiveData.postValue("This comic has already been added")
                }else{
                    sendComictoFireBase(userId)
                }

            }.addOnFailureListener{
                comicMarkedErrorLiveData.postValue(it.message.toString())
            }


        }catch (e:Exception){
            Log.d(TAG,e.message.toString())
            comicMarkedErrorLiveData.postValue(e.message)
        }
    }


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