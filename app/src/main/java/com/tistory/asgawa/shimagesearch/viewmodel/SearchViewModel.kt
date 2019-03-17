package com.tistory.asgawa.shimagesearch.viewmodel

import android.arch.lifecycle.LiveData
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
    private val imageUrls = MutableLiveData<ArrayList<String>>()

    fun getImageUrls(): LiveData<ArrayList<String>> {
        return imageUrls
    }

    fun onSearchTriggered(text: String) {
        keyword = text
        if (keyword.isNotBlank()) {
            search()
        } else {
            imageUrls.value?.clear()
            imageUrls.postValue(ArrayList())
        }
    }

    private fun search() {
        val requestModel = SearchRequestModel()
        requestModel.query = keyword
        compositeDisposable.add(SearchApi.searchImage(requestModel)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({ response: SearchResponseModel ->
                if (response.meta.total_count > 0) {
                    val imageUrls = ArrayList<String>()
                    for (item in response.documents) {
                        imageUrls.add(item.image_url)
                    }
                    this.imageUrls.value = imageUrls
                } else {
                    this.imageUrls.value?.clear()
                    imageUrls.postValue(ArrayList())
                }
            }, { error: Throwable ->
                print("error occurred ${error.localizedMessage}")
            })
        )
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}