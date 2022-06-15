package com.banerjeec713.requestlydebugger.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MemeModel (
    @field:Expose @field:SerializedName("title") var title: String,
    @field:Expose @field:SerializedName("url") var url: String,
    @field:Expose @field:SerializedName("image") var image: String,
    @field:Expose @field:SerializedName("upvotes") var upvotes: Int,
    @field:Expose @field:SerializedName("downvotes") var downvotes: Int,
    @field:Expose @field:SerializedName("comments") var comments: Int,
        ) : Serializable