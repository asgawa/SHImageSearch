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
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo

import kotlinx.android.synthetic.main.activity_main.*
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
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

        val searchRunnable = Runnable {
            log.d("Search start")
            model.searchTriggered(editTextUserInput.text.toString())
        }

        val searchMoreRunnable = Runnable {
            if (model.hasNoMoreResult) {
                Snackbar.make(mainLayout, resources.getText(R.string.strNoMoreResult), Snackbar.LENGTH_LONG).show()
            } else {
                log.d("Search more start")
                model.searchMoreTriggered()
            }
        }

        editTextUserInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) { }
            override fun beforeTextChanged(text: CharSequence?, start: Int, before: Int, after: Int) { }
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, after: Int) {
                searchTimeoutHandler.removeCallbacks(searchRunnable)
                searchTimeoutHandler.postDelayed(searchRunnable, SEARCH_TRIGGER_TIMEOUT)
            }
        })

        //Hide keyboard Begin
//        editTextUserInput.setOnEditorActionListener { _, actionId, _ ->
//            when(actionId) {
//                EditorInfo.IME_ACTION_SEARCH -> {
//                    hideInputMethod()
//                    true
//                }
//                else -> false
//            }
//        }
        recyclerViewImageResults.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                hideInputMethod()
            }
            false
        }
        //Hide keyboard End

        recyclerViewImageResults.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val adapter = RecyclerViewAdapter(ArrayList())
        recyclerViewImageResults.adapter = adapter
        model.getImageUrls().observe(this, Observer {
            if (it == null) {
                adapter.update(ArrayList()) //pass empty array list to clear recyclerview
            } else {
                if (it.isEmpty()) {
                    Snackbar.make(mainLayout, resources.getText(R.string.strNoResult), Snackbar.LENGTH_LONG).show()
                }
                if (model.isLoadMore) {
                    adapter.append(it)
                } else {
                    adapter.update(it)
                }
            }
        })
        model.getLoadingStatus().observe(this, Observer {
            if (it != null) {
                log.d("Loading state changed to $it")
                if (it) {
                    progressBarBackground.visibility = View.VISIBLE //show progress dialog alike modal dialog
                    window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                } else {
                    progressBarBackground.visibility = View.GONE
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
            }
        })

        swipyRefreshLayout.setOnRefreshListener {
            if (!model.getImageUrls().value.isNullOrEmpty()) {
                searchTimeoutHandler.removeCallbacks(searchRunnable)
                searchTimeoutHandler.post(searchMoreRunnable)
            }

            swipyRefreshLayout.isRefreshing = false
        }

        editTextUserInput.requestFocus()
    }

    private fun hideInputMethod() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}
