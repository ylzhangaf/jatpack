package com.ylozhangaf.ppjoke.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import com.ylozhangaf.libcommon.PixUtils
import com.ylozhangaf.ppjoke.R

class ListPlayerView constructor(context: Context,
                                 attrs: AttributeSet?,
                                 defStyleAttr: Int,
                                 defStyleRes: Int): FrameLayout(context, attrs, defStyleAttr, defStyleRes){
    constructor(context: Context) : this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)


    private lateinit var bufferView : ProgressBar
    private lateinit var cover : DataBindingImageView
    private lateinit var background : DataBindingImageView
    private lateinit var play : ImageView

    private var category : String? = null
    private var videoUrl : String? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_player_view, this, true)
        bufferView = findViewById(R.id.pbar_buffer)
        cover = findViewById(R.id.ivew_cover)
        background = findViewById(R.id.ivew_background)
        play = findViewById(R.id.ivew_play)
    }

    fun bindData(category : String, widthPx : Int, heightPx : Int,
        coverUrl : String, videoUrl : String) {
        this.category =category
        this.videoUrl = videoUrl

        cover.setImageUrl(cover, coverUrl, false)
        if (widthPx < heightPx) {
            background.setBlurImageUrl(coverUrl, 10)
            background.visibility = View.VISIBLE
        } else {
            background.visibility = View.INVISIBLE
        }
        setSize(widthPx, heightPx)
    }

    private fun setSize(widthPx: Int, heightPx: Int) {
        val display = PixUtils.getScreenDisplay()
        val maxWidth = display.first
        val maxHeight = maxWidth

        val layoutWidth = maxWidth
        var layoutHeight = 0

        val coverWidth : Int
        var coverHeight : Int = 0

        if (widthPx >= heightPx) {
            coverWidth = maxWidth
            coverHeight = (heightPx / (widthPx * 1.0f / maxWidth)).toInt()
            layoutHeight = coverHeight
        } else {
            coverHeight = maxHeight
            layoutHeight = coverHeight
            coverWidth = (widthPx / (heightPx * 1.0f / maxHeight)).toInt()
        }

        val layoutParams = this.layoutParams
        layoutParams.width = layoutWidth
        layoutParams.height = layoutHeight
        this.layoutParams = layoutParams

        val blurParams = background.layoutParams
        blurParams.width = layoutWidth
        blurParams.height = layoutHeight
        background.layoutParams = blurParams

        val coverParams = cover.layoutParams as LayoutParams
        coverParams.width = coverWidth
        coverParams.height = coverHeight
        coverParams.gravity = Gravity.CENTER
        cover.layoutParams = coverParams

        val playParams = play.layoutParams as LayoutParams
        playParams.gravity = Gravity.CENTER
        play.layoutParams = playParams
    }
}