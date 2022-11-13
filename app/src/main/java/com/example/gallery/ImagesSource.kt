package com.example.gallery

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore

class ImagesSource (private val contentResolver: ContentResolver){
    fun findImages(): List<MainActivity.Image> {
        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projections = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.DISPLAY_NAME
        )

        val findImages = HashMap<String, MainActivity.Image>()


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

                    val image = MainActivity.Image(
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

data class Image(val id: String, val name: String, val uri: Uri)