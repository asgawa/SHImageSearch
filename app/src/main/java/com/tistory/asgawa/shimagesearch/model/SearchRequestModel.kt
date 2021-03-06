package com.tistory.asgawa.shimagesearch.model

import com.google.gson.annotations.SerializedName

class SearchRequestModel {
    @SerializedName("query")
    var query: String = ""  //mandatory

    @SerializedName("sort")
    var sort: String = "accuracy"    //sort by accuracy or recency

    @SerializedName("page")
    var page: Int = 1   //result page number, default: 1

    @SerializedName("size")
    var size: Int = 20   //count in one page, default: 80
}