package com.example.jacob.proclaim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
//            Slide slide = new Slide(Gravity.START);
//            slide.setInterpolator(AnimationUtils.loadInterpolator(this,
//                    android.R.interpolator.linear_out_slow_in));
//            slide.setDuration(1000);
//            getWindow().setEnterTransition(slide);
//        }

        String topicName = getIntent().getStringExtra("Topic");

        if (topicName != null && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(topicName);
        }

    }
}
