package com.ylozhangaf.libnetwork

import java.lang.StringBuilder
import java.net.URLEncoder
import java.util.*

class UriCreator {

    companion object{
        fun createUrlFromParams(url : String, params : Map<String,Any>) : String{
            val builder = StringBuilder()
            builder.append(url)
            if (url.indexOf("?") > 0 || url.indexOf("&") > 0) {
                builder.append("&")
            } else {
                builder.append("?")
            }

            params.forEach {
                try{
                    val value = URLEncoder.encode(it.value.toString(), "UTF-8")
                    builder.append(it.key).append("=").append(value).append("&")
                } catch (exception :Exception) {
                    exception.printStackTrace()
                }
            }
            builder.deleteCharAt(builder.length-1)
            return builder.toString()
        }
    }

}