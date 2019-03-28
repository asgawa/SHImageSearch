package com.tistory.asgawa.shimagesearch.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tistory.asgawa.shimagesearch.util.SearchApi
import com.tistory.asgawa.shimagesearch.model.SearchRequestModel
import com.tistory.asgawa.shimagesearch.model.SearchResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

//important: do not save UI context to avoid memory leak
class SearchViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private lateinit var keyword: String
    private val imageUrls = MutableLiveData<ArrayList<String>>()
    private var isLoading = MutableLiveData<Boolean>()
    var currentSearchPage = 1
    var hasNoMoreResult = true
    var isLoadMore = false

    fun getImageUrls(): LiveData<ArrayList<String>> {
        return imageUrls
    }

    fun getLoadingStatus(): LiveData<Boolean> {
        return isLoading
    }

    fun searchTriggered(text: String) {
        keyword = text
        if (keyword.isNotBlank()) {
            search()
        } else {    //keyword is empty, no need to notify to user 'there is no search result'
            imageUrls.value?.clear()
            imageUrls.postValue(null)
            hasNoMoreResult = true
        }
    }

    fun searchMoreTriggered() {
        searchMore()
    }

    private fun search() {
        isLoading.value = true

        isLoadMore = false

        val requestModel = SearchRequestModel()
        currentSearchPage = requestModel.page
        requestModel.query = keyword
        compositeDisposable.add(
            SearchApi.searchImage(requestModel)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({ response: SearchResponseModel ->
                hasNoMoreResult = response.meta.is_end
                if (response.meta.total_count > 0) {
                    val imageUrls = ArrayList<String>()
                    for (item in response.documents) {
                        imageUrls.add(item.image_url)
                    }
                    this.imageUrls.value = imageUrls
                } else {    //no search result
                    this.imageUrls.value?.clear()
                    imageUrls.postValue(ArrayList())
                }
                isLoading.value = false
            }, { error: Throwable ->
                print("error occurred ${error.localizedMessage}")
                isLoading.value = false
            })
        )
    }

    private fun searchMore() {
        isLoading.value = true

        isLoadMore = true

        val requestModel = SearchRequestModel()
        ++currentSearchPage
        requestModel.page = currentSearchPage
        requestModel.query = keyword

        compositeDisposable.add(
            SearchApi.searchImage(requestModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ response: SearchResponseModel ->
                    hasNoMoreResult = response.meta.is_end
                    val imageUrls = ArrayList<String>()
                    for (item in response.documents) {
                        imageUrls.add(item.image_url)
                    }
                    this.imageUrls.value?.addAll(imageUrls)
                    isLoading.value = false
                }, { error: Throwable ->
                    print("error occurred ${error.localizedMessage}")
                    isLoading.value = false
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}