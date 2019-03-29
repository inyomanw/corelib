package com.inyomanw.corelibrary.utils

import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * [transformRequest] Extension func to transform Glide Request Builder, it won't use animation transition
 * and use immediate load priority & RESOURCE cache
 * @author herisulistiyanto
 * @param placeholder DrawableResID to use as placeholder
 * @param errorPlaceholder DrawableResID to use as placeholder IF error occur on load process
 */
fun RequestBuilder<Drawable>.transformRequest(@DrawableRes placeholder: Int,
                                              @DrawableRes errorPlaceholder: Int,
                                              cropMode: BinarCropMode = BinarCropMode.CENTER_CROP)
        : RequestBuilder<Drawable> {
    val options = RequestOptions().apply {
        priority(Priority.IMMEDIATE)
        diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        dontAnimate()
        placeholder(placeholder)
        error(errorPlaceholder)
        when (cropMode) {
            BinarCropMode.CENTER_CROP -> centerCrop()
            BinarCropMode.FIT_CENTER -> fitCenter()
            BinarCropMode.CENTER_INSIDE -> centerInside()
            BinarCropMode.CIRCLE_CROP -> circleCrop()
            else -> {
            }
        }
    }
    return this.apply(options)
}

/**
 * [loadImageWithPlaceHolder] Ext Func to use Glide from ImageView
 * @author herisulistiyanto
 * @param imageUrl nullable string url to be loaded
 * @param placeholder DrawableResID to use as placeholder
 * @param errorPlaceholder DrawableResID to use as placeholder IF error occur on load process
 * @param delimiter use specific char to substring url, by default it will use '<b>?</b>' char
 * @param onSuccessLoad is a func to handle success load callback
 * @param onErrorLoad is a func to handle error load callback
 */
fun ImageView.loadImageWithPlaceHolder(imageUrl: String?,
                                       @DrawableRes placeholder: Int,
                                       @DrawableRes errorPlaceholder: Int,
                                       delimiter: String = "?",
                                       cropMode: BinarCropMode = BinarCropMode.CENTER_CROP,
                                       onSuccessLoad: () -> Unit = {},
                                       onErrorLoad: () -> Unit = {}) {
    val fixedUrl = imageUrl?.substringBefore(delimiter) ?: ""
    Glide.with(this.context)
        .load(fixedUrl)
        .transformRequest(placeholder, errorPlaceholder, cropMode)
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(resource: Drawable?, model: Any?,
                                         target: Target<Drawable>?, dataSource: DataSource?,
                                         isFirstResource: Boolean): Boolean {
                onSuccessLoad.invoke()
                return false
            }

            override fun onLoadFailed(e: GlideException?, model: Any?,
                                      target: Target<Drawable>?,
                                      isFirstResource: Boolean): Boolean {
                onErrorLoad.invoke()
                return false
            }
        })
        .into(this)
}

enum class BinarCropMode {
    CENTER_CROP,
    CENTER_INSIDE,
    FIT_CENTER,
    CIRCLE_CROP,
    NO_CROP
}