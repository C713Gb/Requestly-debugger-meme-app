package com.banerjeec713.requestlydebugger.data.network

import com.banerjeec713.requestlydebugger.App
import com.banerjeec713.requestlydebugger.util.Constants.BASE_URL
import io.requestly.rqinterceptor.api.RQCollector
import io.requestly.rqinterceptor.api.RQInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    fun create(): RetrofitApi {
        val appContext = App.instance
        val collector = RQCollector(context=appContext, sdkKey="giS5YK3STBjTqa9avxMB")
        val rqInterceptor = RQInterceptor.Builder(appContext).collector(collector).build()
        val client = OkHttpClient.Builder().addInterceptor(rqInterceptor).build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()

        return retrofit.create(RetrofitApi::class.java)
    }

}