package com.example.jacob.proclaim;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
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

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.common.api.Result;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends AppCompatActivity
        implements MainActivityFragment.OnFragmentInteractionListener,
        FavoritesFragment.OnFragmentInteractionListener
//        , NavigationView.OnNavigationItemSelectedListener
{

    final String LOG_TAG = MainActivity.class.getSimpleName();
    private BottomBar mBottomBar;
    public static String PACKAGE_NAME;
    int REQUEST_INVITE = 0;
    DetailCardViewAdapter mAdapter;
    MainActivityFragment mAuthorFragment;
    MainActivityFragment mTopicFragment;
    FavoritesFragment mFavoriteFragment;
    CoordinatorLayout mCoordinatorLayout;
    FragmentManager mFragmentManager = getSupportFragmentManager();
    Snackbar mSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PACKAGE_NAME = getApplicationContext().getPackageName();
        mAuthorFragment = new MainActivityFragment();
        mTopicFragment = new MainActivityFragment();
        mFavoriteFragment = new FavoritesFragment();

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
        mBottomBar.setDefaultTabPosition(1);

//        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {

            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.author_bar_item) {

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(getString(R.string.author_bar_title));
                    }

                    FragmentTransaction ft = mFragmentManager.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("Author", "Author");
                    mAuthorFragment.setArguments(args);
                    ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                    ft.setTransition(ft.TRANSIT_FRAGMENT_FADE);
                    ft.replace(R.id.fragment_container, mAuthorFragment);
//                    ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                    ft.commit();
                    // The user selected item number one.
                }
                if (menuItemId == R.id.topic_bar_item) {
                    // The user selected item number two.

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(getString(R.string.topic_bar_title));
                    }
                    FragmentTransaction ft = mFragmentManager.beginTransaction();
                    ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                    ft.setTransition(ft.TRANSIT_FRAGMENT_FADE);
                    ft.replace(R.id.fragment_container, mTopicFragment);
//                    ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                    ft.commit();
                }
                if (menuItemId == R.id.favorite_bar_item) {

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(getString(R.string.favorite_bar_title));
                    }

                    FragmentTransaction ft = mFragmentManager.beginTransaction();
                    ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                    ft.setTransition(ft.TRANSIT_FRAGMENT_FADE);
                    ft.replace(R.id.fragment_container, mFavoriteFragment);
//                    ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                    ft.commit();
                    // The user selected item number three.
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

                //Standard behavior is to scroll content to the top when the current tab is reselected
                if (menuItemId == R.id.favorite_bar_item) {
                    mFavoriteFragment.scrollToTop();
                }
                if (menuItemId == R.id.topic_bar_item) {
                    mTopicFragment.scrollToTop();
                }
                if (menuItemId == R.id.author_bar_item) {
                    mAuthorFragment.scrollToTop();
                }
            }
        });

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

        MenuItem shareMenuItem = menu.findItem(R.id.menu_share);
        shareMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onInviteClicked();
                return true;
            }
        });

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        if (searchView != null) {
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(
                            new ComponentName(getApplicationContext(), SearchActivity.class)));
        }


        return true;
    }

    private void onInviteClicked() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
//                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
//                .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
//                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(LOG_TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Check how many invitations were sent and log a message
                // The ids array contains the unique invitation ids for each invitation sent
                // (one for each contact select by the user). You can use these for analytics
                // as the ID will be consistent on the sending and receiving devices.
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                Log.v(LOG_TAG, "Hey, you just sent " + ids.length + " invites! Thank you!");
            } else {
                // Sending failed or it was canceled, show failure message to the user
//                showMessage(getString(R.string.send_failed));
                Log.v(LOG_TAG, "Sending failed.");
            }
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
