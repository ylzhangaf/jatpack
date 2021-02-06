package com.ylozhangaf.libnetwork

class ApiResponse<T> {
    var returnCode : String = ""
    var returnMessage : String = ""
    var statusCode : String = ""
    var body : T? = null

    fun isSuccess() : Boolean {
        return returnCode == "100"
    }
}