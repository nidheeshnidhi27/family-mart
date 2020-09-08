package com.alp.familymart;

import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alp.familymart.click_listeners.CategoryClickListener;
import com.alp.familymart.modelclass.category_model.ProductCategory;
import com.alp.familymart.modelclass.product_model.Category;
import com.alp.familymart.ui.CoinsFragment;
import com.alp.familymart.ui.DashboardFragment;
import com.alp.familymart.ui.NotificationsFragment;
import com.alp.familymart.ui.ProfileFragment;
import com.alp.familymart.ui.ViewCartFragment;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    public ImageView menu_icon, ivCart;
    public DrawerLayout drawer;
    Category parsedResponse;
    RequestQueue queue;


//   Button logout;
    String s,url;
    RecyclerView rvMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.nav_view);
        ivCart = findViewById(R.id.imageView3);

        menu_icon = findViewById(R.id.main_menu);

        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_drawer);
        rvMenu = navigationView.findViewById(R.id.rv_menu);

        //click listeners
        ivCart.setOnClickListener(this);

        //menu_icon.setOnClickListener(this);
         getMenu();

//        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DashboardFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                s = item.getTitle().toString();

                switch (s) {
                    case "Coins" :
                        menu_icon.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CoinsFragment()).commit();
                        break;
                    case "Browse" :
                        menu_icon.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DashboardFragment()).commit();
                        break;
                    case "Notifications" :
                        menu_icon.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new NotificationsFragment()).commit();
                        break;
                    case "Profile" :
                        menu_icon.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ProfileFragment()).commit();
                        break;
                }
                return true;
            }

//            Fragment fragment= DashboardFragment.newInstance();
//            FragmentManager fm = getFragmentManager();
            // create a FragmentTransaction to begin the transaction and replace the Fragment
//            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            // replace the FrameLayout with new Fragment
//                fragmentTransaction.replace(R.id.container, selectedfrag,"");
//                fragmentTransaction.commit(); // save the changes


//            @Override
           /* public void onNavigationItemReselected(@NonNull MenuItem item) {
                int  menuId = item.getItemId();
                switch (menuId){
                    case R.id.navigation_home:

                        break;
                    case R.id.navigation_dashboard:

                        break;
                    case R.id.navigation_notifications:

                        break;
                    case R.id.navigation_profile:

                        break;


                }



            }*/
        });

       /* menu_icon
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

        */

    }





//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
//        rvProducts.setLayoutManager(layoutManager);
//        rvProducts.setHasFixedSize(true);
//        rvProducts.setAdapter(productsList);

    public void getMenu()
    {
        queue = Volley.newRequestQueue(this);
        url = "https://familymart.online/wc-api/v3/products/categories";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,  "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    //JSONArray category = response;
                    Gson gson = new Gson();

//                    JSONObject finalObj = new JSONObject();
//                    finalObj.put("category",category);

                     parsedResponse = gson.fromJson(response.toString(), Category.class);

//                    setMenu(parsedResponse.example);

                     Log.e("JSONresponse", "Got Response");
                    addDrawer(parsedResponse);

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



    boolean doubleBackToExitPressedOnce = false;

    public void addDrawer(Category parsedResponse)
    {
        AdapterDrawer adapterDrawer = new AdapterDrawer(this, parsedResponse.getCategories(), new CategoryClickListener() {
            @Override
            public void onCategoryClicked(ProductCategory example, int adapterPosition) {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                if(f instanceof DashboardFragment){
                    DashboardFragment dashboardFragment = (DashboardFragment)f;
                    dashboardFragment.getProducts("",example.getName());
                }
            }
        });
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setAdapter(adapterDrawer);
    }

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


    @Override
    public void onClick(View view) {
        if(view == menu_icon)
        {
           // getMenu();
        }
        if(view == ivCart){
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ViewCartFragment()).commit();
        }
    }
}
