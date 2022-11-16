package com.example.gallery.fragments

import android.content.ContentResolver
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.commit
import com.example.gallery.R


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

        itemImageView.transitionName = "item_image"

        val uri =
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(
                R.drawable.circle_icons_image_svg
            ) + '/' + resources.getResourceTypeName(R.drawable.circle_icons_image_svg) + '/' + resources.getResourceEntryName(
                R.drawable.circle_icons_image_svg
            )

        itemImageView.setOnClickListener {
            parentFragmentManager.commit {
                addSharedElement(itemImageView, uri)
                replace(
                    R.id.fragmentContainerView, ImageFragment.newInstance(
                        uri
                    )
                )

                addToBackStack("image")
            }
        }
    }
}

