package com.tistory.asgawa.shimagesearch.model

import com.google.gson.annotations.SerializedName

class SearchResponseModel {
    @SerializedName("meta")
    val meta: SearchResponseMetaModel = SearchResponseMetaModel()

    @SerializedName("documents")
    val documents  = ArrayList<SearchResponseDocumentModel>()
}