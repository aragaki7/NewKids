package senori.or.jp.newkids;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.etsy.android.grid.StaggeredGridView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import senori.or.jp.newkids.adapter.StaggeredAdapter;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

//PhotoAlbum lib
//https://github.com/etsy/AndroidStaggeredGrid

public class GalleryActivity extends ActionBarActivity {
    private StaggeredGridView gridView;
    private StaggeredAdapter adapter;
    private ArrayList<Integer> list;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        gridView = (StaggeredGridView) findViewById(R.id.gridView);

        list = new ArrayList<Integer>();

        list.add(R.drawable.a);
        list.add(R.drawable.b);
        list.add(R.drawable.c);
        list.add(R.drawable.d);
        list.add(R.drawable.e);
        list.add(R.drawable.a);


        // int margin = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);

        // gridView.setItemMargin(13); // set the GridView margin

        gridView.setPadding(13, 0, 13, 0); // have the margin on the sides as well
        adapter = new StaggeredAdapter(getApplicationContext(), list);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
                intent.putExtra("id", list.get(position));
                startActivity(intent);

                //zoomImageFromThumb(gridView, R.drawable.a);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
