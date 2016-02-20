package senori.or.jp.newkids;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.etsy.android.grid.StaggeredGridView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery);

        Drawable drawable = getResources().getDrawable(R.drawable.cloudqw);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(drawable);

        gridView = (StaggeredGridView) findViewById(R.id.gridView);

        list = new ArrayList<Integer>();

        list.add(R.drawable.a);
        list.add(R.drawable.b);
        list.add(R.drawable.c);
        list.add(R.drawable.d);
        list.add(R.drawable.e);
        list.add(R.drawable.a);
        list.add(R.drawable.b);
        list.add(R.drawable.c);
        list.add(R.drawable.d);
        list.add(R.drawable.e);
        list.add(R.drawable.a);
        list.add(R.drawable.b);
        list.add(R.drawable.c);
        list.add(R.drawable.d);
        list.add(R.drawable.e);
        list.add(R.drawable.a);
        list.add(R.drawable.b);
        list.add(R.drawable.c);
        list.add(R.drawable.d);
        list.add(R.drawable.e);

        // int margin = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);

        // gridView.setItemMargin(13); // set the GridView margin

        gridView.setPadding(13, 0, 13, 0); // have the margin on the sides as well
        adapter = new StaggeredAdapter(getApplicationContext(), list);

        gridView.setAdapter(adapter);

    }


}
