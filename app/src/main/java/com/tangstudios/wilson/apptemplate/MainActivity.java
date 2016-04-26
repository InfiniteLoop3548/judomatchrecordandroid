package com.tangstudios.wilson.apptemplate;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    // Need this to link with the Snackbar
    private RelativeLayout mRoot;
    //Need this to set the title of the app bar
    private FloatingActionButton mFab;
    private Toolbar mToolbar;
    private ViewPager mPager;
    private YourPagerAdapter mAdapter;
    private TabLayout mTabLayout;

    private int pos;

    TabLayout.Tab tabCall;
    TabLayout.Tab tabCall2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRoot = (RelativeLayout) findViewById(R.id.root_relative);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mAdapter = new YourPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setAdapter(mAdapter);
        //Notice how the Tab Layout links with the Pager Adapter
        mTabLayout.setTabsFromPagerAdapter(mAdapter);

        //Notice how The Tab Layout adn View Pager object are linked
        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        tabCall = mTabLayout.getTabAt(0);
        tabCall2 = mTabLayout.getTabAt(1);

        tabCall.setIcon(R.drawable.selector_1);
        tabCall2.setIcon(R.drawable.selector_2);

        final String[] tabTitles = {"Match Record", "Community Service"};

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mToolbar.setTitle(tabTitles[position]);
                pos = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Notice how the Coordinator Layout object is used here
                //Snackbar.make(mRoot, "FAB Clicked", Snackbar.LENGTH_SHORT).setAction("DISMISS", null).show();
                if (pos == 0)
                    startActivity(new Intent(v.getContext(), EditMatchRecord.class));
                else if (pos == 1)
                    startActivity(new Intent(v.getContext(), EditCommService.class));
            }
        });

        //Notice how the title is set on the Collapsing Toolbar Layout instead of the Toolbar
        mToolbar.setTitle(tabTitles[0]);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

}
class YourPagerAdapter extends FragmentStatePagerAdapter {

    public YourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {

            return new Fragment1();

        } else {

            return new Fragment2();

        }

    }

    @Override
    public int getCount() {
        return 2;
    }
/*
    @Override
    public CharSequence getPageTitle(int position) {

//        int whiteIcons[] = {R.drawable.swords_white, R.drawable.person_white};
//        int blackIcons[] = {R.drawable.swords_black, R.drawable.person_black};
//
//        SpannableString[] tabsText = new SpannableString[2];
//        tabsText[0] = new SpannableString("");
//        tabsText[1] = new SpannableString("");
//        Drawable currentWhite;
//        Drawable currentBlack;
//
//        if (position == 0) {
//
//            currentWhite = ContextCompat.getDrawable(context, whiteIcons[0]);
//            currentBlack = ContextCompat.getDrawable(context, blackIcons[1]);
//            currentWhite.setBounds(0, 0, 10, 10);
//            currentBlack.setBounds(0, 0, 10, 10);
//            ImageSpan span1 = new ImageSpan(currentWhite, ImageSpan.ALIGN_BASELINE);
//            ImageSpan span2 = new ImageSpan(currentBlack, ImageSpan.ALIGN_BASELINE);
//
//            tabsText[0].setSpan(span1, 0, 0, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//            tabsText[1].setSpan(span2, 0, 0, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//
//            return tabsText[position];
//
//        } else {
//
//            currentWhite = ContextCompat.getDrawable(context, whiteIcons[1]);
//            currentBlack = ContextCompat.getDrawable(context, blackIcons[0]);
//            currentWhite.setBounds(0, 0, 10, 10);
//            currentBlack.setBounds(0, 0, 10, 10);
//            ImageSpan span1 = new ImageSpan(currentWhite, ImageSpan.ALIGN_BASELINE);
//            ImageSpan span2 = new ImageSpan(currentBlack, ImageSpan.ALIGN_BASELINE);
//
//            tabsText[0].setSpan(span1, 0, 0, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//            tabsText[1].setSpan(span2, 0, 0, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//
//            return tabsText[position];
//
//        }

        return "";


    }
    */
}