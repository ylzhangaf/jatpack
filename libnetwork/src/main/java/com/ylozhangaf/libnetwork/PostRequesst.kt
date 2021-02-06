package com.ylozhangaf.libnetwork

import okhttp3.FormBody

class PostRequesst<T>(url : String) : Request<T, PostRequesst<T>>(url){

    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request? {
        val bodyBuilder = FormBody.Builder()
        params.forEach {
            bodyBuilder.add(it.key, it.value.toString())
        }
        return builder.url(url).post(bodyBuilder.build()).build()
    }
}