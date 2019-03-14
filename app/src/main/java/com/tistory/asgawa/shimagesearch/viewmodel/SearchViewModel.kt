package com.tistory.asgawa.shimagesearch.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tistory.asgawa.shimagesearch.model.SearchApi
import com.tistory.asgawa.shimagesearch.model.SearchRequestModel
import com.tistory.asgawa.shimagesearch.model.SearchResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var keyword: String
    var imageUrls = MutableLiveData<ArrayList<String>>()

    fun onSearchTriggered(text: String) {
        keyword = text
        if (keyword.isNotBlank()) {
            search()
        } else {
            //Notify it need to be cleared
        }
    }

    private fun search() {
        val requestModel = SearchRequestModel()
        requestModel.query = keyword
        compositeDisposable.add(SearchApi.searchImage(requestModel)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({ response: SearchResponseModel ->
                val urls = ArrayList<String>()
                for (item in response.documents) {
                    urls.add(item.image_url)
                }
                imageUrls.value = urls
            }, { error: Throwable ->
                print("error occurred ${error.localizedMessage}")
            })
        )
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}