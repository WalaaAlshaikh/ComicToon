package com.example.comictoon.views.videos

import android.content.Context
import android.widget.MediaController
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.comictoon.R
import com.example.comictoon.databinding.FragmentUpdateBinding
import com.example.comictoon.databinding.FragmentVideoDialogBinding
import com.example.comictoon.model.videos.Result
import android.webkit.WebViewClient

import android.webkit.WebSettings.PluginState

import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebChromeClient





private const val TAG = "VideoDialogFragment"
class VideoDialogFragment (): DialogFragment() {
    private val videoViewModel:VideoViewModel by activityViewModels()
    //private lateinit var videoResult:Result

    private var list= mutableListOf<Result>()

    private lateinit var binding: FragmentVideoDialogBinding
//    override fun onStart() {
//        super.onStart()
//        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
//
//        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentVideoDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //videoResult=videoViewModel.selectedLiveData


        videoViewModel.selectedLiveData.observe(viewLifecycleOwner,{
            binding.videoProgressBar.animate().alpha(0f)
//            var mediaControls: MediaController? = null
//            if (mediaControls == null) {
//                //  creating an object of media controller class
//                mediaControls = MediaController(requireContext())
//
//                // set the anchor view for the video view
//                mediaControls.setAnchorView(binding.simpleVideoView)
//                binding.simpleVideoView.setMediaController(mediaControls)
//                binding.simpleVideoView.setVideoURI(
//                    Uri.parse(it.lowUrl))
//                binding.simpleVideoView.requestFocus()
//                binding.simpleVideoView.start()
//            }

//            val controller = MediaController(requireActivity())
//            controller.setAnchorView(binding.simpleVideoView)
//            controller.setMediaPlayer(binding.simpleVideoView)
//            binding.simpleVideoView.setMediaController(controller)
//            binding.simpleVideoView.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=${it.youtubeId}"))
//
//            binding.simpleVideoView.setOnPreparedListener { mp ->
//                val lp: ViewGroup.LayoutParams = binding.simpleVideoView.getLayoutParams()
//                val videoWidth: Int = mp.videoWidth
//                val videoHeight: Int = mp.videoHeight
//                val viewWidth: Int = binding.simpleVideoView.width
//                lp.height = (viewWidth * (videoHeight / videoWidth)).toInt()
//                binding.simpleVideoView.setLayoutParams(lp)
//                //  if (!videoView.isPlaying()) optional
//                binding.simpleVideoView.start()
//            }

            val webViewSettings: WebSettings = binding.webView.getSettings()
            webViewSettings.javaScriptCanOpenWindowsAutomatically = true
            webViewSettings.javaScriptEnabled = true
//            webViewSettings.pluginState
            webViewSettings.builtInZoomControls = true
//            webViewSettings.pluginState = PluginState.ON

//
            binding.webView.setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    return false
                }
            })
            binding.webView.setWebChromeClient(WebChromeClient())

            binding.webView.loadUrl("https://www.youtube.com/watch?v=${it.youtubeId}"
            )
        })




    }


}