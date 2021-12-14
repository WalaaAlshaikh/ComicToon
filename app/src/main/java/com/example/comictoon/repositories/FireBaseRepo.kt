package com.example.comictoon.repositories

import android.content.Context
import com.example.comictoon.model.comic.Result
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception
private const val USERS="users"
private const val FAVOURITE="favourite"
private const val COMIC_ID="comicId"
class FireBaseRepo {

    private val fireBaseDataBase=Firebase.firestore

    private val users=fireBaseDataBase.collection(USERS)




    fun userInfo(userId:String, user:HashMap<String,String>)=users.document(userId).set(user)

    fun markedComic(userId: String, item:HashMap<String,Any>)= users.document(userId).collection(
        FAVOURITE).document(
        item.getValue(COMIC_ID).toString()
    ).set(item)

    fun markClick(userId: String , id:Int)=users.document(userId).collection(FAVOURITE).whereEqualTo(
        COMIC_ID,id).get()



    companion object{
        private var instance:FireBaseRepo?=null
        fun init(context: Context){
            if(instance==null)
                instance= FireBaseRepo()
        }

        fun get():FireBaseRepo{
            return instance?: throw Exception("FireBase Repository must be initialized")
        }
    }
}