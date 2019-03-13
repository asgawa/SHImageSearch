package com.tistory.asgawa.shimagesearch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

/**
 * Observe user input $editTextUserInput and search when it does not change for 1 second
 * Search result will be shown in $imageViewSearchResult
 * If an error occurred or there are no result, show UI to user
 * Progress UI is required for long time works
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
