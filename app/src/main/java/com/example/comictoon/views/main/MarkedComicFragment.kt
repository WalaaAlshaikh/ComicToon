package com.example.comictoon.views.main

import MarkedComicViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.comictoon.R
import com.example.comictoon.adaptersimport.MarkedAdapter
import com.example.comictoon.databinding.FragmentMarkedComicBinding
import com.example.comictoon.model.comic.MarkedModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase

private const val TAG = "MarkedComicFragment"
class MarkedComicFragment : Fragment() {
private lateinit var binding:FragmentMarkedComicBinding
private lateinit var markList:MutableList<MarkedModel>
private lateinit var markedAdapter:MarkedAdapter
private lateinit var db:FirebaseFirestore
 val comic:MarkedComicViewModel by activityViewModels()
    private lateinit var bottomNav: BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observer()
        // Inflate the layout for this fragment
        bottomNav=activity!!.findViewById(R.id.bottomNavigation)
        bottomNav.visibility=View.VISIBLE
        binding= FragmentMarkedComicBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        markList= mutableListOf()
        markedAdapter= MarkedAdapter(requireActivity(),requireActivity().supportFragmentManager,comic)
        binding.comicProgressBar.animate().alpha(0f).setDuration(1000)
        binding.markedRecyclerView.adapter=markedAdapter


        comic.receiveItemFromFireBase(Firebase.auth.uid.toString())

       // markedAdapter.submittedList(markList)






    }
    fun observer(){
        comic.markLiveData.observe(viewLifecycleOwner,{

           it?.let {

               Log.d(TAG, it.size.toString())
               markedAdapter.submittedList(it)

               comic.markLiveData.postValue(null)

           }
        })
        comic.markedComicErrorLiveData.observe(viewLifecycleOwner,{
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            comic.markedComicErrorLiveData.postValue(null)
        })

        comic.markedStringComicLiveData.observe(viewLifecycleOwner,{
            it?.let {
                comic.receiveItemFromFireBase(Firebase.auth.uid.toString())
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                comic.markedStringComicLiveData.postValue(null)
                markedAdapter.submittedList(markList)

        }
        })

    }

    override fun onDestroy() {
        super.onDestroy()



    }

// the function below will get the data from Firestore Database after it was saved when clicking on mark comic

//   private fun eventChangeList(){
//       val userId = Firebase.auth.currentUser!!.uid
//       db = FirebaseFirestore.getInstance()
//
//       db.collection("users").document(userId).collection("favourite").orderBy("title",Query.Direction.ASCENDING)
//           .addSnapshotListener(object :EventListener<QuerySnapshot>{
//               @SuppressLint("NotifyDataSetChanged")
//               override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
//                  if(error != null){
//                      Log.e("FireStore Error",error.message.toString())
//                      return
//                  }
//                   for(dc:DocumentChange in value?.documentChanges!!){
//                       if (dc.type == DocumentChange.Type.ADDED){
//                           markList.add(dc.document.toObject(MarkedModel::class.java))
//                       }
//                   }
//
//                   Log.d(TAG,markList.size.toString())
//                   markedAdapter.submittedList(markList)
//
//               }
//
//           })
//
//   }


}