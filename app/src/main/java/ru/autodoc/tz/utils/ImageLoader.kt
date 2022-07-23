package ru.autodoc.tz.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.autodoc.tz.R

object ImageLoader {
    fun picasso(url: String?, imageView: ImageView) {
        Picasso.get()
            .load(url)
            .fit().centerCrop()
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .into(imageView)
    }
}