package com.example.wise4rmgod.retrofitexample;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wise4rmgod.retrofitexample.adapter.ListAdapter;
import com.example.wise4rmgod.retrofitexample.model.GuardianClass;
import com.example.wise4rmgod.retrofitexample.model.Result;

import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final static String API_KEY = "2d99e21b1f4e4198bb214344a158a224";
    private final static String V2 = "v2";
    private final static String topstories = "topstories";
    private final static String home = "home";
    public RecyclerView recyclerView;
    public LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {

            connected = false;
            Toast.makeText(getApplicationContext(), "No Network Connection", Toast.LENGTH_SHORT).show();
        }

        //calling the method to display the heroes
        getHeroes();

    }

    private void getHeroes() {
        // this is for popular movies from moviedb api
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<GuardianClass> call = api.getHeroes(topstories,V2,home,API_KEY);

        call.enqueue(new Callback<GuardianClass>() {
            @Override
            public void onResponse(Call<GuardianClass> call, Response<GuardianClass> response) {

                GuardianClass result = (GuardianClass) response.body();
                List<Result> listofresult = result.getResults();
                Result testd=listofresult.get(1);



              /**  String[] heroes = new String[listofresult.size()];
                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < listofresult.size(); i++) {
                    heroes[i] = listofresult.get(i).getTitle();
                    Toast.makeText(getApplicationContext(),heroes[i] , Toast.LENGTH_SHORT).show();
                }  **/
                // get the reference of RecyclerView
                recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                // set a GridLayoutManager with default vertical orientation and 2 number of columns
                //set layout manager to recycler view
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setAdapter(new ListAdapter(getApplicationContext(),listofresult));

                }

            @Override
            public void onFailure(Call<GuardianClass> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "error fetching data", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
