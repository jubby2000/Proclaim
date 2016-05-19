package com.example.jacob.proclaim;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class DetailActivity extends AppCompatActivity implements DetailActivityFragment.OnFragmentInteractionListener{

    // Remove the below line after defining your own ad unit ID.
    //TODO take care of this
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(Gravity.END);
            slide.setInterpolator(AnimationUtils.loadInterpolator(this,
                    android.R.interpolator.fast_out_slow_in));
            slide.setDuration(300);
            slide.excludeTarget(android.R.id.statusBarBackground, true);
            slide.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(slide);
        }

        setContentView(R.layout.activity_detail);

        // Load an ad into the AdMob banner view.
        final AdView adView = (AdView) findViewById(R.id.adView);

        //Starting up ads delays the animation transition. Postpone them here just a smidgeon.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AdRequest adRequest = new AdRequest.Builder()
                        .setRequestAgent("android_studio:ad_template")
                        //TODO Remove this later
                        .addTestDevice(getString(R.string.device_id))
                        .build();
                if (adView != null) {
                    adView.loadAd(adRequest);
                }
            }
        }, 2000);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detail_fragment_container, new DetailActivityFragment())
                .commit();

        String topicName = getIntent().getStringExtra("Topic");

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        if (topicName != null && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(topicName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
