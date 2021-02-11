package com.ylozhangaf.ppjoke.ui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.ylozhangaf.libcommon.PixUtils
import jp.wasabeef.glide.transformations.BlurTransformation

class DataBindingImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : AppCompatImageView(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)


    @BindingAdapter(value = ["image_url", "isCircle"], requireAll = true) // 只有两个参数都设置了才会调用方法
    fun setImageUrl(imageView: DataBindingImageView, imageUrl: String, isCircle: Boolean) {
        val builder = Glide.with(imageView).load(imageUrl)
        if (isCircle) {
            builder.transform(CircleCrop())
        }
        // 防止图片过大,设置大小
        val layoutParams = imageView.layoutParams
        if (layoutParams != null && layoutParams.width > 0 && layoutParams.height > 0) {
            builder.override(layoutParams.width, layoutParams.height)
        }
        builder.into(imageView)

    }

    fun bindData(widthPx: Int,
                 heightPx: Int,
                 marginLeft: Int,
                 imageUrl: String) {
        val display = PixUtils.getScreenDisplay()
        bindData(widthPx,heightPx,marginLeft, display.first, display.second, imageUrl)
    }

    fun bindData(
        widthPx: Int,
        heightPx: Int,
        marginLeft: Int,
        maxWidth: Int,
        maxHeight: Int,
        imageUrl: String
    ) {
        if (widthPx <= 0 || heightPx <= 0) {
            Glide.with(this).load(imageUrl).into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    val width = resource.intrinsicWidth
                    val height = resource.intrinsicHeight
                    setSize(width, height, marginLeft, maxWidth, maxHeight)
                    setImageDrawable(resource)
                }

            })
        } else {
            setSize(widthPx, heightPx, marginLeft, maxWidth, maxHeight)
            setImageUrl(this, imageUrl, false)
        }
    }

    private fun setSize(
        width: Int,
        height: Int,
        marginLeft: Int,
        maxWidth: Int,
        maxHeight: Int
    ) {
        var finalWidth : Int
        var finalHeight : Int
        if (width > height) {
            finalWidth = maxWidth
            finalHeight = (height / (width * 1.0f / finalWidth)).toInt()
        } else {
            finalHeight = maxHeight
            finalWidth =  (width / (height * 1.0f / finalHeight)).toInt()
        }

        val params = ViewGroup.MarginLayoutParams(finalWidth,finalHeight).apply {
            this.leftMargin = if(height > width) PixUtils.dp2px(marginLeft) else 0
        }
        layoutParams = params
    }

    fun setBlurImageUrl(coverUrl : String, radius : Int) {
        Glide.with(this).load(coverUrl).override(50)
            .transform(BlurTransformation())
            .dontAnimate()
            .into(object : SimpleTarget<Drawable>(){
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    background = resource
                }

            })
    }
}