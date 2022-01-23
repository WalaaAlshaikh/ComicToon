package com.example.comictoon.repositories

import android.content.Context
import android.net.Uri
import com.example.comictoon.model.comic.Result
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.lang.Exception
private const val USERS="users"
private const val FAVOURITE="favourite"
private const val COMIC_ID="comicId"
class FireBaseRepo {

    private val fireBaseDataBase=Firebase.firestore
    private val users=fireBaseDataBase.collection(USERS)

    private val imageRef = Firebase.storage.reference





    // for creating a collection (user) with userId in firebase
    fun userInfo(userId:String, user:HashMap<String,String>)=users.document(userId).set(user)

    // for creating a collection (favourite) inside (user) collection and setting the id of document as the comic id which was taken from ComicVine api
    fun markedComic(userId: String, item:HashMap<String,Any>)= users.document(userId).collection(
        FAVOURITE).document(
        item.getValue(COMIC_ID).toString()
    ).set(item)

    // for retrieving the data from fire store that has the same id as comicId (from the field)
    fun markClick(userId: String , id:Int)=users.document(userId).collection(FAVOURITE).whereEqualTo(
        COMIC_ID,id).get()

    // for retrieving data and arranging the document fields of (favourite) collection by the title of the comic
    fun receiveItemsFromFireBase(userId:String)=users.document(userId).collection(FAVOURITE).orderBy("title",
        Query.Direction.ASCENDING)

    // for add/ update the data inside (favourite) collection -specifically the personal note field-
    fun updateItem(userId: String,data:String, docId:String)=users.document(userId).collection(FAVOURITE).document(docId
       ).update("personalNote",data)

    // for removing the items in (favourite) collection
    fun deleteItem(userId: String,docId:String)=users.document(userId).collection(FAVOURITE).document(docId).delete()

    // upload Item Image to fireStorage
    fun uploadItemImage(imageUri: Uri)= imageRef.putFile(imageUri)





    // create a singleton object
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