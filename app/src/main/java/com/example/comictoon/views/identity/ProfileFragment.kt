package com.example.comictoon.views.identity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.comictoon.R
import com.example.comictoon.databinding.FragmentProfileBinding
import com.example.comictoon.util.Permission
import com.example.comictoon.util.Permission.checkPermission
import com.example.comictoon.views.main.SHARED_PREF_FILE
import com.example.comictoon.views.main.STATE
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.rpc.context.AttributeContext
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import java.security.Permissions

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrfEditor: SharedPreferences.Editor
    private lateinit var bottomNav: BottomNavigationView
    private val updateItemViewModel: UpdateProfileImageViewModel by activityViewModels()
    private val IMAGE_PICKER = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // calling the bottom navigation bar to set it GONE in this page
        bottomNav = activity!!.findViewById(R.id.bottomNavigation)
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        sharedPrfEditor = sharedPref.edit()
        // to check the permission of getting the access of the gallery of the phone
        checkPermission(requireContext(), requireActivity())


        binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val localizationDelegate = LocalizationActivityDelegate(requireActivity())
        bottomNav.visibility = View.GONE

        // set the language into Arabic when clicking on it
        binding.acivBotton.setOnClickListener {
            localizationDelegate.setLanguage(requireContext(), "ar")

        }
        // set the language into English when clicking on it
        binding.engButton.setOnClickListener {
            localizationDelegate.setLanguage(requireContext(), "en")

        }
        updateItemViewModel.updateImageLiveData.observe(viewLifecycleOwner, {

            it?.let {
                Toast.makeText(requireActivity(), "image_upload_successfully", Toast.LENGTH_SHORT)
                    .show()
            }

            updateItemViewModel.updateImageLiveData.postValue(null)

        })

        binding.circleImageView.setOnClickListener {


            ImagePicker()
        }

        // getting the login info after registration

        FirebaseAuth.getInstance().currentUser?.let {

            Log.d(TAG, it.displayName.toString())
            binding.userIdTextView.text = it.displayName // username
            binding.emailTextView.text = it.email // email address
            Log.d(TAG, it.photoUrl.toString())

            Glide
                .with(requireContext())
                .load(it.photoUrl)
                .into(binding.circleImageView)
        }

        binding.logoutButton.setOnClickListener {
            /** pop up warning window for confirmation of logging out from account
             * if it's logged out, it will move to login page*/

            val aluilder = AlertDialog.Builder(requireContext())
            aluilder.setTitle(getString(R.string.logot_notice))
            aluilder.setMessage(getString(R.string.delete_message))
            aluilder.setPositiveButton(getString(R.string.yes)) { dialogInterface, which ->
                FirebaseAuth.getInstance().signOut()
                // to reset the shared preference state ti false value so it can be moved to login page
                sharedPrfEditor.putBoolean(STATE, false).commit()

                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }

            aluilder.setNegativeButton(getString(R.string.no)) { dialogInterface, which ->

            }
            val theDialog: AlertDialog = aluilder.create()
            theDialog.setCancelable(false)
            theDialog.show()

        }

    }

    // showing ImagePicker using Matisse library
    fun ImagePicker() {
        Matisse.from(this)
            .choose(MimeType.ofImage(), false) // image or image and video or whatever
            .captureStrategy(CaptureStrategy(true, "com.example.comictoon"))
            .forResult(IMAGE_PICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICKER && resultCode == Activity.RESULT_OK) {
            //using Matisse library to take uri of chosen image
            //[0] index 0 to take first index of the array of photo selected

            val imageUri: Uri? = Matisse.obtainResult(data)[0]
            Log.d(TAG, imageUri.toString())

            val profileUpdates = UserProfileChangeRequest.Builder()

                .setPhotoUri(imageUri)
                .build()
            FirebaseAuth.getInstance().currentUser?.updateProfile(profileUpdates)

            Glide
                .with(requireContext())
                .load(imageUri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.circleImageView)


            imageUri?.let { updateItemViewModel.updateItemImage(imageUri!!) }


        }
    }

}