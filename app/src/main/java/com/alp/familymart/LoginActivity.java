package com.alp.familymart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alp.familymart.modelclass.BaseModel;
import com.alp.familymart.modelclass.LoginModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    RequestQueue queue;
    String url, suname;
    EditText uname, pass;
    TextView regBtn;
    Button loginbtn;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        uname= findViewById(R.id.uname);
        pass = findViewById(R.id.login_pass);
        loginbtn = findViewById(R.id.login_btn);
        regBtn = findViewById(R.id.regBtn);
        progressBar = findViewById(R.id.pBar);

        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String UserName = saved_values.getString("UserId", null);

        if(UserName != null){
            Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        }


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                suname = uname.getText().toString().trim();
                String password = pass.getText().toString().trim();
                if(TextUtils.isEmpty(suname) | password.length() < 6 | TextUtils.isEmpty(password)){
                if (TextUtils.isEmpty(suname)) {
                    uname.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    pass.setError("Password is Required");
                    return;
                }

                if (password.length() < 6) {
                    pass.setError("Password must contain minimum 6 characters");
                    return;
                }
            }

                else{
                    logdata();
                }

                progressBar.setVisibility(View.VISIBLE);



            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });


    }

    private void logdata() {

       queue = Volley.newRequestQueue(this);
       url = "http://familymart.online/api/login.php?";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username",uname.getText().toString());
            jsonBody.put("password",pass.getText().toString());
        }
        catch(Exception ex){

        }

  /*  JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,jsonBody, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {



            try {
               // JSONArray example = response;
                Gson gson = new Gson();


                //JSONObject finalObj = new JSONObject();
                //finalObj.put("example", example);

               LoginModel parsedResponse = gson.fromJson(response.toString(), LoginModel.class);


                if(parsedResponse.getStatus().equalsIgnoreCase("success")){


                    Intent i = new Intent(LoginActivity.this,DashboardActivity.class);
                    startActivity(i);
                }

                else {
                   // Toast.makeText(LoginActivity.this,parsedResponse.getMessage()+"",Toast.LENGTH_LONG).show();
                }

               Log.e("JSONresponse", "Got Response");
            } catch (Exception ex) {
                Log.e("error", " " + ex);
            }



        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("JSONresponse", "ERROR in Response");
    }
    });

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
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
*/


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        Gson gson =new Gson();
                        LoginModel parsedResponse = gson.fromJson(response.toString(), LoginModel.class);


                        if(parsedResponse.getStatus().equalsIgnoreCase("success")){

                            SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor=saved_values.edit();
                            editor.putString("UserId",""+suname);
                            editor.commit();


                            Intent i = new Intent(LoginActivity.this,DashboardActivity.class);
                            startActivity(i);
                        }

                        else {
                             Toast.makeText(LoginActivity.this,parsedResponse.getMessage()+"",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",uname.getText().toString());
                params.put("password",pass.getText().toString());
                return params;
            }

        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
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


        queue.add(stringRequest);
    }



    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}