package com.example.gallery.fragments


import android.app.SharedElementCallback
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.Image
import com.example.gallery.MainActivity
import com.example.gallery.R
import com.example.gallery.RecyclerClickListener
import com.example.gallery.adapter.ImagesSource
import com.example.gallery.adapter.RecyclerAdapter


class RecyclerFragment() : Fragment() {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        recyclerView = inflater.inflate(R.layout.fragment_recycler, container, false) as RecyclerView
        recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = RecyclerAdapter(ImagesSource.findImages(), Listener(), this@RecyclerFragment)
        }


        postponeEnterTransition()

        return recyclerView
    }





    inner class Listener() : RecyclerClickListener {

        override fun onClick(image: Image, index: Int, itemImageView: View) {

            val view = itemImageView.findViewById<ImageView>(R.id.smallImage)
            MainActivity.currentPosition = index

            parentFragmentManager.commit {
                setReorderingAllowed(true)
                println(view.transitionName)
                addSharedElement(view, view.transitionName)
                replace(
                    R.id.fragmentContainerView,
                    ImageFragment.newInstance(image.uri.toString()),
                    ImageFragment::class.java.simpleName
                )
                addToBackStack(null)
            }
        }
    }
}

