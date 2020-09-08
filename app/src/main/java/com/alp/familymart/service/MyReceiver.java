package com.alp.familymart.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.alp.familymart.WebviewActivity;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);

        if(status.isEmpty()) {
            status="No Internet Connection";
            WebviewActivity.webview.setEnabled(false);
            WebviewActivity.webview.setClickable(false);


        }
        else
            if(status.equalsIgnoreCase("No internet is available")){
                WebviewActivity.webview.setEnabled(false);
                WebviewActivity.webview.setClickable(false);
            }
            else
            {
                WebviewActivity.webview.setEnabled(true);
                WebviewActivity.webview.setClickable(true);
            }
//        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}