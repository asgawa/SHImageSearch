package com.tistory.asgawa.shimagesearch.model

import com.google.gson.annotations.SerializedName

class SearchResponseDocumentModel {
    @SerializedName("collection")
    var collection: String = ""

    @SerializedName("thumbnail_url")
    var thumbnail_url: String = ""

    @SerializedName("image_url")
    var image_url: String = ""

    @SerializedName("width")
    var width = 0

    @SerializedName("height")
    var height = 0

    @SerializedName("display_sitename")
    var display_sitename: String = ""

    @SerializedName("doc_url")
    var doc_url: String = ""

    @SerializedName("datetime")
    var datetime: String = ""   //ISO 8601. [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
}