package com.alp.familymart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alp.familymart.service.MyReceiver;

public class WebviewActivity extends AppCompatActivity {

    private BroadcastReceiver MyReceiver = null;

    public static WebView webview;
//    ImageView nocon;
    SwipeRefreshLayout refreshLayout;
    LinearLayout netError;
    Button retry;
    String url;
    Boolean network;
    ProgressBar pbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webview = findViewById(R.id.webview_loader);
        refreshLayout = findViewById(R.id.refresh);
        netError = findViewById(R.id.ll_net_error);
        retry = findViewById(R.id.btn_retry);
        pbar = findViewById(R.id.pbar);

        refreshLayout.setRefreshing(true);

        webview.setWebViewClient(new SSLTolerentWebViewClient());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);


            url = "https://familymart.online/";

//            if (isNetworkConnected()) {
//
//                webview.loadUrl(url);
//            } else {
//                if (nocon != null)
//                    nocon.setVisibility(View.VISIBLE);
//                webview.setVisibility(View.GONE);
//            }

        webload();

        MyReceiver = new MyReceiver();
        broadcastIntent();

        refreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                webview.reload();
                webload();
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pbar.setVisibility(View.VISIBLE);
//                webview.reload();
                webview.setActivated(true);
                webload();


            }
        });


        //WEBVIEW CLIENT
        webview.setWebViewClient(new Callback());
    }

    public void webload()
    {
        refreshLayout.setRefreshing(false);
        if (isNetworkConnected()) {
            webview.setVisibility(View.VISIBLE);
            webview.loadUrl(url);
        } else {
//            if (nocon != null)
//                nocon.setVisibility(View.VISIBLE);
            webview.setVisibility(View.GONE);
            netError.setVisibility(View.VISIBLE);
        }
    }


    //CLASS CALLBACK

    public class Callback extends WebViewClient{
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

            int errorCode = error.getErrorCode();
            Log.e("","");
            if(errorCode == Callback.ERROR_HOST_LOOKUP){
                webview.destroy();
                netError.setVisibility(View.VISIBLE);
            }
            else{
                netError.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            netError.setVisibility(View.GONE);
            pbar.setVisibility(View.GONE);
        }
    }

    //END---------------
    public void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }
    public void onBackPressed()
    {
        if (webview.canGoBack()) {
            webview.goBack();
        }
        else{
            super.onBackPressed();
        }
    }

    private class SSLTolerentWebViewClient extends WebViewClient {

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // Ignore SSL certificate errors
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            refreshLayout.setRefreshing(true);

        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            refreshLayout.setRefreshing(false);

        }
    }



    public static class NetworkUtil {
        public static final int TYPE_WIFI = 1;
        public static final int TYPE_MOBILE = 2;
        public static final int TYPE_NOT_CONNECTED = 0;
        public static final int NETWORK_STATUS_NOT_CONNECTED = 0;
        public static final int NETWORK_STATUS_WIFI = 1;
        public static final int NETWORK_STATUS_MOBILE = 2;

        public static int getConnectivityStatus(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) {
                if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI;

                if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE;
            }
            return TYPE_NOT_CONNECTED;
        }

        public static int getConnectivityStatusString(Context context) {
            int conn = NetworkUtil.getConnectivityStatus(context);
            int status = 0;
            if (conn == NetworkUtil.TYPE_WIFI) {
                status = NETWORK_STATUS_WIFI;
            } else if (conn == NetworkUtil.TYPE_MOBILE) {
                status = NETWORK_STATUS_MOBILE;
            } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
                status = NETWORK_STATUS_NOT_CONNECTED;
            }
            return status;
        }
    }


    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            int status = NetworkUtil.getConnectivityStatusString(context);
           // Log.e("Sulod sa network reciever", "Sulod sa network reciever");
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {


                    //new ForceExitPause(context).execute();
                } else {


                    // new ResumeForceExitPause(context).execute();
                }
            }
        }
    }





}
