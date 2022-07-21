package com.example.myapplication.util

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import java.net.URL
/**
 * Created by Samira Salah
 */
class BindingExtentions{
    companion object{

@SuppressLint("ResourceType", "NewApi")
@JvmStatic
@BindingAdapter("app:loadBackgroundResourceImage")
fun loadBackgroundResourceImage(view: ImageView, path: String?) {
    view.scaleType = ImageView.ScaleType.CENTER_CROP

    if (path != null) {

          val  newPath = "https://image.tmdb.org/t/p/w300/"+path

            val options =
                RequestOptions().timeout(100000000).centerCrop()
                    .error(view.context.getDrawable(R.drawable.place_holder))
                    .placeholder(view.context.getDrawable(R.drawable.place_holder))
            Glide.with(view.context)
                .load(newPath)
                .apply(options).thumbnail(0.1f)
                .into(view)

    } else {
        view.setImageResource(R.drawable.place_holder)
    }
}

    }
}

