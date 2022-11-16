package com.example.gallery.adapter

import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore
import com.example.gallery.Image
import java.lang.IllegalStateException

object ImagesSource{
    private lateinit var contentResolver: ContentResolver
    var isInitialized: Boolean = false
    private set


    fun init(contentResolver: ContentResolver) {
        ImagesSource.contentResolver = contentResolver
        isInitialized = true
    }

    fun findImages(): List<Image> {
        if (!isInitialized) throw IllegalStateException("ImageSource should be initialized with contentResolver before use")

        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projections = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.DISPLAY_NAME
        )

        val findImages = HashMap<String, Image>()


        contentResolver.query(
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

                    val image = Image(
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

