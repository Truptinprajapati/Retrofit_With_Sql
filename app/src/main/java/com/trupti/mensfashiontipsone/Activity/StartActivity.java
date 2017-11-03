package com.trupti.mensfashiontipsone.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.trupti.mensfashiontipsone.R;

import pl.droidsonroids.gif.GifImageView;
import pl.droidsonroids.gif.GifTextView;

public class StartActivity extends AppCompatActivity {

    GifTextView apphub;
    ImageView start_button,toolMenu,share_img;
    NativeExpressAdView adView;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        adView = (NativeExpressAdView) findViewById(R.id.native_adView);
        adView.loadAd(new AdRequest.Builder().build());



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.root_linear);
        ImageView shareImage = (ImageView) findViewById(R.id.share);
        GifImageView gifImageView = (GifImageView) findViewById(R.id.gifimage);

        ImageView startImage = (ImageView) findViewById(R.id.start_imageview);
        startImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,MainActivity.class));

            }
        });
        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Men's Fashion Tips app by #dreamyinfotech \nhttps://goo.gl/gsd23e");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Men's Fashion Tips app ");
                startActivity(Intent.createChooser(sharingIntent, "Share using"));


            }
        });

        gifImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    Intent intent = new Intent(StartActivity.this, AppHubActivity.class);
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(StartActivity.this, "Please Turn Your Internet Connection On..!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
            }

            return true;
        }

        if (id == R.id.privacypolicy ) {

            final Dialog dialog = new Dialog(StartActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.privacydialog);
            ImageView imageView = (ImageView) dialog.findViewById(R.id.img_close);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {

        if (isNetworkAvailable()) {
            Intent intent = new Intent(StartActivity.this, BAckAppActivity.class);
            startActivity(intent);
            finish();

        } else {


            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            StartActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        }
    }
}
