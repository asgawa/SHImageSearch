package com.tistory.asgawa.shimagesearch

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Handler
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo

import kotlinx.android.synthetic.main.activity_main.*
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.tistory.asgawa.shimagesearch.util.SHLog
import com.tistory.asgawa.shimagesearch.view.RecyclerViewAdapter
import com.tistory.asgawa.shimagesearch.viewmodel.SearchViewModel


/**
 * # Business Logic
 * 1. Observe user input $editTextUserInput and search when it does not change for 1 second
 * 2. Search when it has triggered
 * 3. Search result will be shown in $recyclerViewImageResults
 * 4. If an error occurred or there are no result, show UI to user
 * 5. Progress UI is required for long time works
*/

class MainActivity : AppCompatActivity() {

    private val SEARCH_TRIGGER_TIMEOUT = 1000L

    private val log: SHLog = SHLog("MainActivity")
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()
    private val searchTimeoutHandler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        val searchTriggerRunnable = Runnable {
            //#BL2 Begin
            log.d("Search start")
            model.onSearchTriggered(editTextUserInput.text.toString())
            //#BL2 End
        }

        //#BL1 Begin
        editTextUserInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) { }
            override fun beforeTextChanged(text: CharSequence?, start: Int, before: Int, after: Int) { }
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, after: Int) {
                searchTimeoutHandler.removeCallbacks(searchTriggerRunnable)
                searchTimeoutHandler.postDelayed(searchTriggerRunnable, SEARCH_TRIGGER_TIMEOUT)
            }
        })
        editTextUserInput.setOnEditorActionListener { _, actionId, _ ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    hideInputMethod()
                    true
                }
                else -> false
            }
        }
        recyclerViewImageResults.setOnTouchListener { _, _ ->
            hideInputMethod()
            false
        }
        //#BL1 End

        linearLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerViewImageResults.layoutManager = linearLayoutManager
        recyclerViewImageResults.adapter = RecyclerViewAdapter(ArrayList())

        model.getImageUrls().observe(this, Observer<ArrayList<String>> {
            if (it!!.isEmpty()) {
                //#BL4 Begin no result
                Snackbar.make(mainLayout, "검색 결과 없음", Snackbar.LENGTH_LONG).show()
                //#BL4 End
            }
            //#BL3 Begin
            val adapter = recyclerViewImageResults.adapter as RecyclerViewAdapter
            adapter.update(it)
            //#BL3 End
        })

        editTextUserInput.requestFocus()
    }

    private fun hideInputMethod() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ViewModelProviders.of(this).get(SearchViewModel::class.java).onDestroy()
    }
}
