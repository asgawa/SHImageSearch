package com.tistory.asgawa.shimagesearch.model

import com.google.gson.annotations.SerializedName

class SearchResponseDocumentModel {
    @SerializedName("collection")
    val collection: String = ""

    @SerializedName("thumbnail_url")
    val thumbnail_url: String = ""

    @SerializedName("image_url")
    val image_url: String = ""

    @SerializedName("width")
    val width = 0

    @SerializedName("height")
    val height = 0

    @SerializedName("display_sitename")
    val display_sitename: String = ""

    @SerializedName("doc_url")
    val doc_url: String = ""

    @SerializedName("datetime")
    val datetime: String = ""   //ISO 8601. [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
}