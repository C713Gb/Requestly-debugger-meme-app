package com.banerjeec713.requestlydebugger.data.network

import com.banerjeec713.requestlydebugger.data.models.ResponseModel
import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitApi {

    @GET("/api/memes")
    fun getMeme(): Observable<ResponseModel>

}