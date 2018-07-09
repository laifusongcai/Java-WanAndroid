package com.laifusongcai.android.wanandroid.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.laifusongcai.android.wanandroid.R;
import com.laifusongcai.android.wanandroid.ui.home.HomeFragment;
import com.laifusongcai.android.wanandroid.ui.knowledge.KnowledgeFragment;
import com.laifusongcai.android.wanandroid.ui.navigation.NavigationFragment;
import com.laifusongcai.android.wanandroid.ui.project.ProjectFragment;
import com.laifusongcai.android.wanandroid.utils.BottomNavigationViewHelper;

import java.util.ArrayList;

/**
 * @author laifusongcai
 * @date 18-6-23
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mBottomNavigationView;
    private TextView mTitleView;
    private FrameLayout mFrameLayout;

    private ArrayList<Fragment> mFragments;
    private HomeFragment mHomeFragment;
    private NavigationFragment mNavigationFragment;
    private KnowledgeFragment mKnowledgeFragment;
    private ProjectFragment mProjectFragment;
    private int mLastFgIndex;

    public static final int TYPE_MAIN = 0;
    public static final int TYPE_NAVIGATION = 1;
    public static final int TYPE_KNOWLEDGE = 2;
    public static final int TYPE_PROJECT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        mTitleView = (TextView) findViewById(R.id.toolbar_title_tv);
        mFrameLayout = (FrameLayout) findViewById(R.id.fragment_group);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        if (savedInstanceState == null) {
            initPager(TYPE_MAIN);
        } else {
            initPager(TYPE_MAIN);
        }

        initBottomNavigationView();
    }

    private void initPager(int position) {
        mHomeFragment = new HomeFragment();
        mNavigationFragment = new NavigationFragment();
        mKnowledgeFragment = new KnowledgeFragment();
        mProjectFragment = new ProjectFragment();

        mFragments.add(mHomeFragment);
        mFragments.add(mNavigationFragment);
        mFragments.add(mKnowledgeFragment);
        mFragments.add(mProjectFragment);

        loadPager(getString(R.string.bottom_nav_home), mHomeFragment, position, TYPE_MAIN);
    }

    private void initBottomNavigationView() {
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_item_home:
                        loadPager(getString(R.string.bottom_nav_home), mHomeFragment, 0, TYPE_MAIN);
                        break;
                    case R.id.bottom_item_nav:
                        loadPager(getString(R.string.bottom_nav_navigation), mNavigationFragment, 1, TYPE_NAVIGATION);
                        break;
                    case R.id.bottom_item_knowledge:
                        loadPager(getString(R.string.bottom_nav_knowledge), mKnowledgeFragment, 2, TYPE_KNOWLEDGE);
                        break;
                    case R.id.bottom_item_project:
                        loadPager(getString(R.string.bottom_nav_project), mProjectFragment, 3, TYPE_PROJECT);
                        break;
                    default:
                        break;

                }
                return true;
            }
        });
    }

    private void loadPager(String title, Fragment fragment, int position, int type) {
        mTitleView.setText(title);
        switchFragment(position);
    }

    private void switchFragment(int position) {
        if (position >= mFragments.size()) {
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = mFragments.get(position);
        Fragment lastFragment = mFragments.get(mLastFgIndex);
        mLastFgIndex = position;
        fragmentTransaction.hide(lastFragment);
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            fragmentTransaction.add(R.id.fragment_group, fragment);
        }
        fragmentTransaction.show(fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.header_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
