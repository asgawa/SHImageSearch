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

class MainActivity : AppCompatActivity() {

    private val SEARCH_TRIGGER_TIMEOUT = 1000L
    private val log: SHLog = SHLog("MainActivity")
    private val searchTimeoutHandler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        val searchTriggerRunnable = Runnable {
            log.d("Search start")
            model.onSearchTriggered(editTextUserInput.text.toString())
        }

        editTextUserInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) { }
            override fun beforeTextChanged(text: CharSequence?, start: Int, before: Int, after: Int) { }
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, after: Int) {
                searchTimeoutHandler.removeCallbacks(searchTriggerRunnable)
                searchTimeoutHandler.postDelayed(searchTriggerRunnable, SEARCH_TRIGGER_TIMEOUT)
            }
        })

        //Hide keyboard Begin
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
        //Hide keyboard End

        recyclerViewImageResults.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val adapter = RecyclerViewAdapter(ArrayList())
        recyclerViewImageResults.adapter = adapter
        model.getImageUrls().observe(this, Observer<ArrayList<String>> {
            if (it == null) {
                adapter.update(ArrayList()) //pass empty array list to clear recyclerview
            } else {
                if (it.isEmpty()) Snackbar.make(mainLayout, resources.getText(R.string.strNoResult), Snackbar.LENGTH_LONG).show()
                adapter.update(it)
            }
        })

        editTextUserInput.requestFocus()
    }

    private fun hideInputMethod() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}
