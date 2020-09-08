package com.alp.familymart.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alp.familymart.LoginActivity;
import com.alp.familymart.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    RequestQueue queue;
    String url;
    Button logout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        logout = root.findViewById(R.id.btn_logout);





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logapi();



            }
        });

        return root;
    }

    public void logapi() {
        queue = Volley.newRequestQueue(getActivity());
        url = "https://familymart.online/wp-json/cocart/v1/logout";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = saved_values.edit();
                editor.remove("UserId");
                editor.commit();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("","");

            }
        } ){
            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {


                Map<String,String> headers =  new HashMap<>();
                // add headers <key,value>
                String credentials = "ck_b7b9a62bc1f161d51603048008a239309b2ff1fe:cs_f7a23047d3314fba621c6894ae745bae0a23f0fb";
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }

            //Pass Your Parameters here
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s","ck_b7b9a62bc1f161d51603048008a239309b2ff1fe","cs_f7a23047d3314fba621c6894ae745bae0a23f0fb");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }

        });

        queue.add(request);

    }


}