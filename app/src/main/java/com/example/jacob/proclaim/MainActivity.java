package com.example.jacob.proclaim;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends AppCompatActivity
        implements MainActivityFragment.OnFragmentInteractionListener,
        FavoritesFragment.OnFragmentInteractionListener
//        , NavigationView.OnNavigationItemSelectedListener
{

    final String LOG_TAG = MainActivity.class.getSimpleName();
    private BottomBar mBottomBar;
    DetailCardViewAdapter mAdapter;
    MainActivityFragment mAuthorFragment;
    MainActivityFragment mTopicFragment;
    FavoritesFragment mFavoriteFragment;
    CoordinatorLayout mCoordinatorLayout;
    FragmentManager mFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuthorFragment = new MainActivityFragment();
        mTopicFragment = new MainActivityFragment();
        mFavoriteFragment = new FavoritesFragment();

//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.author_bar_fragment, mAuthorFragment);
//        transaction.add(R.id.topic_bar_fragment, mTopicFragment);
//        transaction.add(R.id.favorite_bar_fragment, mFavoriteFragment);
//        transaction.commit();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//            Slide upSlide = new Slide(Gravity.TOP);
            Slide startSlide = new Slide(Gravity.START);
            startSlide.setInterpolator(AnimationUtils.loadInterpolator(this,
                    android.R.interpolator.linear_out_slow_in));

//            upSlide.setDuration(500);
            startSlide.setDuration(300);
//            upSlide.addTarget(R.id.toolbar);
//            startSlide.excludeTarget(R.id.toolbar, true);
            startSlide.excludeTarget(android.R.id.statusBarBackground, true);
            startSlide.excludeTarget(android.R.id.navigationBarBackground, true);
            //getWindow().setEnterTransition(upSlide);
            getWindow().setExitTransition(startSlide);
        }

        setContentView(R.layout.content_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.topic_bar_title));
        }

        //Add all three fragments to the same view, but hide the ones you don't want to see until click
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mTopicFragment)
                .add(R.id.fragment_container, mAuthorFragment)
                .add(R.id.fragment_container, mFavoriteFragment)
                .hide(mAuthorFragment)
                .hide(mFavoriteFragment)
                .commit();

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        //To make this hide on scroll, adjust attach to attachShy with a CoordinatorLayout
        mBottomBar = BottomBar.attachShy(mCoordinatorLayout,
                findViewById(R.id.fragment_container), savedInstanceState);
//        mBottomBar.noTopOffset();
        mBottomBar.noNavBarGoodness();

//        mBottomBar.setActiveTabColor(ContextCompat.getColor(this, R.color.colorAccent));
        
        mBottomBar.setDefaultTabPosition(1);

        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {

            //                    Fragment author = fm.findFragmentById(R.id.author_bar_fragment);
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.author_bar_item) {

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(getString(R.string.author_bar_title));
                    }

//                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = mFragmentManager.beginTransaction();
//                    ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                    ft.replace(R.id.fragment_container, mAuthorFragment);
//                    ft.hide(mFavoriteFragment);
//                    ft.hide(mTopicFragment);
                    Bundle args = new Bundle();
                    args.putString("Author", "Author");
                    mAuthorFragment.setArguments(args);

                    ft.replace(R.id.fragment_container, mAuthorFragment);
                    ft.setTransition(ft.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                    // The user selected item number one.
                }
                if (menuItemId == R.id.topic_bar_item) {
                    // The user selected item number two.

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(getString(R.string.topic_bar_title));
                    }
//                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = mFragmentManager.beginTransaction();
//                    ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                    ft.replace(R.id.fragment_container, mTopicFragment);
//                    ft.hide(mFavoriteFragment);
                    ft.replace(R.id.fragment_container, mTopicFragment);
//                    ft.hide(mAuthorFragment);
                    ft.setTransition(ft.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
                if (menuItemId == R.id.favorite_bar_item) {

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(getString(R.string.favorite_bar_title));
                    }
//                    FragmentManager fm = getSupportFragmentManager();

                    FragmentTransaction ft = mFragmentManager.beginTransaction();
                    //ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                    ft.replace(R.id.fragment_container, mFavoriteFragment);
                    ft.replace(R.id.fragment_container, new FavoritesFragment());
//                    ft.hide(mTopicFragment);
//                    ft.hide(mAuthorFragment);
                    ft.setTransition(ft.TRANSIT_FRAGMENT_FADE);
                    ft.commit();


                    // The user selected item number three.
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.author_bar_item) {
//                    FragmentManager fm = getSupportFragmentManager();
//
//                    fm.findFragmentById(R.id.fragment_container).getView().scrollTo(0, 0);
//                    fm.findFragmentById(R.id.fragment_container)
                    // The user reselected item number one, scroll your content to top.
                }
                if (menuItemId == R.id.topic_bar_item) {
                    // The user reselected item number one, scroll your content to top.
                }
                if (menuItemId == R.id.favorite_bar_item) {
                    // The user reselected item number one, scroll your content to top.
                }
            }
        });



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

    }

    public void click(View view, Intent intent) {
        Log.v(LOG_TAG, "click method called");
        //Slide slide = new Slide(Gravity.START);

        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        TransitionManager.beginDelayedTransition(root);

        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle();

        this.startActivity(intent, bundle);
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_favorite) {
//            Intent intent = new Intent(this, DetailActivity.class);
//            intent.putExtra("Topic", "Favorites");
//            startActivity(intent);
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
