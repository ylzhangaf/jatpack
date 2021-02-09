package com.ylozhangaf.libnetwork

import com.alibaba.fastjson.JSON
import java.lang.reflect.Type

class JsonConvert : Convert<Any> {


    override fun convert(response: String, type: Type): Any? {
        val jsonObject = JSON.parseObject(response)
        val data = jsonObject.getJSONObject("data")
        if (data != null) {
            val childData = data.get("data")
            return JSON.parseObject(childData.toString(), type)
        }
        return null
    }

    override fun convert(response: String, claz: Class<Any>): Any? {
        val jsonObject = JSON.parseObject(response)
        val data = jsonObject.getJSONObject("data")
        if (data != null) {
            val childData = data.get("data")
            return JSON.parseObject(childData.toString(), claz)
        }
        return null
    }
}