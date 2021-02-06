package com.ylozhangaf.libnetwork

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.NullPointerException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

class ApiService {

    companion object {
        private val httpInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val httpClient = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(httpInterceptor)
            .build()

        // 证书
        val trustManager = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {

            }

            override fun getAcceptedIssuers(): Array<X509Certificate?> {
                return arrayOfNulls(0)
            }

        }
        val sslContext = SSLContext.getInstance("SSL").apply {
            init(null, arrayOf(trustManager), SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(socketFactory)
            HttpsURLConnection.setDefaultHostnameVerifier { _, _ ->
                true
            }
        }


        var baseUrl : String = ""
        var convert : Convert<Any>? = null
        fun init(baseUrl : String, convert : Convert<Any>?) {
            this.baseUrl = baseUrl
            if (convert == null) {
                this.convert = JsonConvert()
            }
        }
    }

}