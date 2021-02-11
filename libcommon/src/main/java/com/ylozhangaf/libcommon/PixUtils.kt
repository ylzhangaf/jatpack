package com.ylozhangaf.libcommon

object PixUtils {

    fun dp2px(dpValue : Int) : Int{
        val metrics = AppGlobals.getApplication().resources.displayMetrics
        return (metrics.density * dpValue + 0.5f).toInt()
    }

    fun getScreenDisplay() : Pair<Int,Int>{
        AppGlobals.getApplication().resources.displayMetrics.apply {
            return this.widthPixels to this.heightPixels
        }
    }
}