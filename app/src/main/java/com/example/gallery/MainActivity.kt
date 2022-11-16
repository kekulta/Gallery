package com.example.gallery

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.gallery.fragments.BlankFragment
import com.example.gallery.adapter.ImagesSource
import com.example.gallery.fragments.ImageFragment

import com.example.gallery.fragments.RecyclerFragment


const val POSITION_KEY = "CURRENT_POSITION_KEY"
const val TAG = "MyActivity"


class MainActivity : AppCompatActivity() {

    companion object {
        var currentPosition = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runtimePermissions()
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(POSITION_KEY, 0)
            return
        }

        ImagesSource.init(contentResolver)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<RecyclerFragment>(R.id.fragmentContainerView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(POSITION_KEY, currentPosition)
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




}

