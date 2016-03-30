package com.example.jacob.proclaim;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.animation.AnimationUtils;

public class DetailActivity extends AppCompatActivity implements DetailActivityFragment.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(Gravity.TOP);
            slide.setInterpolator(AnimationUtils.loadInterpolator(this,
                    android.R.interpolator.fast_out_slow_in));
            slide.setDuration(500);
            slide.excludeTarget(android.R.id.statusBarBackground, true);
            slide.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(slide);
        }

        setContentView(R.layout.activity_detail);

        String topicName = getIntent().getStringExtra("Topic");

        if (topicName != null && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(topicName);
        }

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
