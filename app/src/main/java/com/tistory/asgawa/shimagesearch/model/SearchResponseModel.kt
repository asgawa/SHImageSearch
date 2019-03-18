package com.tistory.asgawa.shimagesearch.model

import com.google.gson.annotations.SerializedName

class SearchResponseModel {
    @SerializedName("meta")
    var meta: SearchResponseMetaModel = SearchResponseMetaModel()

    @SerializedName("documents")
    var documents  = ArrayList<SearchResponseDocumentModel>()
}