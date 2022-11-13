package com.example.gallery


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match


/**
 * A simple [Fragment] subclass.
 * Use the [WhiteScreen.newInstance] factory method to
 * create an instance of this fragment.
 */


class WhiteScreen() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_white_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val imagesSource = ImagesSource(requireActivity().contentResolver)


        recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = RecyclerAdapter(imagesSource.findImages())

        }
        startPostponedEnterTransition()
    }
}

