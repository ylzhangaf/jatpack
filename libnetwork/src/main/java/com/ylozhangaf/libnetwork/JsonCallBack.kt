package com.ylozhangaf.libnetwork

abstract class JsonCallBack<T> {
    fun onSuccess(response : ApiResponse<T>) {

    }

    fun onError(response: ApiResponse<T>) {

    }

    fun onCacheSuccess(response: ApiResponse<T>) {

    }
}