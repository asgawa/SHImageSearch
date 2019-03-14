package com.tistory.asgawa.shimagesearch

import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Handler
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

    private var log: SHLog = SHLog("MainActivity")
    private val viewModel: SearchViewModel = SearchViewModel()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()
    private val searchTimeoutHandler: Handler = Handler()
    private val searchTriggerRunnable = Runnable {
        //#BL2 Begin
        viewModel.onSearchTriggered(editTextUserInput.text.toString())
        //#BL2 End
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        viewModel.imageUrls.observe(this, Observer<ArrayList<String>> {
            //#BL3 Begin
            val adapter = recyclerViewImageResults.adapter as RecyclerViewAdapter
            adapter.update(it!!)
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
        viewModel.onDestroy()
    }
}
