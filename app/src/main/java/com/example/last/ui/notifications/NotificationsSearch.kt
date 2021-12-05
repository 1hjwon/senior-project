package com.example.last.ui.notifications

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

class NotificationsSearch: AppCompatActivity() {
    private lateinit var conn : ConnectivityManager
    private var binding: NotificationssearchBinding? = null
    //val webView = findViewById<WebView>(R.id.webView) //?? 바인딩으로 안됨..

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notificationssearch)

        binding = NotificationssearchBinding.inflate( layoutInflater)

        val root :View = binding!!.root
        val wv = searchWebView(this, binding!!)

        conn = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager//wv.getConnectManager()


        val builder = NetworkRequest.Builder()

        conn.registerNetworkCallback(builder.build(), networkCallbackMSG)


        //val webView = binding!!.webView.findViewById<WebView>(R.id.webView) //바인딩으로 안됨
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

        webView.loadUrl("https://www.google.com/")



        Log.i("웹뷰!!!", webView.url.toString()) //여기 됨
        //networkInfo(wv)


        //searchWebView().isNetworkConnected()


        Log.i("second", "2")

        //return root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        conn = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        conn.unregisterNetworkCallback(networkCallbackMSG)

    }

    val networkCallbackMSG = object : ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
            //Toast.makeText(this@NotificationsSearch, "연결성공", Toast.LENGTH_LONG).show()
            Log.i("연결성공", network.toString())
        }

        override fun onLost(network: Network) {
            Toast.makeText(this@NotificationsSearch, "연결종료", Toast.LENGTH_LONG).show()
            Log.i("연결종료", network.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBackPressed() {
        //?? 바인딩으로 안됨..
        val webView = requireViewById<WebView>(R.id.webView)

        if (webView.canGoBack()){
            webView.goBack()
        } else {
            super.onBackPressed()
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    fun networkInfo(wv: searchWebView){ //안됐음
        if(wv.getConnectManager().isDefaultNetworkActive){
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                // 성공
                //wv.initWebView()

                val webView = binding!!.webView

                webView.settings.javaScriptEnabled = true
                webView.webViewClient = WebViewClient()
                webView.webChromeClient = WebChromeClient()



                webView.loadUrl("http://www.google.com/")


                Log.i("웹뷰", webView.url.toString()) //여기 됨

                //Toast.makeText(this@NotificationsSearch, "연결됨", Toast.LENGTH_SHORT).show()

                Log.i("연결됨", handler.toString())

            }, 1250)

        }
    }
}

/*
        if(wv.isNetworkConnected() != null){
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                // 실패

                Toast.makeText(this@NotificationsSearch, "연결없음", Toast.LENGTH_SHORT).show()

                Log.i("연결없음", handler.toString())

            }, 1250)
        } else {
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                // 성공
                wv.initWebView()


                Toast.makeText(this@NotificationsSearch, "연결됨", Toast.LENGTH_SHORT).show()

                Log.i("연결됨", handler.toString())

            }, 1000)

        }
 */