package com.ylozhangaf.libcommon.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.google.android.material.button.MaterialButton
import com.ylozhangaf.libcommon.R

class EmptyView constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    constructor(context: Context) : this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    var icon : ImageView
    var title : TextView
    var action : MaterialButton

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true)
        orientation = VERTICAL
        gravity = Gravity.CENTER

        icon = findViewById(R.id.empty_icon)
        title = findViewById(R.id.empty_text)
        action = findViewById(R.id.empty_action)
    }

    fun setEmptyIcon(@DrawableRes iconRes : Int) {
        icon.setImageResource(iconRes)
    }

    fun setTitle(titleStr : String, listener : OnClickListener) {
        if (TextUtils.isEmpty(titleStr)) {
            title.visibility = View.GONE
        } else {
            title.text = titleStr
            title.visibility = View.VISIBLE
            title.setOnClickListener(listener)
        }
    }
}