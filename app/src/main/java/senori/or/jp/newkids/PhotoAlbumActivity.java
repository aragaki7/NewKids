package senori.or.jp.newkids;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import senori.or.jp.newkids.adapter.StaggeredAdapter;
import senori.or.jp.newkids.photoalbum.StaggeredGridView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

//PhotoAlbum lib
//https://github.com/maurycyw/StaggeredGridViewDemo/tree/master/src/com/example/staggeredgridviewdemo

public class PhotoAlbumActivity extends AppCompatActivity {
    private StaggeredGridView gridView;
    private StaggeredAdapter adapter;
    private ArrayList<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        gridView = (StaggeredGridView) findViewById(R.id.staggeredGridView1);

        list = new ArrayList<Integer>();

        list.add(R.drawable.cloudqw);
        list.add(R.drawable.left);
        list.add(R.drawable.profile);
        list.add(R.drawable.cloudqw);
        list.add(R.drawable.right_gray);
        list.add(R.drawable.profile);
        list.add(R.drawable.cloudqw);
        list.add(R.drawable.cloudqw);
        list.add(R.drawable.left);
        list.add(R.drawable.profile);
        list.add(R.drawable.cloudqw);
        list.add(R.drawable.right_gray);
        list.add(R.drawable.profile);
        list.add(R.drawable.cloudqw);
        list.add(R.drawable.cloudqw);
        list.add(R.drawable.left);
        list.add(R.drawable.profile);
        list.add(R.drawable.cloudqw);
        list.add(R.drawable.right_gray);
        list.add(R.drawable.profile);
        list.add(R.drawable.cloudqw);


        int margin = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);

        gridView.setItemMargin(margin); // set the GridView margin

        gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well
        adapter = new StaggeredAdapter(getApplicationContext(), list);

        gridView.setAdapter(adapter);

    }

}
