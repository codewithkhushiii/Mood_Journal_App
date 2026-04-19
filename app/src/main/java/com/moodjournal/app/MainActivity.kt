package com.moodjournal.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)

        setupWebView()
        
        // 10.0.2.2 points to localhost of the host machine from the Android Emulator
        // If connecting from a real physical device on same wifi, replace this with your computer's local IP (e.g. 192.168.1.5)
        webView.loadUrl("http://10.0.2.2:8000")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val webSettings: WebSettings = webView.settings
        
        // Must allow JS for standard web apps to function
        webSettings.javaScriptEnabled = true
        
        // Enable local storage for web app variables if needed
        webSettings.domStorageEnabled = true
        
        // Improve performance and look
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.builtInZoomControls = false
        
        // Handle navigations within the webview instead of opening an external browser
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }
        
        // Support for alerting and video/rich content features if any
        webView.webChromeClient = WebChromeClient()
    }

    // Ensure the hardware back button navigates backwards in the web history 
    // instead of instantly closing the Android application if we navigated around.
    @Deprecated("Deprecated in Java", ReplaceWith(
        "if (webView.canGoBack()) webView.goBack() else super.onBackPressed()"
    ))
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
