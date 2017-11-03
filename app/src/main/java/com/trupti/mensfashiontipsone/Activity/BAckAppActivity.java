package com.trupti.mensfashiontipsone.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.trupti.mensfashiontipsone.Adapter.RecyclerdownAdapter;
import com.trupti.mensfashiontipsone.Adapter.TopsAppRecyclerView;
import com.trupti.mensfashiontipsone.R;
import com.trupti.mensfashiontipsone.Response.AppDialogResponse;
import com.trupti.mensfashiontipsone.Response.AppOnlineDialogResponse;
import com.trupti.mensfashiontipsone.Utilities.ApiInterface;
import com.trupti.mensfashiontipsone.Utilities.Apiclient2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BAckAppActivity extends AppCompatActivity {
    private ImageView appIconImg;
    private TextView appnameTv;
    private TextView appDescriptionTv;
    private Button cancelBtn;
    private Button okBtn;
    private Call<AppDialogResponse> apiResponseCall;
    private ApiInterface apiInterface1;
    private String appLink;
    private ProgressDialog pDialog;
    private Apiclient2 apiclient2;
    private Button yesbtn;
    private Button nobtn;
    public RecyclerView appsRecyclerview, recycler_2;
    private TopsAppRecyclerView recyclerView1Adapter;
    private RecyclerdownAdapter moreAppsRecyclerViewAdapter;
    private Call<AppOnlineDialogResponse> appListResponseCall;
    private InterstitialAd interstitialAd;
    private Dialog main_dialog;

    private NativeExpressAdView adView;
    private String LOG_TAG = "Example";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activitiy_app_hub);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcentertext);

        pDialog = new ProgressDialog(this);
        pDialog = pDialog.show(BAckAppActivity.this, null, "Showing Ads...", false, false);
        apiInterface1 = apiclient2.getClient().create(ApiInterface.class);
        interstitialAd = new InterstitialAd(BAckAppActivity.this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        AdRequest adRequest1 = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest1);
        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                interstitialAd.show();
            }
        });
        horizontalApiCall();
        verticalApiCall();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {
            Intent intent = new Intent(this, StartActivity.class);
            intent.putExtra("EXIT", true);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void horizontalApiCall() {
        appsRecyclerview = (RecyclerView) findViewById(R.id.recycler1);
        LinearLayoutManager verticalLayoutManagaer = new LinearLayoutManager(BAckAppActivity.this, LinearLayoutManager.HORIZONTAL, false);
        appsRecyclerview.setLayoutManager(verticalLayoutManagaer);
        appsRecyclerview.setItemAnimator(new DefaultItemAnimator());

        appListResponseCall = apiInterface1.TOP_APPS_CALL();
        appListResponseCall.enqueue(new Callback<AppOnlineDialogResponse>() {
            @Override
            public void onResponse(Call<AppOnlineDialogResponse> call, Response<AppOnlineDialogResponse> response) {
                Log.d("response code", String.valueOf(response.code()));
                if (!response.body().isError()) {
                    pDialog.dismiss();
                    List<AppOnlineDialogResponse.AppList> data = response.body().getRow();
                    recyclerView1Adapter = new TopsAppRecyclerView(BAckAppActivity.this, data);
                    appsRecyclerview.setAdapter(recyclerView1Adapter);
                } else {
                    Toast.makeText(BAckAppActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AppOnlineDialogResponse> call, Throwable t) {
                Toast.makeText(BAckAppActivity.this, "Failure Connection..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void verticalApiCall() {
        recycler_2 = (RecyclerView) findViewById(R.id.recyclerbelow);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(BAckAppActivity.this, GridLayoutManager.VERTICAL);
        int numberOfColumns = 3;
        recycler_2.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recycler_2.setItemAnimator(new DefaultItemAnimator());
        appListResponseCall = apiInterface1.MORE_APPS_CALL();
        appListResponseCall.enqueue(new Callback<AppOnlineDialogResponse>() {
            @Override
            public void onResponse(Call<AppOnlineDialogResponse> call, Response<AppOnlineDialogResponse> response) {
                Log.d("response code ", String.valueOf(response.code()));
                if (!response.body().isError()) {

                    List<AppOnlineDialogResponse.AppList> data = response.body().getRow();
                    moreAppsRecyclerViewAdapter = new RecyclerdownAdapter(BAckAppActivity.this, data);
                    recycler_2.setAdapter(moreAppsRecyclerViewAdapter);
                } else {
                    Toast.makeText(BAckAppActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AppOnlineDialogResponse> call, Throwable t) {
                Toast.makeText(BAckAppActivity.this, "Failure Connection..!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void Inistialaddinitilize() {
        interstitialAd = new InterstitialAd(BAckAppActivity.this);
        interstitialAd.setAdUnitId(getString(R.string.NAtive_Add));
        AdRequest adRequest1 = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest1);
        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                interstitialAd.show();
            }
        });


        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {

                showInterstitial();
            }
        });

    }

    private void showInterstitial() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }

    @Override
    public void onBackPressed() {

        backDialog();

    }

    public void backDialog() {


        final Dialog dialog = new Dialog(BAckAppActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.back_dialog);

        yesbtn = (Button) dialog.findViewById(R.id.yes_button);
        nobtn = (Button) dialog.findViewById(R.id.no_button);
        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView text1 = (TextView) dialog.findViewById(R.id.text_view);
        text1.setText("Are you sure you want to exit?");
        NativeExpressAdView adView = (NativeExpressAdView) dialog.findViewById(R.id.back_native_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        dialog.show();


    }

}
