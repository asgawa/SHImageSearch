package com.tistory.asgawa.shimagesearch.model

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

class SearchApi {
    interface SearchApiImpl {
//        @Headers("Authorization: KakaoAK 33deaa7a6c79fdd190b694d4c1a0293b")
        @Headers("Authorization: KakaoAK 56246375a4bd58d9c2e6052811be824c")
        @GET("v2/search/image")
        fun searchImage(@Query("query") query: String,
                        @Query("sort") sort: String,
                        @Query("page") page: Int,
                        @Query("size") size: Int) : Observable<SearchResponseModel>
    }

    companion object {
        private fun searchImage(query: String, sort: String, page: Int, size: Int): Observable<SearchResponseModel> {
            return SearchEngine.create(SearchApiImpl::class.java)
                .searchImage(query, sort, page, size)
        }

        fun searchImage(requestModel: SearchRequestModel) : Observable<SearchResponseModel> {
            return searchImage(requestModel.query, requestModel.sort, requestModel.page, requestModel.size)
        }
    }
}