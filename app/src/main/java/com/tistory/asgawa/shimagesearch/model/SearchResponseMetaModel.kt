package com.tistory.asgawa.shimagesearch.model

import com.google.gson.annotations.SerializedName

class SearchResponseMetaModel {
    @SerializedName("total_count")
    var total_count: Int = 0    //found count

    @SerializedName("pageable_count")
    var pageable_count: Int = 0 //count of total_count which can be shown

    @SerializedName("is_end")
    var is_end: Boolean = true  //this is the last page or not
}