package com.devcraft.tiktok.features.viewModel

import androidx.lifecycle.ViewModel

import android.net.Uri
class EditorViewModel : ViewModel() {
    private lateinit var videoURI: Uri


    fun setVideoURI(URI: Uri){
        videoURI = URI
    }
}