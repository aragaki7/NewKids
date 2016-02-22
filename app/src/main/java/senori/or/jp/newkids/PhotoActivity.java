package senori.or.jp.newkids;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoActivity extends Activity {

    private ImageView imageView;
    private PhotoViewAttacher viewAttacher;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Intent intent = getIntent();
        int i = intent.getExtras().getInt("id", 0);
        Log.d("position", "" + i);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(i);

        viewAttacher = new PhotoViewAttacher(imageView);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

}
