import android.util.Log
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comictoon.model.comic.MarkedModel

import com.example.comictoon.repositories.FireBaseRepo
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot



private const val TAG = "MarkedComicViewModel"
class MarkedComicViewModel:ViewModel() {



    val fireBasePero = FireBaseRepo.get()

    val markedComicErrorLiveData=MutableLiveData<String>()
    val markedStringComicLiveData=MutableLiveData<String>()
    var markLiveData = MutableLiveData<List<MarkedModel>>()





//////////////////////////////////////////////////////



//////////////////////////////////////////////////////////

fun deleteItem(userId: String, docId: String) {
    viewModelScope.launch(Dispatchers.IO) {

        try {
            val response = fireBasePero.deleteItem(userId, docId)
            response.addOnCompleteListener {
                if (it.isSuccessful) {
                    markedStringComicLiveData.postValue("deleted Successfully")
                } else {
                    markedComicErrorLiveData.postValue(response.exception!!.message)
                }
            }

        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            markedComicErrorLiveData.postValue(e.message.toString())
        }
    }

}
    //

///////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////
    fun receiveItemFromFireBase(userId: String) {
        var listOfMarked = mutableListOf<MarkedModel>()
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val responde = fireBasePero.receiveItemsFromFireBase(userId)

                responde.addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if (error != null) {
                            markedComicErrorLiveData.postValue(error.message)
                            return
                        }

                        for (dc: DocumentChange in value?.documentChanges!!) {
                            if (dc.type == DocumentChange.Type.ADDED) {
                                listOfMarked.add(dc.document.toObject(MarkedModel::class.java))

                            }
                        }
                        Log.d(TAG, "ReciveItem list: $listOfMarked")
                        markLiveData.postValue(listOfMarked)
                        Log.d(TAG, "ReciveItem: $markLiveData")
                    }

                })

            } catch (e: Exception) {
                markedComicErrorLiveData.postValue(e.message.toString())
                Log.d(TAG, e.message.toString())
            }
        }
    }


}