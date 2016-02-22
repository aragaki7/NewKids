package senori.or.jp.newkids;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormatSymbols;

import senori.or.jp.newkids.calendar.CalendarView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private CalendarView calendarView;
    private LinearLayout linearLayout;
    private TextView text_sun;
    private TextView text_mon;
    private TextView text_tue;
    private TextView text_wed;
    private TextView text_thu;
    private TextView text_fri;
    private TextView text_sat;
    private TextView text_year;
    private ImageButton preButton;
    private ImageButton nextButton;
    float x1, x2;
    float y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setBackgroundResource(R.drawable.cloudqw);

        text_year = (TextView) findViewById(R.id.text_year);

        text_sun = (TextView) findViewById(R.id.text_sunday);
        text_mon = (TextView) findViewById(R.id.text_monday);
        text_tue = (TextView) findViewById(R.id.text_tuesday);
        text_wed = (TextView) findViewById(R.id.text_wedneday);
        text_thu = (TextView) findViewById(R.id.text_thursday);
        text_fri = (TextView) findViewById(R.id.text_friday);
        text_sat = (TextView) findViewById(R.id.text_satday);

        preButton = (ImageButton) findViewById(R.id.btn_previous);
        nextButton = (ImageButton) findViewById(R.id.btn_next);

        preButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        text_year.setOnClickListener(this);

        DateFormatSymbols dfs = DateFormatSymbols.getInstance();
        String[] weekdays = dfs.getShortWeekdays();

        text_sun.setTextColor(Color.RED);
        text_sun.setText(weekdays[1]);
        text_mon.setText(weekdays[2]);
        text_tue.setText(weekdays[3]);
        text_wed.setText(weekdays[4]);
        text_thu.setText(weekdays[5]);
        text_fri.setText(weekdays[6]);
        text_sat.setText(weekdays[7]);
        text_sat.setTextColor(Color.BLUE);


        // toolbar.setTitleTextColor(Color.rgb(80, 113, 138));
        //android:textColor="#4f708a"
        //fde929
//        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//        startActivity(intent);


        linearLayout = (LinearLayout) findViewById(R.id.layout);
        calendarView = new CalendarView(this, linearLayout);
        calendarView.initCalendar();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        calendarView.setContentext();
        calendarView.printView();

        text_year.setText(calendarView.getData("yyyy.MM"));
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            intent = new Intent(getApplicationContext(), GalleryActivity.class);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            intent = new Intent(getApplicationContext(), SettingActivity.class);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        if (intent != null) {
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_previous:
                calendarView.preMonth();
                calendarView.setContentext();

                text_year.setText(calendarView.getData("yyyy.MM"));
                break;
            case R.id.btn_next:
                calendarView.nextMonth();
                calendarView.setContentext();

                text_year.setText(calendarView.getData("yyyy.MM"));
                break;
            case R.id.text_year:

                new DatePickerDialog(MainActivity.this, datePickerDialog, Integer.valueOf(calendarView.getData("yyyy")), Integer.valueOf(calendarView.getData("MM")), 0).show();

                break;
        }
    }


    private DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendarView.setDate(year, monthOfYear);
            text_year.setText(calendarView.getData("yyyy.MM"));
        }

    };

}
