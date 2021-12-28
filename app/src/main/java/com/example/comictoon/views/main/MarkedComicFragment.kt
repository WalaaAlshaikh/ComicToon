package com.example.comictoon.views.main

import MarkedComicViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }
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
               if(it.isNotEmpty()){
                   binding.visibilityLayout.visibility=View.INVISIBLE
               }else{
                   binding.visibilityLayout.visibility=View.VISIBLE
               }
               markedAdapter.submittedList(it)
               markList=it as MutableList<MarkedModel>
               binding.markedRecyclerView.animate().alpha(1f)




           }
            comic.markLiveData.postValue(null)
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


    //the system calls onCreateOptionsMenu() when starting the activity, in order to show items to the app bar.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.main_menu,menu)
        val searchItem=menu.findItem(R.id.app_bar_search)

        val searchView=searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                markedAdapter.submittedList(markList.filter { it.title!!.lowercase().contains(query!!.lowercase()) })
                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                markedAdapter.submittedList(markList)
                return true
            }

        })

    }

}