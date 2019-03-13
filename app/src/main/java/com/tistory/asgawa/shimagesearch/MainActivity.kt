package com.tistory.asgawa.shimagesearch

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Handler
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo

import kotlinx.android.synthetic.main.activity_main.*;
import android.view.inputmethod.InputMethodManager


/**
 * # Business Logic
 * 1. Observe user input $editTextUserInput and search when it does not change for 1 second
 * 2. Search when it has triggered
 * 3. Search result will be shown in $recyclerViewImageResults
 * 4. If an error occurred or there are no result, show UI to user
 * 5. Progress UI is required for long time works
*/

class MainActivity : AppCompatActivity() {

    private val SEARCH_TRIGGER_TIMEOUT = 1000L;
    private var log: SHLog = SHLog("MainActivity")
    private var isSearch: Boolean = false
    private var searchTimeTrigger: Handler = Handler()
    private val searchTrigger: Runnable = object : Runnable {
        override fun run() {
            isSearch = true
            log.d("1 sec passed")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //#BL1
        editTextUserInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) { }
            override fun beforeTextChanged(text: CharSequence?, start: Int, before: Int, after: Int) { }
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, after: Int) {
                searchTimeTrigger.removeCallbacks(searchTrigger)
                searchTimeTrigger.postDelayed(searchTrigger, SEARCH_TRIGGER_TIMEOUT)
            }
        })
        editTextUserInput.setOnEditorActionListener { v, actionId, event ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    hideInputMethod()
                    true
                }
                else -> false
            }
        }
        recyclerViewImageResults.setOnTouchListener { v, event ->
            hideInputMethod()
            false
        }

        //#BL2

        editTextUserInput.requestFocus()
    }

    private fun hideInputMethod() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}
