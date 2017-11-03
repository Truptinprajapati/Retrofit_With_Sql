package com.trupti.mensfashiontipsone.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.trupti.mensfashiontipsone.Adapter.RecyclerAdapter;
import com.trupti.mensfashiontipsone.Adapter.RecyclerExitAdapter;
import com.trupti.mensfashiontipsone.Adapter.Sqliteadapter;
import com.trupti.mensfashiontipsone.Database.DBHelper;
import com.trupti.mensfashiontipsone.R;
import com.trupti.mensfashiontipsone.Response.AppDialogResponse;
import com.trupti.mensfashiontipsone.Response.AppListResponse;
import com.trupti.mensfashiontipsone.Response.AppOnlineDialogResponse;
import com.trupti.mensfashiontipsone.Utilities.ApiInterface;
import com.trupti.mensfashiontipsone.Utilities.Apiclient2;
import com.trupti.mensfashiontipsone.Utilities.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    InterstitialAd mInterstitialAd;
    private AdView mAdView;

    private RecyclerView dataRecyclerView;
    Sqliteadapter sqliteDescAdapter;
    RecyclerAdapter recyclerAdapterList;
    private ApiInterface apiInterface;
    private RetrofitClient apiServices;
    Call<AppListResponse> appListResponseCall;
    SQLiteDatabase sqLiteDatabase;
    ProgressDialog pDialog;
    ArrayList<String> topicArrayList;

    private Button cancelBtn, okBtn;
    Call<AppOnlineDialogResponse> appOnlineDialogResponseCall;
    private RecyclerExitAdapter exitRecyclerViewAdapter;
    RecyclerView recyclerexit;
    private Call<AppDialogResponse> appDialogResponseCall;
    private ApiInterface apiInterface1;
    private static boolean mHasItRun = false;
    Apiclient2 apiclient2;
    GifImageView gifImageView;
    private ImageView appIconImg,shareImage;
    private TextView appNameTv, appDesTv;
    String appLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        dataRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerexit= (RecyclerView) findViewById(R.id.recyclerexit);
        shareImage= (ImageView) findViewById(R.id.share);
        gifImageView= (GifImageView) findViewById(R.id.gifimage);
        banneradd();


        DBHelper dbHelper = new DBHelper(MainActivity.this);
        sqLiteDatabase = openOrCreateDatabase("manfashiontips", MODE_PRIVATE, null);
        sqLiteDatabase = dbHelper.getReadableDatabase();
        sqLiteDatabase = dbHelper.getWritableDatabase();

        apiInterface = apiServices.getRetrofit().create(ApiInterface.class);
        apiInterface1 = apiclient2.getClient().create(ApiInterface.class);
        topicArrayList = new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        dataRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        dataRecyclerView.setItemAnimator(new DefaultItemAnimator());

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);



        oneTimeApiCall();

    }


    public void saveData() {
        pDialog = pDialog.show(MainActivity.this, null, "Please Wait", false, false);
        appListResponseCall = apiInterface.APP_LIST_RESPONSE_CALL();
        appListResponseCall.enqueue(new Callback<AppListResponse>() {
            @Override
            public void onResponse(Call<AppListResponse> call, Response<AppListResponse> response) {
                Log.d("response_code", String.valueOf(response.code()));

                if (!response.body().isError()) {
                    pDialog.dismiss();
                    List<AppListResponse.CategoryEntity> data = response.body().getRow();
                    recyclerAdapterList = new RecyclerAdapter(MainActivity.this, data);
                    dataRecyclerView.setAdapter(recyclerAdapterList);
                    Log.d("size", "" + data.size());
                } else {
                    pDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<AppListResponse> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(MainActivity.this, "Failure Connection..!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void oneTimeApiCall() {
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun) {
            if (isNetworkAvailable()) {
                saveData();

                SharedPreferences.Editor editor = wmbPreference.edit();
                editor.putBoolean("FIRSTRUN", false);
                editor.commit();
            } else {
                Toast.makeText(this, "Pleasae Turn Your Internet Connection On..!!", Toast.LENGTH_SHORT).show();
            }

        } else {
            sqliteDescAdapter = new Sqliteadapter(MainActivity.this, topicArrayList);

            Cursor cursor = sqLiteDatabase.rawQuery("select topic from manfashion limit 14", null);

            if (cursor.moveToFirst()) {
                do {
                    topicArrayList.add(cursor.getString(cursor.getColumnIndex("topic")));

                } while (cursor.moveToNext());

                dataRecyclerView.setAdapter(sqliteDescAdapter);
                sqliteDescAdapter.notifyDataSetChanged();
            }
        }
    }
    public void share(View view)
    {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Men's Fashion Tips  app by #dreamyinfotech \nhttps://goo.gl/gsd23e");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Men's Fashion Tips Test app ");
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_desc, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        if (id == R.id.rateus) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/gsd23e"));
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException e) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/gsd23e"));
                startActivity(intent);
            }
            return true;
        }
        if (id == R.id.moreapp) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=dreamyinfotech&hl=en"));
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException e) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=dreamyinfotech&hl=en"));
                startActivity(intent);
            }
            return true;
        }
        if (id == R.id.share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Men's Fashion Tips app by #dreamyinfotech \nhttps://goo.gl/gsd23e");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Men's Fashion Tips app ");
            startActivity(Intent.createChooser(sharingIntent, "Share using"));

            return true;
        }

         return super.onOptionsItemSelected(item);
    }

    public void banneradd() {
        mAdView = (AdView) findViewById(R.id.adView_main_screen);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
    }

}
