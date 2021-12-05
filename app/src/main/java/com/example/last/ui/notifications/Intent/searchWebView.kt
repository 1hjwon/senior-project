package com.example.last.ui.notifications.Intent

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.last.R
import com.example.last.databinding.NotificationssearchBinding

class searchWebView(context: Context, binding: NotificationssearchBinding) {
    private val context = context
    private val binding = binding

    fun initWebView(){
        val webView = binding.webView//.findViewById<WebView>(R.id.webView)

        //val wv = binding!!.webView

        webView.apply {
            webView.webViewClient = WebViewClient()


        }

        webView.loadUrl("https://www.google.com/")
        Log.i("웹뷰", webView.url.toString()) //여기 됨
        Toast.makeText(context, "웹뷰", Toast.LENGTH_SHORT).show()

    }

    fun isNetworkConnected():Boolean{
        val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val active = conn.activeNetworkInfo
        //val isConnected =
        return active?.isConnectedOrConnecting == true

    }

    fun getConnectManager():ConnectivityManager{
        val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return conn
    }
}