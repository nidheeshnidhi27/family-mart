package com.alp.familymart.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alp.familymart.DashboardActivity;
import com.alp.familymart.R;
import com.alp.familymart.click_listeners.ProductClickListener;
import com.alp.familymart.modelclass.BaseModel;
import com.alp.familymart.modelclass.product_model.ProductModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  DashboardFragment extends Fragment {

    public static final String PRODUCT_OBJECT = "product_object";
    RequestQueue queue;
    RecyclerView rvProducts;
    // private SearchView searchView;
    private EditText editText;
    private ImageView filterimg;
    String searchText = "";
    String filter, url;
    String menu;
    ImageView menu_icon;
    DrawerLayout drawer;

    public static DashboardFragment newInstance() {

        Bundle args = new Bundle();
        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        rvProducts = root.findViewById(R.id.rv_products);
       editText = root.findViewById(R.id.edit_text);
       //searchView = root.findViewById(R.id.search_view);
        filterimg = root.findViewById(R.id.filter_img);

        if(getActivity() instanceof DashboardActivity) {

            menu_icon = ((DashboardActivity) getActivity()).menu_icon;
            drawer = ((DashboardActivity) getContext()).drawer;

        }
        if(menu_icon != null) {
            menu_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                        showPopupMenu(view);

                    drawer.open();

                }
            });

        }

        //search
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getProducts(charSequence.toString(),"");
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        filterimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });


getProducts(searchText,"");

        return root;
    }

    private void setProductsList(List<ProductModel> example){
        AdapterProductsList productsList = new AdapterProductsList(getActivity(), example, new ProductClickListener() {
            @Override
            public void onProductClicked(ProductModel example, int adapterPosition) {

                Intent productDetailIntent = new Intent(getActivity(), ActivityProductDetails.class);
                productDetailIntent.putExtra(PRODUCT_OBJECT,example);
                startActivity(productDetailIntent);

            }

            @Override
            public void onProductRemoved(ProductModel example, int adapterPosition) {

            }

            @Override
            public void onProductUpdated(ProductModel example, int adapterPosition, int quantity) {

            }

        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        rvProducts.setLayoutManager(layoutManager);
        rvProducts.setHasFixedSize(true);
        rvProducts.setAdapter(productsList);
    }

    public void getProducts(String searchText,String menu){

        queue = Volley.newRequestQueue(getActivity());

        //String url="https://familymart.online/wp-json";
       // String url="https://familymart.online/wp-json/wc/v2/products";
       url = "https://familymart.online/wp-json/wc/v2/products";
        if(!searchText.equalsIgnoreCase(""))
            url = url + "/?search="+searchText;

      /*  if(filter != null && !filter.equalsIgnoreCase("default"))
            if(!searchText.equalsIgnoreCase(""))
                url = url +"&filter=["+filter+"]=asc";
            else
                url = url + "/?filter="+filter;
                *
       */


            if(menu != null) {
                if (menu.equalsIgnoreCase("chocolate")) {
                    url = url + "?category=133";
                }

                else if (menu.equalsIgnoreCase("icecream")) {
                    url = url + "?category=134";
                }

                else if (menu.equalsIgnoreCase("electronics")) {
                    url = url + "?category=135";
                }

                else if (menu.equalsIgnoreCase("grocery")) {
                    url = url + "?category=27";
                }

                else if (menu.equalsIgnoreCase("vegitables")) {
                    url = url + "?category=102";
                }


            }
          //  else
            //    url = url + "/?filter="+filter;


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET,url,  "", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONArray example = response;
                    Gson gson = new Gson();


                    JSONObject finalObj = new JSONObject();
                    finalObj.put("example",example);

                    BaseModel parsedResponse = gson.fromJson(finalObj.toString(), BaseModel.class);

                    setProductsList(parsedResponse.example);

                    Log.e("JSONresponse", "Got Response");
                }
                catch(Exception ex){
                    Log.e("error"," "+ex);
                }

            }
        }, new Response.ErrorListener() {
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



    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        //PopupMenu popup = new PopupMenu(this,v);
        MenuInflater inflater1 = popup.getMenuInflater();
        inflater1.inflate(R.menu.filter, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                int itemId = menuItem.getItemId();
                switch (itemId){
                    case R.id.defaultt:
                        filter = "default sort";
                        getProducts("","");
                        break;


                    case R.id.popularity:
                        filter = "sort by popularity";
                        getProducts("","");
                        break;
                    case R.id.rating:
                        filter = "average rating";
                        getProducts("","");
                        break;
                    case R.id.latest:
                        filter = "sort by latest";
                        getProducts("","");
                        break;
                    case R.id.pricelow:
                        filter = "pricelow";
                        getProducts("","");
                        break;
                    case R.id.pricehigh:
                        filter = "pricehigh";
                        getProducts("","");
                        break;

                }
                return false;
            }
        });
        popup.show();
    }


//    public void showPopupMenu(View v) {
////        PopupMenu popup = new PopupMenu(getActivity(), v);
//        PopupMenu popup = new PopupMenu(getActivity(), v);
//        MenuInflater inflater1 = popup.getMenuInflater();
//        inflater1.inflate(R.menu.menu_item, popup.getMenu());
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//
//                int itemId = menuItem.getItemId();
//                switch (itemId){
//                    case R.id.home:
//                        menu="home";
//                        new DashboardFragment();
////                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DashboardFragment()).commit();
//                        break;
//                    case R.id.chocolate:
//                        menu="chocolate";
//                        break;
//
//                    case R.id.icecream:
//                        menu="icecream";
//                        break;
//
//                    case R.id.electronics:
//                        menu="electronics";
//                        break;
//
//                    case R.id.grocery:
//                        menu="grocery";
//                        break;
//                    case R.id.vegitables:
//                        menu="vegitables";
//                        break;
//
//                }
//                getProducts("");
//                return false;
//            }
//        });
//        popup.show();
//    }

    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.filter_img) {
            switch (item.getItemId()) {
                case R.id.defaultt:
                    return true;
                case R.id.popularity:
                    return true;
                case R.id.rating:
                    return true;
                case R.id.latest:
                    return true;
                case R.id.pricehigh:
                    return true;
                case R.id.pricelow:
                    return true;

            }
        }
        return false;
    }
}