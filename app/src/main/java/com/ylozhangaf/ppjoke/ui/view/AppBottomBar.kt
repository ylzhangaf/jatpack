package com.ylozhangaf.ppjoke.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.ylozhangaf.ppjoke.R
import com.ylozhangaf.ppjoke.utils.AppConfig

@SuppressLint("RestrictedApi")
class AppBottomBar constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): BottomNavigationView(
    context,
    attrs,
    defStyleAttr
) {

    private val sIcons = arrayOf(
        R.drawable.icon_tab_home,
        R.drawable.icon_tab_sofa,
        R.drawable.icon_tab_publish,
        R.drawable.icon_tab_find,
        R.drawable.icon_tab_mine
    )


    constructor(context: Context) : this(context, null, 0)


    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    init {
        val bottomBar = AppConfig.getBottomBar()
        val tabs = bottomBar.tabs

        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_selected)
        states[1] = intArrayOf()

        val colors = intArrayOf(
            Color.parseColor(bottomBar.activeColor),
            Color.parseColor(bottomBar.inActiveColor)
        )
        val colorStateList = ColorStateList(states, colors)
        itemIconTintList = colorStateList
        itemTextColor = colorStateList
        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        selectedItemId = bottomBar.selectTab

        tabs.forEach {
            if (it.enable) {
                val id = getId(it.pageUrl)
                if (id >= 0) {
                    val menuItem = menu.add(0, id, it.index, it.title)
                    menuItem.setIcon(sIcons[it.index])
                }
            }
        }

        tabs.forEachIndexed { index, tab ->
            if (tab.enable) {
                val id = getId(tab.pageUrl)
                if (id >= 0) {
                    val iconSize = dp2px(tab.size)
                    val menuView = getChildAt(0) as BottomNavigationMenuView
                    val itemView = menuView.getChildAt(index) as BottomNavigationItemView
                    itemView.setIconSize(iconSize)

                    if (TextUtils.isEmpty(tab.title)) {
                        itemView.setIconTintList(ColorStateList.valueOf(Color.parseColor(tab.tintColor)))
                        itemView.setShifting(false)
                    }
                }
            }
        }

        val selectTab = bottomBar.tabs[bottomBar.selectTab]
        if (selectTab.enable) {
            val itemId = getId(selectTab.pageUrl)
            post {
                selectedItemId = itemId
            }
        }
    }

    private fun dp2px(size : Int) : Int {
        return (context.resources.displayMetrics.density * size + 0.5f).toInt()
    }

    private fun getId(pageUrl: String) : Int{
        AppConfig.getDestConfig()[pageUrl]?.let {
            return it.id
        } ?: return -1
    }

}