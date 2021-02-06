package com.ylozhangaf.libcommon

import android.app.Application

class AppGlobals {

    companion object{
        private var sApplication : Application? = null

        fun getApplication() : Application {
            if (sApplication == null) {
                try {
                    val method = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication")
                    sApplication = method.invoke(null) as Application
                } catch (exception : Exception) {
                    exception.printStackTrace()
                }
            }

            return sApplication!!
        }
    }

}