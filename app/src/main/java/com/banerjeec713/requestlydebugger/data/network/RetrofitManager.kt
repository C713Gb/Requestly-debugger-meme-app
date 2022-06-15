package com.banerjeec713.requestlydebugger.data.network

import com.banerjeec713.requestlydebugger.data.network.RetrofitClient.create
import com.banerjeec713.requestlydebugger.data.models.ResponseModel
import io.reactivex.Observable

class RetrofitManager private constructor() {
    fun getMeme(): Observable<ResponseModel>{
        val api = create()
        return api.getMeme()
    }

    companion object {
        private var mInstance: RetrofitManager? = null
        val instance: RetrofitManager
            get() = if (mInstance == null) RetrofitManager().also {
                mInstance = it
            } else mInstance!!
    }
}