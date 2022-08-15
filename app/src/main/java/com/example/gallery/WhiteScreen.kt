package com.example.gallery

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.os.Bundle
import android.provider.MediaStore
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_white_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)

        recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = RecyclerAdapter(findImages())
        }
        startPostponedEnterTransition()
    }

    private fun findImages(): List<MainActivity.ImageM> {
        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projections = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.DISPLAY_NAME
        )

        val findImages = HashMap<String, MainActivity.ImageM>()


        requireActivity().contentResolver.query(
            contentUri, projections, null, null,
            "${MediaStore.Images.ImageColumns.DATE_TAKEN} ASC"
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
                val displayNameIndex =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME)
                val debugIndex =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)
                do {
                    val mediaId = cursor.getLong(idIndex)
                    val filename = cursor.getString(displayNameIndex)
                    val uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        mediaId
                    )
                    val debug = cursor.getLong(debugIndex).toString()

                    val image = MainActivity.ImageM(
                        id = mediaId.toString(),
                        name = filename,
                        uri = uri,
                        debug = debug,
                    )
                    findImages[mediaId.toString()] = image


                } while (cursor.moveToNext())
            }
        }

        return findImages.values.toList().sortedByDescending { it.name }
    }


}

