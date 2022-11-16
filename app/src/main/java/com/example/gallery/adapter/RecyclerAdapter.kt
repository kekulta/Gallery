package com.example.gallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.Image
import com.example.gallery.MainActivity
import com.example.gallery.R
import com.example.gallery.RecyclerClickListener


class RecyclerAdapter(private val images: List<Image>, private val listener: RecyclerClickListener, private val fragment: Fragment) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val image: ImageView = itemView.findViewById(R.id.smallImage)
        init {
            image.setOnClickListener(this)
        }

        fun onBind() {
            setImage()

            image.transitionName = images[adapterPosition].uri.toString()
        }

        private fun setImage() {
            image.setImageURI(images[adapterPosition].uri)
            onLoaded()
        }

        private fun onLoaded() {
            if (adapterPosition == MainActivity.currentPosition) fragment.startPostponedEnterTransition()
        }

        override fun onClick(view: View?) {
            listener.onClick(images[adapterPosition], adapterPosition, image)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.frame_textview, viewGroup, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.onBind()
    }

    override fun getItemCount(): Int {
        return images.size
    }


}

