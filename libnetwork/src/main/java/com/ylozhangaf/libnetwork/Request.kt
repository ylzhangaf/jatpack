package com.ylozhangaf.libnetwork

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Log
import androidx.annotation.IntDef
import androidx.arch.core.executor.ArchTaskExecutor
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.io.Serializable
import java.lang.NullPointerException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class Request<T, R>(val url: String) {
    var headers = hashMapOf<String, String>()
    var params = hashMapOf<String, Any>()
    var cacheKey: String = ""
    var type: Type? = null
    var claz: Class<Any>? = null
    var cacheStrategy: Int = -1

    @IntDef(CACHE_ONLY, CACHE_FIRST, NET_ONLY, NET_CACHE)
    annotation class CacheStrategy

    fun cacheStrategy(@CacheStrategy cacheStrategy: Int): R {
        this.cacheStrategy = cacheStrategy
        return this as R
    }

    fun addHeader(key: String, value: String): R {
        headers[key] = value
        return this as R
    }

    fun addParam(key: String, value: Any): R {
        try {
            val field = value.javaClass.getField("TYPE")
            val claz = field.get(null) as Class<*>
            if (claz.isPrimitive) {
                params[key] = value
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return this as R
    }

    fun cacheKey(key: String): R {
        this.cacheKey = key
        return this as R
    }

    fun responseType(type: Type): R {
        this.type = type
        return this as R
    }

    fun responseType(claz: Class<Any>): R {
        this.claz = claz
        return this as R
    }

    fun execute(): ApiResponse<T>? {
        if (cacheStrategy == CACHE_ONLY) {
            return readCache()
        }

        try {
            val response = getCall()?.execute() ?: throw NullPointerException("execute : call为空")
            return parseResponse(response, null)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return null
    }

    @SuppressLint("RestrictedApi")
    fun execute(callBack: JsonCallBack<T>) {
        // 获取数据库数据
        if (cacheStrategy != NET_ONLY) {
            ArchTaskExecutor.getIOThreadExecutor().execute {
                val response = readCache()
                callBack?.let {
                    it.onCacheSuccess(response)
                }
            }
        }

        // 网络请求
        if (cacheStrategy != CACHE_ONLY) {
            getCall()?.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val response = ApiResponse<T>()
                    response.returnMessage = e.message ?: "error"
                    callBack.onError(response)
                }

                override fun onResponse(call: Call, response: Response) {
                    val apiResponse: ApiResponse<T> = parseResponse(response, callBack)
                    if (apiResponse.isSuccess()) {
                        callBack.onSuccess(apiResponse)
                    } else {
                        callBack.onError(apiResponse)
                    }
                }

            })
        }
    }

    private fun readCache(): ApiResponse<T> {
        val key = if (TextUtils.isEmpty(cacheKey)) generateCacheKey() else cacheKey
        val cache = CacheManager.getCache<T>(key)
        return ApiResponse<T>().apply {
            statusCode = ApiResponse.RETURNCODE_SUCCESS_FROM_CACHE
            returnMessage = "get cache success"
            body = cache
        }
    }

    private fun parseResponse(response: Response, callBack: JsonCallBack<T>?): ApiResponse<T> {
        var message: String? = null
        val status = response.code
        var isSuccess = response.isSuccessful
        val result = ApiResponse<T>()
        try {
            val content = response.body.toString()
            if (isSuccess) {
                if (callBack == null) {
                    when {
                        type != null -> {
                            result.body = ApiService.convert?.convert(content, type!!) as T
                        }
                        claz != null -> {
                            result.body = ApiService.convert?.convert(content, claz!!) as T
                        }
                        else -> {
                            Log.e("request", "parseResponse : 无法解析")
                        }
                    }
                } else {
                    val type: ParameterizedType =
                        callBack.javaClass.genericSuperclass as ParameterizedType
                    val argument = type.actualTypeArguments[0]
                    result.body = ApiService.convert?.convert(content, argument) as T
                }

            } else {
                message = content
            }
        } catch (exception: Exception) {
            message = exception.message
            isSuccess = false
        }
        result.returnCode =
            if (isSuccess) ApiResponse.RETURNCODE_SUCCESS_FROM_NET else ApiResponse.RETURNCODE_FAILED
        result.statusCode = status.toString()
        result.returnMessage = message ?: "OK"

        if (cacheStrategy != NET_ONLY && result.isSuccess() && result.body != null && result.body is Serializable) {
            saveCache(result.body!!)
        }
        return result
    }

    private fun saveCache(body: T) {
        val key = if (TextUtils.isEmpty(cacheKey)) generateCacheKey() else cacheKey
        CacheManager.save(key, body)
    }

    private fun generateCacheKey(): String {
        return UriCreator.createUrlFromParams(url, params)
    }

    private fun getCall(): Call? {
        val builder = okhttp3.Request.Builder()
        addHeaders(builder)
        val request = generateRequest(builder) ?: return null
        return ApiService.httpClient.newCall(request)
    }

    private fun addHeaders(builder: okhttp3.Request.Builder) {
        headers.forEach {
            builder.addHeader(it.key, it.value)
        }
    }

    abstract fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request?
}

const val CACHE_ONLY = 1 // 本地
const val CACHE_FIRST = 2 // 先本地同时网络
const val NET_ONLY = 3 // 网络
const val NET_CACHE = 4  // 先网络再本地