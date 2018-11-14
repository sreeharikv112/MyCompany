package com.lovoo.network

import com.lovoo.ui.homelist.model.OfficeRoom
import io.reactivex.Single
import retrofit2.http.GET

interface NetworkService  {

    @GET("lovooOffice/")
    fun getOfficeData(): Single<ArrayList<OfficeRoom>>

}