package com.banerjeec713.requestlydebugger.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResponseModel (
    @field:Expose @field:SerializedName("success") var success: Boolean,
    @field:Expose @field:SerializedName("data") var data: MemeModel
        ): Serializable