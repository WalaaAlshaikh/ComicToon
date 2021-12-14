package com.example.comictoon.views.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.comictoon.R
import com.example.comictoon.adaptersimport.MarkedAdapter
import com.example.comictoon.databinding.FragmentMarkedComicBinding
import com.example.comictoon.model.comic.MarkedModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase

private const val TAG = "MarkedComicFragment"
class MarkedComicFragment : Fragment() {
private lateinit var binding:FragmentMarkedComicBinding
private lateinit var markList:MutableList<MarkedModel>
private lateinit var markedAdapter:MarkedAdapter
private lateinit var db:FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMarkedComicBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        markList= mutableListOf<MarkedModel>()
        markedAdapter= MarkedAdapter()
        binding.comicProgressBar.animate().alpha(0f).setDuration(1000)
        markedAdapter.submittedList(markList)
        binding.markedRecyclerView.adapter=markedAdapter
        eventChangeList()





    }
    private fun delete(){
        val userId = Firebase.auth.currentUser!!.uid
        db= FirebaseFirestore.getInstance()
        db.collection("users").document("$userId").collection("favourite").document("comicId").delete()

    }

// the function below will get the data from Firestore Database after it was saved when clicking on mark comic
   private fun eventChangeList(){
       val userId = Firebase.auth.currentUser!!.uid
       db= FirebaseFirestore.getInstance()
       db.collection("users").document("$userId").collection("favourite").orderBy("title",Query.Direction.ASCENDING)
           .addSnapshotListener(object :EventListener<QuerySnapshot>{
               override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                  if(error != null){
                      Log.e("FireStore Error",error.message.toString())
                      return
                  }
                   for(dc:DocumentChange in value?.documentChanges!!){
                       if (dc.type == DocumentChange.Type.ADDED){
                           markList.add(dc.document.toObject(MarkedModel::class.java))
                       }
                   }

                   markedAdapter.notifyDataSetChanged()
               }

           })
   }


}