package com.myoffice.network

import com.myoffice.ui.homelist.model.OfficeRoom
import io.reactivex.Single
import retrofit2.http.GET

interface NetworkService  {

    @GET("myOffice/")
    fun getOfficeData(): Single<ArrayList<OfficeRoom>>

}