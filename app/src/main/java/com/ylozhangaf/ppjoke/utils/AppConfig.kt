package com.ylozhangaf.ppjoke.utils

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.ylozhangaf.libcommon.AppGlobals
import com.ylozhangaf.ppjoke.model.BottomBar
import com.ylozhangaf.ppjoke.model.Destination

class AppConfig {

    companion object{
        private var sDestConfig = hashMapOf<String, Destination>()
        private var sBottomBar : BottomBar? = null

        fun getDestConfig() : HashMap<String, Destination> {
            if (sDestConfig.isEmpty()) {
                val content = parseFile("destination.json")
                sDestConfig = JSON.parseObject(
                    content, object : TypeReference<HashMap<String, Destination>>() {})
            }
            return sDestConfig
        }

        fun getBottomBar() : BottomBar{
            if (sBottomBar == null) {
                val content = parseFile("menu_bottom_bar.json")
                sBottomBar = JSON.parseObject(
                    content, BottomBar::class.java
                )
            }
            return sBottomBar!!
        }

        private fun parseFile(fileName: String) : String {
            AppGlobals.getApplication().resources.assets.apply {
                try {
                    open(fileName).use {
                        return it.bufferedReader().readLines().joinToString(",")
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }

            }
            return ""
        }
    }


}