package com.alp.familymart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alp.familymart.modelclass.LoginModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";


    RequestQueue queue;
    String url;
    EditText fName, lName,email,uname, pass;
    String sfName, slName,seMail,suname, sPass;
    TextView loginText;
    Button reg;

    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register1);



        fName = findViewById(R.id.fname);
        lName = findViewById(R.id.lname);

        email = findViewById(R.id.email);
        uname = findViewById(R.id.uname);
        pass = findViewById(R.id.pass);

        reg = findViewById(R.id.button);
        loginText = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.loading);





        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                    sfName = fName.getText().toString().trim();
                    slName = lName.getText().toString().trim();
                    seMail = email.getText().toString().trim();
                    suname = uname.getText().toString().trim();
                    sPass = pass.getText().toString().trim();



                    if (!Patterns.EMAIL_ADDRESS.matcher(seMail).matches()) {
                        email.setError("invalid email");
                        return;
                    }




                    if (TextUtils.isEmpty(seMail)) {
                        email.setError("Email is Required");
                        return;
                    }





                    if (TextUtils.isEmpty(sPass)) {
                        pass.setError("Password is Required");
                        return;
                    }

                    if (sPass.length() < 6) {
                        pass.setError("Password must be minimum 6 characters long");
                        return;
                    }

                    else{
                        regdata();
                    }


                    progressBar.setVisibility(View.VISIBLE);

//                    fAuth.createUserWithEmailAndPassword(eMail, sPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//
//                            if (task.isSuccessful()) {
//                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
//                                first.setVisibility(View.GONE);
//                                second.setVisibility(View.VISIBLE);
//                                loginText.setVisibility(View.GONE);
//                                reg.setText("Continue");
//                                progressBar.setVisibility(View.GONE);
//                            }
//                            else {
//                                progressBar.setVisibility(View.GONE);
//                                Toast.makeText(RegisterActivity.this, "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });


//                else {
//
//                    fulName = fullName.getText().toString();
//                    sAddr = addr.getText().toString();
//                    sCity = city.getText().toString();
//                    sPincode = pincode.getText().toString();
//                    sPhno = phno.getText().toString();
//
//                    if (TextUtils.isEmpty(fulName)) {
//                        fullName.setError("Name is Required");
//                        return;
//                    }
//
//                    if (TextUtils.isEmpty(sAddr)) {
//                        addr.setError("Address is Required");
//                        return;
//                    }
//
//                    if (TextUtils.isEmpty(sCity)) {
//                        city.setError("City is Required");
//                        return;
//                    }
//
//                    if (TextUtils.isEmpty(sPincode)) {
//                        pincode.setError("Pincode is Required");
//                        return;
//                    }
//
//                    if (sPincode.length() != 6) {
//                        pincode.setError("Invalid Pincode!");
//                        return;
//                    }
//
//                    if (TextUtils.isEmpty(sPhno)) {
//                        phno.setError("Mobile Number is Required");
//                        return;
//                    }
//
//                    if (sPhno.length() != 10) {
//                        phno.setError("Invalid Mobile Number!");
//                        return;
//                    }
//
//                    else {
//
//                        progressBar.setVisibility(View.VISIBLE);
//
//                        // register the user in firebase
//
//                        userID = fAuth.getCurrentUser().getUid();
//                        DocumentReference documentReference = fStore.collection("users").document(userID);
//                        Map<String, Object> user = new HashMap<>();
//                        user.put("FullName", fulName);
//                        user.put("email", eMail);
//                        user.put("address", sAddr);
//                        user.put("city", sCity);
//                        user.put("pincode", sPincode);
//                        user.put("phone", sPhno);
//
//                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.d(TAG, "onSuccess: user Profile is created for " + userID);
//                                Toast.makeText(RegisterActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
//
//                                startActivity(new Intent(getApplicationContext(), DashboardActivity1.class));
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                progressBar.setVisibility(View.GONE);
//                                Log.d(TAG, "onFailure: " + e.toString());
//                                Toast.makeText(RegisterActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                    }
//                }

            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });

    }

    private void regdata() {

        queue = Volley.newRequestQueue(this);
        url = "http://familymart.online/api/reg.php";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("first_name",sfName);
            jsonBody.put("last_name",slName);
            jsonBody.put("email",seMail);
            jsonBody.put("username",suname);
            jsonBody.put("password",sPass);
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


                            Intent i = new Intent(RegisterActivity.this,DashboardActivity.class);
                            startActivity(i);
                        }

                        else {
                            Toast.makeText(RegisterActivity.this,parsedResponse.getMessage()+"",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("first_name",sfName);
                params.put("last_name",slName);
                params.put("email",seMail);
                params.put("username",suname);
                params.put("password",sPass);

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
