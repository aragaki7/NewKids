package senori.or.jp.newkids;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import senori.or.jp.newkids.adapter.SectionsPagerAdapter;

public class DayActivity extends ActionBarActivity implements TabLayout.OnTabSelectedListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private View view;
    private TextView text_year;
    private ImageButton prebutton;
    private ImageButton nextbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        ActionBar actionBar = getSupportActionBar();


        view = LayoutInflater.from(this).inflate(R.layout.custom_title, null);
        actionBar.setCustomView(view);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);


        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        text_year = (TextView) view.findViewById(R.id.text_year);
        prebutton = (ImageButton) view.findViewById(R.id.btn_previous);
        nextbutton = (ImageButton) view.findViewById(R.id.btn_next);

        text_year.setText(getIntent().getExtras().getString("day"));


        tabLayout.addTab(tabLayout.newTab().setText("공지"), 0);
        tabLayout.addTab(tabLayout.newTab().setText("사진첩"), 1);
        tabLayout.addTab(tabLayout.newTab().setText("일정"), 2);
        tabLayout.addTab(tabLayout.newTab().setText("식단표"), 3);


        sectionsPagerAdapter = new

                SectionsPagerAdapter(getSupportFragmentManager(), getApplicationContext(), tabLayout);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(sectionsPagerAdapter);
        //  tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        Log.d("position", "" + viewPager.isDrawingCacheEnabled());


    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        // Log.d("position2", "" + viewPager.getClipChildren());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Log.d("position1", "" + viewPager.isDrawingCacheEnabled());
    }
}
