package com.example.gallery.fragments

import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.core.view.ViewCompat
import com.example.gallery.MainActivity
import com.example.gallery.R
import com.example.gallery.TAG

private const val URI_PARAM = "URI"


class ImageFragment : Fragment() {
    private var uri: String? = null

    lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            uri = it.getString(URI_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        image = inflater.inflate(R.layout.fragment_image, container, false) as ImageView
        image.transitionName = uri
        image.setImageURI(Uri.parse(uri))

        prepareTransitions()

        return image
    }

    private fun prepareTransitions() {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val heroImageView = view.findViewById<ImageView>(R.id.hero_image)
        Log.d(TAG, uri ?: "null")


        heroImageView.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(uri: String) =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putString(URI_PARAM, uri)
                }
            }
    }
}