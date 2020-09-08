package com.alp.familymart.ui;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alp.familymart.DashboardActivity;
import com.alp.familymart.R;
import com.alp.familymart.click_listeners.ProductClickListener;
import com.alp.familymart.modelclass.AddCartModel;
import com.alp.familymart.modelclass.Example;
import com.alp.familymart.modelclass.ViewCartModel;
import com.alp.familymart.modelclass.cart_model.CartModel;
import com.alp.familymart.modelclass.cart_model.ViewCartBaseModel;
import com.alp.familymart.modelclass.product_model.Category;
import com.alp.familymart.modelclass.product_model.ProductModel;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewCartFragment extends Fragment {

    CartModel parsedResponse;
    List<CartModel> example;
    RequestQueue queue;
    String url;
    ImageView menu_icon;
    private RecyclerView rvCart;
    AdapterCart adapterCart;
    Button update;
    private ProductModel product;

    public static ViewCartFragment newInstance() {

        Bundle args = new Bundle();
        ViewCartFragment fragment = new ViewCartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_viewcart, container, false);
        rvCart = root.findViewById(R.id.rv_cart);
        update = root.findViewById(R.id.btn_update_cart);
        menu_icon = ((DashboardActivity) getActivity()).menu_icon;
        menu_icon.setVisibility(View.GONE);

        queue = Volley.newRequestQueue(getActivity());

        getcart();

        return root;


    }
    public void getcart ()
    {

        url = "https://familymart.online/wp-json/cocart/v1/get-cart/ ";
       // url = "https://familymart.online/wp-json/cocart/v1/get-cart/?";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,  "", new Response.Listener<JSONObject>() {
            @Override
            public void  onResponse(JSONObject response) {


/*                try {
                    Gson gson = new Gson();
                   // ViewCartModel parsedResponse = gson.fromJson(response.toString(), .class);
                   ViewCartModel parsedResponse = gson.fromJson(response.toString(), ViewCartModel.class);
                   setMenu(parsedResponse.example);
                    setCart(parsedResponse.items);
                    Log.e("JSONresponse", "Got Response");
                }
                catch(Exception ex){
                    Log.e("error"," "+ex);
                }
            }
        },
            @Override
            public void onResponse(String response) {
*/


                Log.e("JSONresponse", "Got Response");
                //Toast.makeText(ActivityProductDetails.this,response,Toast.LENGTH_LONG).show();
                //Toast.makeText(ActivityProductDetails.this,"product added",Toast.LENGTH_LONG).show();
                Gson gson =new Gson();
                JSONObject jsonObject = new JSONObject();
                try {

                    //jsonObject.put("map", requireActivity());
                }
                catch(Exception e){

                }
               //String jsStr = jsonObject.toString();

                //ViewCartModel parsedResponse = gson.fromJson(response.toString(), ViewCartModel.class);
                CartModel parsedResponse = gson.fromJson(response.toString(), CartModel.class);


                    Log.e("","");


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
                setCart(parsedResponse );
            }
        },

        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("JSONresponse","ERROR in Response");
            }
        }){
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

              //  String creds = String.format("%s:%s","demo","Nidhi123");

                String creds = String.format("%s:%s","ck_b7b9a62bc1f161d51603048008a239309b2ff1fe","cs_f7a23047d3314fba621c6894ae745bae0a23f0fb");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };

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

        queue.add(jsonObjectRequest);
    }

    private void setCart(CartModel items) {

//    private void setCart(ViewCartModel items) {

        if(adapterCart == null) {
            adapterCart = new AdapterCart(getActivity(), items.getItems(), new ProductClickListener() {
                @Override
                public void onProductClicked(ProductModel example, int adapterPosition) {

                }

                @Override
                public void onProductRemoved(ProductModel example, int position) {
                    removeFromCart( example,  position);
                }

                @Override
                public void onProductUpdated(ProductModel example, int adapterPosition, int quantity) {
                    updateFromeCart(example,adapterPosition,quantity);
                }
            });
            rvCart.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvCart.setAdapter(adapterCart);
        }
        else
            adapterCart.notifyDataSetChanged();


    }

    private void updateFromeCart(ProductModel example, int adapterPosition, int quantity) {
        url="https://familymart.online/wp-json/cocart/v1/item/";
        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("cart_item_key", product.getKey());
            jsonBody.put("quantity", quantity);
        }
        catch(Exception ex){

        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("","");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("","");
            }
        })
        {
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

    private void removeFromCart(ProductModel example, int position)
    {
        url="https://familymart.online/wp-json/cocart/v1/item/?cart_item_key="+example.getKey();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("","");
                Toast.makeText(getActivity(),"deleted",Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("","");
            }
        })
        {
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

        queue.add(jsonObjectRequest);

    }
}