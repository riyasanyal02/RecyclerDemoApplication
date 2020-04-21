package com.example.altimetrikproject.ui;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.altimetrikproject.R;

public class DescriptionActivity extends AppCompatActivity {

    private String title, blurb, pleadge, country, location, by;

    private TextView titleText, blurpText, pleadgeText, countryText, locationText, byText;

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_layout);

        if(getIntent().getExtras()!=null){
            title = getIntent().getStringExtra("title");
            blurb = getIntent().getStringExtra("blurb");
            pleadge = getIntent().getStringExtra("pleadge");
            country = getIntent().getStringExtra("country");
            location = getIntent().getStringExtra("location");
            by = getIntent().getStringExtra("by");

        }

        init();
    }

    private void init() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Description");

        titleText = findViewById(R.id.title);
        blurpText = findViewById(R.id.blurpText);
        pleadgeText = findViewById(R.id.pleadgeText);
        countryText = findViewById(R.id.countryText);
        locationText = findViewById(R.id.locationText);
        byText = findViewById(R.id.byText);
        imageView = findViewById(R.id.imageView);

       setTextView();

    }

    private void setTextView() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                titleText.setText(title);
                blurpText.setText(blurb);
                pleadgeText.setText(pleadge);
                countryText.setText(country);
                locationText.setText(location);
                byText.setText(by);

                Glide.with(DescriptionActivity.this).load("https://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg").into(imageView);
            }
        },1000);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }
}
