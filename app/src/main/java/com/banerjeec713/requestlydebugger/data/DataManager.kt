package com.banerjeec713.requestlydebugger.data

import android.content.Context
import com.banerjeec713.requestlydebugger.data.network.RetrofitManager.Companion.instance
import com.banerjeec713.requestlydebugger.data.models.ResponseModel
import com.banerjeec713.requestlydebugger.data.network.RetrofitManager
import io.reactivex.Observable

class DataManager private constructor(context: Context) {
    private val retrofitManager: RetrofitManager = instance

    fun getMeme(): Observable<ResponseModel>{
        return retrofitManager.getMeme()
    }

    companion object {
        private var mInstance: DataManager? = null
        @Synchronized
        fun getInstance(context: Context): DataManager {
            return if (mInstance == null) DataManager(context).also {
                mInstance = it
            } else mInstance!!
        }
    }

}