package com.example.gallery

import android.net.Uri
import android.view.View

interface RecyclerClickListener {
    fun onClick(image: Image, index: Int, itemImageView: View)
}

data class Image(val id: String, val name: String, val uri: Uri, val debug: String)