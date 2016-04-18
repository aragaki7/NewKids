package senori.or.jp.sharering.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import senori.or.jp.newkids.R;
import senori.or.jp.newkids.preference.Pre;
import senori.or.jp.newkids.thread.ServerThread;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private CircularImageView imageView_profile;
    private ImageView imageView_cover;
    private ImageButton button;
    private Uri data;
    private String filefath;
    private String image_uri = "http://133.130.88.202:8080/Images/imags/";
    private String err = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            err = null;
            this.data = data.getData();
            String[] filStrings = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(this.data, filStrings, null, null, null);
            cursor.moveToFirst();

            int column = cursor.getColumnIndex(filStrings[0]);
            filefath = cursor.getString(column);
            cursor.close();
            Glide.with(this).load(filefath).into(imageView_profile);
            final String file[] = filefath.split("/");

            new ServerThread(new ServerThread.OnConnect() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void doInBackground() {
                    String uri = "http://133.130.88.202:8080/project//profile/update";
                    try {

                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://133.130.88.202:8080/Images/upload.jsp");
                        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setCharset(Charset.forName("UTF-8")).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                        builder.addPart("fileData", new FileBody(new File(filefath)));
                        httpPost.setEntity(builder.build());
                        httpClient.execute(httpPost);


                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost(uri);
                        List params = new ArrayList(); // 파라미터를 List에 담아서 보냅니다.
                        params.add(new BasicNameValuePair("id", new Pre(getApplicationContext()).getUser(getString(R.string.key_id)))); //파라미터 이름, 보낼 데이터 순입니다.
                        params.add(new BasicNameValuePair("icon", file[file.length - 1]));

                        UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params);
                        post.setEntity(ent);
                        HttpResponse responsePOST = client.execute(post);
                        HttpEntity resEntity = responsePOST.getEntity();


                    } catch (Exception e) {
                        Log.e("err", e.getMessage());
                    }
                }

                @Override
                public void onPostExecute() {
                    if (err == null) {
                        if (!new Pre(getApplicationContext()).getUser(getString(R.string.key_icon)).equals("null")) {
                            File files = new File("http://133.130.88.202:8080/project/img/" + new Pre(getApplicationContext()).getUser(getString(R.string.key_icon)));
                            if (files.exists()) {
                                files.delete();
                            }
                        }
                        new Pre(getApplicationContext()).setUser(getString(R.string.key_icon), file[file.length - 1]);
                    }
                }
            }).execute(0);

        }

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            err = null;
            this.data = data.getData();
            String[] filStrings = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(this.data, filStrings, null, null, null);
            cursor.moveToFirst();

            int column = cursor.getColumnIndex(filStrings[0]);
            filefath = cursor.getString(column);
            cursor.close();

            final String file[] = filefath.split("/");
            Glide.with(this).load(filefath).into(imageView_cover);
            new ServerThread(new ServerThread.OnConnect() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void doInBackground() {
                    String uri = "http://133.130.88.202:8080/project/cover/update";
                    try {

                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://133.130.88.202:8080/Images/upload.jsp");
                        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setCharset(Charset.forName("UTF-8")).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                        builder.addPart("fileData", new FileBody(new File(filefath)));
                        httpPost.setEntity(builder.build());
                        httpClient.execute(httpPost);


                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost(uri);
                        List params = new ArrayList(); // 파라미터를 List에 담아서 보냅니다.
                        params.add(new BasicNameValuePair("id", new Pre(getApplicationContext()).getUser(getString(R.string.key_id)))); //파라미터 이름, 보낼 데이터 순입니다.
                        params.add(new BasicNameValuePair("cover", file[file.length - 1]));

                        UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params);
                        post.setEntity(ent);
                        HttpResponse responsePOST = client.execute(post);
                        HttpEntity resEntity = responsePOST.getEntity();


                    } catch (Exception e) {
                        Log.e("err", e.getMessage());
                    }
                }

                @Override
                public void onPostExecute() {
                    if (err == null) {
                        if (!new Pre(getApplicationContext()).getUser(getString(R.string.key_cover)).equals("null")) {
                            File files = new File("http://133.130.88.202:8080/project/img/" + new Pre(getApplicationContext()).getUser(getString(R.string.key_cover)));
                            if (files.exists()) {
                                files.delete();
                            }
                        }
                        new Pre(getApplicationContext()).setUser(getString(R.string.key_cover), file[file.length - 1]);
                    }
                }
            }).execute(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        imageView_cover = (ImageView) findViewById(R.id.imageView_cover);
        imageView_profile = (CircularImageView) findViewById(R.id.imageView_profile);
        imageView_profile.setBorderWidth(10);
        imageView_profile.addShadow();

        button = (ImageButton) findViewById(R.id.button);

        imageView_profile.setOnClickListener(this);
        imageView_cover.setOnClickListener(this);
        button.setOnClickListener(this);
        if (!new Pre(this).getUser(getString(R.string.key_icon)).equals("null")) {

            Glide.with(this).load(image_uri + new Pre(this).getUser(getString(R.string.key_icon))).into(imageView_profile);
        }
        if (!new Pre(this).getUser(getString(R.string.key_cover)).equals("null")) {
            Glide.with(this).load(image_uri + new Pre(this).getUser(getString(R.string.key_cover))).into(imageView_cover);

        }

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        switch (id) {
            case R.id.imageView_profile:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                break;
            case R.id.imageView_cover:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
                break;
            case R.id.button:
                finish();
                break;

        }
    }
}
