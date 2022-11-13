package com.example.gallery

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.commit


class BlankFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemImageView = view.findViewById<ImageView>(R.id.item_image)
        ViewCompat.setTransitionName(itemImageView, "item_image")

        itemImageView.setOnClickListener{
            parentFragmentManager.commit {
                addSharedElement(itemImageView, "hero_image")
                replace(R.id.fragmentContainerView, ImageFragment.newInstance(
                    ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.circle_icons_image_svg) + '/' + resources.getResourceTypeName(R.drawable.circle_icons_image_svg) + '/' + resources.getResourceEntryName(R.drawable.circle_icons_image_svg)))

                addToBackStack("image")
            }
        }
    }
}

