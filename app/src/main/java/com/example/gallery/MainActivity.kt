package com.example.gallery

import android.Manifest
import android.content.ContentResolver
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.add
import androidx.fragment.app.commit


const val TAG = "MyActivity"


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runtimePermissions()
        setContentView(R.layout.activity_main)
        val addFragmentButton = findViewById<Button>(R.id.addActivityButton)
        val deleteFragmentButton = findViewById<Button>(R.id.deleteActivityButton)
        val shareTransitionButton = findViewById<Button>(R.id.sharedTransitionButton)





        //Log.i(TAG, findImagesInAlbum("0").toString())

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<BlankFragment>(R.id.fragmentContainerView)
        }
        addFragmentButton.setOnClickListener {

            if (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) is WhiteScreen) {
                supportFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    setReorderingAllowed(true)

                    remove(supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as WhiteScreen)
                    add<BlankFragment>(R.id.fragmentContainerView)
                    addToBackStack("name")
                }
            }
        }
        deleteFragmentButton.setOnClickListener {
            if (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) is BlankFragment) {
                supportFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    setReorderingAllowed(true)
                    remove(supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as BlankFragment)
                    add<WhiteScreen>(R.id.fragmentContainerView)
                    addToBackStack("name")
                }
            }
        }

        shareTransitionButton.setOnClickListener {
            shareTransition()

    }


    }
    private fun shareTransition() {
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) is BlankFragment) {
            val itemImageView = findViewById<ImageView>(R.id.item_image)
            supportFragmentManager.commit {
                addSharedElement(itemImageView, "hero_image")
                replace(R.id.fragmentContainerView, ImageFragment.newInstance(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.circle_icons_image_svg) + '/' + resources.getResourceTypeName(R.drawable.circle_icons_image_svg) + '/' + resources.getResourceEntryName(R.drawable.circle_icons_image_svg)))
                addToBackStack("image")
            }
        } else if (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) is ImageFragment) {
            supportFragmentManager.popBackStack()
        }
    }
    private fun runtimePermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    0
                )
            }

        }
    }

    data class ImageM(val id: String, val name: String, val uri: Uri, var debug: String)

}

