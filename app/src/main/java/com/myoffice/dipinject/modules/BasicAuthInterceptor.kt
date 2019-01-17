package com.myoffice.dipinject.modules

import com.myoffice.utils.AppConstants
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Auth interceptor for networking
 */
class BasicAuthInterceptor(_username:String , _password:String ) : Interceptor {

    val username:String
    val password:String
    val credentials : String
    init{
        username = _username
        password = _password
        credentials = Credentials.basic(username,password)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header(AppConstants.AUTHORIZATION, credentials).build()
        return chain.proceed(authenticatedRequest)
    }
}