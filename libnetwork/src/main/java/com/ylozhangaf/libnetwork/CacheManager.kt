package com.ylozhangaf.libnetwork

import com.ylozhangaf.libnetwork.cache.Cache
import com.ylozhangaf.libnetwork.cache.CacheDatabase
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object CacheManager {
    fun <T> save(key: String, body: T) {
        val cache = Cache(key)
        cache.data = toByteArray(body)
        CacheDatabase.getDataBase().getCache().save(cache)
    }

    fun <T> getCache(key : String) : T? {
        val cache = CacheDatabase.getDataBase().getCache().getCache(key)
        cache?.data?.let {
            return toObject(it)
        } ?: return null
    }

    private fun <T> toObject(data : ByteArray) : T? {
        ByteArrayInputStream(data).use {byteArrayInputStream ->
            val objectInputStream = ObjectInputStream(byteArrayInputStream)
            objectInputStream.use {
                return it.readObject() as T
            }
        }
    }

    private fun <T> toByteArray(body : T) : ByteArray?{
         ByteArrayOutputStream().use { byteArrayOutputStream ->
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.use {
                it.writeObject(body)
                return byteArrayOutputStream.toByteArray()
            }
        }

    }

}
