package com.trupti.mensfashiontipsone.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.trupti.mensfashiontipsone.R;

public class DiscriptionActivity extends AppCompatActivity {
    TextView descriptonText,headingText;



    private InterstitialAd interstitialAd;

    Toolbar toolbar1;
    ImageView back;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discription);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        headingText= (TextView) findViewById(R.id.btn_heading);
        descriptonText= (TextView) findViewById(R.id.txt_data);
        banneradd();
        Intent intent = getIntent();
        String topicName = intent.getStringExtra("topicName");
        String description = intent.getStringExtra("description");
        headingText.setText(topicName);
        descriptonText.setText(description);
        descriptonText.setMovementMethod(new ScrollingMovementMethod());
    }
    public void back(View view) {
        Intent intent = new Intent(DiscriptionActivity.this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_desc, menu);
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
        adView = (AdView) findViewById(R.id.adView_main_screen);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
    }
}
