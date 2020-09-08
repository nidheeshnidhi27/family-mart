package com.alp.familymart.ui;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alp.familymart.DashboardActivity;
import com.alp.familymart.LoginActivity;
import com.alp.familymart.R;
import com.alp.familymart.modelclass.AddCartModel;
import com.alp.familymart.modelclass.LoginModel;
import com.alp.familymart.modelclass.product_model.ProductModel;
import com.alp.familymart.modelclass.product_model.Image;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityProductDetails extends AppCompatActivity {

    RequestQueue queue;
    String url;
    private TextView tvProductName, tvRegularPrice, tvProductPrice;
    private ViewPager vpProductImage;
    private ProductModel product;
    private Button btnaddcart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        //inits
        tvProductName = findViewById(R.id.tv_product_name);
        tvRegularPrice = findViewById(R.id.tv_regular_price);
        tvProductPrice = findViewById(R.id.tv_product_price);
        vpProductImage = findViewById(R.id.vp_product_image);
        btnaddcart = findViewById(R.id.btn_addcart);
        //get product from Intent
        Intent getIntent = getIntent();
        if(getIntent.hasExtra(DashboardFragment.PRODUCT_OBJECT))
        product = (ProductModel) getIntent.getSerializableExtra(DashboardFragment.PRODUCT_OBJECT);
        if(product != null)
            setDatas(product);

        btnaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcart();
            }
        });
    }

    private void setDatas(ProductModel product){

        if(product.getName() != null){

            tvProductName.setText(product.getName());
            tvRegularPrice.setText("\u20B9"+product.getRegularPrice());
            tvProductPrice.setText("\u20B9"+product.getSalePrice());

            if(product.getImages() != null && product.getImages().size() >0){

                MyCustomPagerAdapter myCustomPagerAdapter;


                    myCustomPagerAdapter = new MyCustomPagerAdapter(ActivityProductDetails.this, product.getImages());
                    vpProductImage.setAdapter(myCustomPagerAdapter);



            }

        }
    }


    private void addcart() {

        queue = Volley.newRequestQueue(this);
        url = "https://familymart.online/wp-json/cocart/v1/add-item/?";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("product_id",product.getId());
           // jsonBody.put("password",pass.getText().toString());
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
                        Log.e("JSONresponse", "Got Response");
                         //Toast.makeText(ActivityProductDetails.this,response,Toast.LENGTH_LONG).show();
                        Toast.makeText(ActivityProductDetails.this,"product added",Toast.LENGTH_LONG).show();
                        Gson gson =new Gson();
                        AddCartModel parsedResponse = gson.fromJson(response.toString(), AddCartModel.class);


//                        if(parsedResponse.getStatus().equalsIgnoreCase("success")){
//
//                            SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                            SharedPreferences.Editor editor=saved_values.edit();
//                            editor.putString("UserId",""+suname);
//                            editor.commit();
//
//
//                            Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
//                            startActivity(i);
//                        }
//
//                        else {
//                            Toast.makeText(LoginActivity.this,parsedResponse.getMessage()+"",Toast.LENGTH_LONG).show();
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityProductDetails.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.e("JSONresponse", "ERROR in Response");
                    }
                })


        {
            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {


                Map<String,String> headers =  new HashMap<>();
                // add headers <key,value>
                //String credentials = "demo:Nidhi123";
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

                //String creds = String.format("%s:%s","demo","Nidhi123");

                String creds = String.format("%s:%s","ck_b7b9a62bc1f161d51603048008a239309b2ff1fe","cs_f7a23047d3314fba621c6894ae745bae0a23f0fb");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };

//        {
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("product_id",product.getId().toString());
//                //params.put("password",pass.getText().toString());
//                return params;
//            }
//
//        };
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




    public class MyCustomPagerAdapter extends PagerAdapter {
        Context context;
        List<Image> images;
        LayoutInflater layoutInflater;


        public MyCustomPagerAdapter(Context context, List<Image> images) {
            this.context = context;
            this.images = images;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = layoutInflater.inflate(R.layout.item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

            Picasso.get().load(images.get(position).getSrc()).into(imageView);

            container.addView(itemView);

            //listening to image click
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
                }
            });

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }




}
