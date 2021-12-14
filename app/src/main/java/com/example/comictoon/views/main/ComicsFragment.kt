package com.example.comictoon.views.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.comictoon.R
import com.example.comictoon.adaptersimport.ComicAdapter
import com.example.comictoon.databinding.FragmentComicsBinding
import com.example.comictoon.model.comic.Result
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "ComicsFragment"
class ComicsFragment : Fragment() {
    private lateinit var binding:FragmentComicsBinding
    private lateinit var comicAdapter:ComicAdapter


    private val comicViewModel:ComicViewModel by activityViewModels()
    private var list= mutableListOf<Result>()
    private lateinit var profileItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentComicsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val db = Firebase.firestore

        val recyclerView: RecyclerView =view.findViewById(R.id.comic_recyclerView)
       comicAdapter= ComicAdapter(comicViewModel)
        recyclerView.adapter=comicAdapter
        comicViewModel.callomics()

        comicViewModel.comicLiveData.observe(viewLifecycleOwner,{
            binding.comicProgressBar.animate().alpha(0f)
            comicAdapter.submittedList(it.results)
        })





    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profile_item -> {
                findNavController().navigate(R.id.action_comicsFragment_to_profileFragment)
            }
        }
        return super.onOptionsItemSelected(item)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.main_menu,menu)
        val searchItem=menu.findItem(R.id.app_bar_search)
        profileItem=menu.findItem(R.id.profile_item)

        val searchView=searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                comicAdapter.submittedList(list.filter { it.name.lowercase().contains(query!!.lowercase()) })
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
                comicAdapter.submittedList(list)
                return true
            }

        })


    }




}