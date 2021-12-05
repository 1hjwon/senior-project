package com.example.last.ui.notifications

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.last.R
import com.example.last.databinding.NotificationssearchBinding
import com.example.last.ui.notifications.Intent.searchWebView

class NotificationSearchNaver: AppCompatActivity() {
    private lateinit var conn : ConnectivityManager
    private var binding: NotificationssearchBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notificationssearch)

        binding = NotificationssearchBinding.inflate( layoutInflater)
        val root : View = binding!!.root
        val wv = searchWebView(this, binding!!)

        conn = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager//wv.getConnectManager()
        val builder = NetworkRequest.Builder()
        conn.registerNetworkCallback(builder.build(), networkCallbackMSG)

        val webView = findViewById<WebView>(R.id.webView) //?? 바인딩으로 안됨..

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()


        webView.apply {
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.useWideViewPort = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.domStorageEnabled = true
        }
        webView.loadUrl("https://www.naver.com/")
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
        conn = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        conn.unregisterNetworkCallback(networkCallbackMSG)

    }

    val networkCallbackMSG = object : ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
            Log.i("연결성공", network.toString())
        }

        override fun onLost(network: Network) {
            Toast.makeText(this@NotificationSearchNaver, "연결종료", Toast.LENGTH_LONG).show()
            Log.i("연결종료", network.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBackPressed() {
        val webView = requireViewById<WebView>(R.id.webView)
        if (webView.canGoBack()){
            webView.goBack()
        } else {
            super.onBackPressed()
        }

    }
}