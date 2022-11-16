package com.example.gallery.fragments

import android.graphics.drawable.Drawable
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
        prepareTransitions()


        postponeEnterTransition()
        image.transitionName = uri

        Glide.with(this)
            .load(uri)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(image)

        return image
    }

    private fun prepareTransitions() {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.image_shared_element_transition)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val heroImageView = view.findViewById<ImageView>(R.id.hero_image)
        Log.d(TAG, uri ?: "null")


        heroImageView.setOnClickListener {
            println(MainActivity.currentPosition)
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