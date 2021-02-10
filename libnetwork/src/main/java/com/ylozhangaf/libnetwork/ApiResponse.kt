package com.ylozhangaf.libnetwork

class ApiResponse<T> {
    var returnCode : String = ""
    var returnMessage : String = ""
    var statusCode : String = ""
    var body : T? = null

    fun isSuccess() : Boolean {
        return returnCode == "100"
    }

    companion object{
        const val RETURNCODE_SUCCESS_FROM_NET = "100"
        const val RETURNCODE_SUCCESS_FROM_CACHE = "304"
        const val RETURNCODE_FAILED = "0"
    }
}