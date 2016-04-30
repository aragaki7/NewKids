package senori.or.jp.sharering.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import senori.or.jp.sharering.R;
import senori.or.jp.sharering.adapter.SectionsPagerAdapter;
import senori.or.jp.sharering.data.Item;
import senori.or.jp.sharering.preference.Pre;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener, View.OnClickListener {

    private ViewPager viewPager;
    private ViewPager toolbarviewpager;
    //    private TabLayout tabLayout;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ToolbarViewPagerAdapter viewPagerAdapter;
    private TextView text_email;
    private TextView text_nicname;
    private CircularImageView imageView;
    public ArrayList<Item> list = new ArrayList<Item>();
    private NavigationView navigationView;

    private Handler handler;
    private Toolbar toolbar;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // toolbar.setBackgroundResource(R.drawable.cloudqw);


//        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbarviewpager = (ViewPager) findViewById(R.id.toolbarviewpager);

//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_assignment_black_36dp), 0);
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_insert_photo_black_36dp), 1);
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_person_black_36dp), 2);

        sectionsPagerAdapter = new

                SectionsPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter = new ToolbarViewPagerAdapter(getSupportFragmentManager());
        toolbarviewpager.setAdapter(viewPagerAdapter);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(toolbarviewpager);


//        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(sectionsPagerAdapter);
        //  tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setOnTabSelectedListener(this);
        CircleIndicator indicators = (CircleIndicator) findViewById(R.id.indicators);
        indicators.setViewPager(viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        text_email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.text_email);
        text_nicname = (TextView) navigationView.getHeaderView(0).findViewById(R.id.text_nicname);
        LinearLayout button_add = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.button_add);

        button_add.setOnClickListener(this);

        imageView = (CircularImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int currentItem = toolbarviewpager.getCurrentItem();
                int setCurrentItem = (currentItem + 1) % 5;
                toolbarviewpager.setCurrentItem(setCurrentItem);
            }
        };

        new Thread(new Runnable() {


            @Override
            public void run() {
                while (true) {

                    try {
                        handler.sendMessage(handler.obtainMessage());
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

//    private void DialogHtmlView(final Context context) {
//        AlertDialog.Builder ab = new AlertDialog.Builder(context);
//        ab.setMessage("네트워크 오류");
//        ab.setPositiveButton("재접속", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                onResume();
//            }
//        });
//        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                MainActivity.this.finish();
//            }
//        });
//        ab.show();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (new Pre(this).getUser(getString(R.string.key_id)) == null) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.login);
//            text_email.setText(null);
//            text_nicname.setText("로그인");
//            imageView.setImageResource(R.drawable.profile);
            new Pre(this).setUser(getString(R.string.key_icon), "null");
            new Pre(this).setUser(getString(R.string.key_cover), "null");
            Intent intent = new Intent(getApplicationContext(), LogIn.class);
            startActivity(intent);
            finish();
        } else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.logout);
            text_email.setText(new Pre(this).getUser(getString(R.string.key_email)));
            text_nicname.setText(new Pre(this).getUser(getString(R.string.key_nicname)));

            imageView.setBorderWidth(10);
            imageView.addShadow();
            imageView.setOnClickListener(this);
            if (!new Pre(this).getUser(getString(R.string.key_icon)).equals("null"))
                Glide.with(this).load("http://133.130.88.202:8080/Images/imags/" + new Pre(this).getUser(getString(R.string.key_icon))).into(imageView);

        }
//        ConnectivityManager manager =
//                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//        if (mobile.isConnected() || wifi.isConnected()) {
//            // WIFI, 3G 어느곳에도 연결되지 않았을때
//
//        } else {
//            Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show();
//            DialogHtmlView(this);
//        }
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        int badgeCount = 10;
//
//        final int SAMPLE2_ID = 34535;
//        ActionItemBadge.update(this, menu.findItem(R.id.action_settings), , ActionItemBadge.BadgeStyles.DARK_GREY, badgeCount);
        //int badgeCount = 2;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {

            Intent intent;
            if (new Pre(getApplicationContext()).getUser(getString(R.string.key_id)) == null) {
                intent = new Intent(getApplicationContext(), LogIn.class);
            } else {
                intent = new Intent(getApplicationContext(), WriteActivity.class);
            }
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent = null;
        if (id == R.id.nav_login) {
            if (new Pre(this).getUser(getString(R.string.key_id)) == null) {
                intent = new Intent(getApplicationContext(), LogIn.class);

            } else {
                new Pre(this).setUser(getString(R.string.key_id), null);
                new Pre(this).setUser(getString(R.string.key_nicname), null);
                new Pre(this).setUser(getString(R.string.key_cover), null);
                new Pre(this).setUser(getString(R.string.key_icon), null);
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                onResume();
            }

            // Handle the camera action
        }
//        else if (id == R.id.nav_gallery) {
//
//
//        } else if (id == R.id.nav_slideshow) {
//
//        }
        else if (id == R.id.nav_manage) {
            intent = new Intent(getApplicationContext(), SettingActivity.class);

        }
//        else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
        if (intent != null) {
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageView) {
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
            intent.putExtra("nicname", new Pre(this).getUser(getString(R.string.key_nicname)));
            startActivity(intent);
        }
        if (v.getId() == R.id.button_add) {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        }

    }


    public class ToolbarViewPagerAdapter extends FragmentPagerAdapter {

        public ToolbarViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class
            // below).

            return ToolbarViewPager.instance(position);

        }

        @Override
        public Object instantiateItem(View container, int position) {
            // TODO Auto-generated method stub
            return getItem(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

    }

    public static class ToolbarViewPager extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private ImageView imageView;
        private int position = 0;

        public static ToolbarViewPager instance(int num) {
            ToolbarViewPager fragment = new ToolbarViewPager();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, num);
            fragment.setArguments(args);

            return fragment;
        }

        public ToolbarViewPager() {

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            position = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_toolbar, container, false);

            // toolbar 광고이미지(이벤트, 광고, 공지 등등)

            imageView = (ImageView) view.findViewById(R.id.imageView);

            Glide.with(this).load("http://133.130.88.202:8080/project/img/image" + (position + 1) + ".jpg").into(imageView);

            return view;
        }


    }
}
