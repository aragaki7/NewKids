package senori.or.jp.newkids.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import senori.or.jp.newkids.R;
import senori.or.jp.newkids.preference.Pre;
import senori.or.jp.newkids.thread.ServerThread;

public class WriteActivity extends AppCompatActivity {

    private static int position = 1;
    private static AutoCompleteTextView text_title;
    private static EditText text_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Setup spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(
                toolbar.getContext(),
                new String[]{
                        "커뮤니티",
                        "포토"
                }));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the given dropdown item is selected, show its contents in the
                // container view.
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.menu_write, menu);
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


    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public MyAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }

        @Override
        public Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements ServerThread.OnConnect {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private EditText edittext;
        private ImageView imageview;
        private ImageButton button_camera;
        private ImageButton button_image;
        private String filefath;
        private String err;
        private Uri data;
        private FileInputStream mFileInputStream = null;
        private URL connectUrl = null;
        private ByteArrayInputStream mByteInputStream = null;
        private byte[] imgbyte;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            position = getArguments().getInt(ARG_SECTION_NUMBER);

            View rootView = null;

            if (position == 1) {
                rootView = inflater.inflate(R.layout.fragment_write, container, false);
                text_content = (EditText) rootView.findViewById(R.id.text_content);
                text_title = (AutoCompleteTextView) rootView.findViewById(R.id.text_title);
            }

            if (position == 2) {
                rootView = inflater.inflate(R.layout.fragment_sns_write, container, false);
                text_content = (EditText) rootView.findViewById(R.id.text_content);
                imageview = (ImageView) rootView.findViewById(R.id.imageView);
                button_camera = (ImageButton) rootView.findViewById(R.id.button_camera);
                button_image = (ImageButton) rootView.findViewById(R.id.button_image);
                button_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 1);
                    }
                });


            }
            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new ServerThread(PlaceholderFragment.this).execute(0);


                }
            });
            return rootView;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
                this.data = data.getData();
                String[] filStrings = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(this.data, filStrings, null, null, null);
                cursor.moveToFirst();

                int column = cursor.getColumnIndex(filStrings[0]);
                filefath = cursor.getString(column);
                cursor.close();
                Glide.with(getActivity()).load(filefath).into(imageview);

            }
        }

        @Override
        public void onPreExecute() {

        }

        @Override
        public void doInBackground() {

            String uri;
            HttpClient client = new DefaultHttpClient();
            err = null;
            try {
                if (position == 1) {
                    uri = "http://133.130.88.202:8080/project/noticeboardwrite.jsp";
                    HttpPost post = new HttpPost(uri);
                    List params = new ArrayList(); // 파라미터를 List에 담아서 보냅니다.
                    params.add(new BasicNameValuePair("id", new Pre(getActivity()).getUser(getString(R.string.key_id))));
                    params.add(new BasicNameValuePair("title", text_title.getText().toString()));
                    params.add(new BasicNameValuePair("content", text_content.getText().toString()));

                    UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, "utf-8");
                    post.setEntity(ent);
                    HttpResponse responsePOST = client.execute(post);
                    HttpEntity resEntity = responsePOST.getEntity();
                    Log.d("resEntity", EntityUtils.toString(resEntity).trim());

                }
                if (position == 2) {
                    if (filefath != null) {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://133.130.88.202:8080/Images/upload.jsp");
                        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setCharset(Charset.forName("UTF-8")).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                        builder.addPart("fileData", new FileBody(new File(filefath)));
                        httpPost.setEntity(builder.build());
                        httpClient.execute(httpPost);

                        String file[] = filefath.split("/");

                        uri = "http://133.130.88.202:8080/project/sns/insert";
                        HttpPost post = new HttpPost(uri);
                        List params = new ArrayList(); // 파라미터를 List에 담아서 보냅니다.
                        params.add(new BasicNameValuePair("id", new Pre(getActivity()).getUser(getString(R.string.key_id))));
                        params.add(new BasicNameValuePair("content", text_content.getText().toString()));
                        params.add(new BasicNameValuePair("url", file[file.length - 1]));

                        UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, "utf-8");
                        post.setEntity(ent);
                        HttpResponse responsePOST = client.execute(post);
                        HttpEntity resEntity = responsePOST.getEntity();
                        Log.d("resEntity", EntityUtils.toString(resEntity).trim());
                    } else {
                        err = "이미지가 없습니다.";
                    }
                }
            } catch (Exception e) {
                Log.e("err", e.getMessage());
                err = e.getMessage();
            }

        }

        @Override
        public void onPostExecute() {
            if (err == null) {
                getActivity().finish();
                Toast.makeText(getActivity(), "저장중...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), err, Toast.LENGTH_SHORT).show();
            }
        }


    }
}
