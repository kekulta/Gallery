package com.example.gallery.adapter

import android.graphics.drawable.Drawable
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
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class RecyclerAdapter(
    private val images: List<Image>,
    private val listener: RecyclerClickListener,
    private val fragment: Fragment
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val requestManager = Glide.with(fragment)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val image: ImageView = itemView.findViewById(R.id.card_image)

        init {
            image.setOnClickListener(this)
        }

        fun onBind() {
            setImage()

            image.transitionName = images[adapterPosition].uri.toString()
        }

        private fun setImage() {
            requestManager.load(images[adapterPosition].uri).listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any,
                    target: Target<Drawable?>, isFirstResource: Boolean
                ): Boolean {
                    onLoaded()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    onLoaded()
                    return false
                }
            }).into(image)
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
            .inflate(R.layout.card_image, viewGroup, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.onBind()
    }

    override fun getItemCount(): Int {
        return images.size
    }


}

