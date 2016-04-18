package senori.or.jp.sharering.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import senori.or.jp.newkids.R;
import senori.or.jp.newkids.adapter.ReplyAdapter;
import senori.or.jp.newkids.data.Reply_Data;
import senori.or.jp.newkids.preference.Pre;
import senori.or.jp.newkids.thread.ServerThread;

public class ReplyActivity extends AppCompatActivity implements ServerThread.OnConnect {

    private ListView listView;
    private ArrayList<Reply_Data> list = new ArrayList<Reply_Data>();
    private ReplyAdapter adapter;
    private String sb;
    private int number;
    private int count;
    private ImageButton button;
    private AutoCompleteTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        Intent intent = getIntent();
        number = intent.getExtras().getInt("number");
        count = intent.getExtras().getInt("count");
        getSupportActionBar().setTitle("댓글(" + count + ")");
        listView = (ListView) findViewById(R.id.listview);
        button = (ImageButton) findViewById(R.id.button);
        textView = (AutoCompleteTextView) findViewById(R.id.edit_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textView.getText().toString().trim().equals(" "))
                    new ServerThread(new ServerThread.OnConnect() {
                        @Override
                        public void onPreExecute() {

                        }

                        @Override
                        public void doInBackground() {
                            String uri = "http://133.130.88.202:8080/project/reply/write";
                            sb = null;
                            try {
                                HttpClient client = new DefaultHttpClient();
                                HttpPost post = new HttpPost(uri);
                                List params = new ArrayList(); // 파라미터를 List에 담아서 보냅니다.
                                params.add(new BasicNameValuePair("number", String.valueOf(number))); //파라미터 이름, 보낼 데이터 순입니다.
                                params.add(new BasicNameValuePair("id", new Pre(getApplicationContext()).getUser(getString(R.string.key_id))));
                                params.add(new BasicNameValuePair("content", textView.getText().toString()));
                                UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, "utf-8");
                                post.setEntity(ent);
                                HttpResponse responsePOST = client.execute(post);
                                HttpEntity resEntity = responsePOST.getEntity();


                            } catch (Exception e) {

                                sb = null;
                            }
                        }

                        @Override
                        public void onPostExecute() {
                            textView.setText("");
                            getSupportActionBar().setTitle("댓글(" + (count + 1) + ")");
                        }
                    }).execute(0);

                new ServerThread(ReplyActivity.this).execute(0);
            }
        });

        new ServerThread(this).execute(0);
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void doInBackground() {
        String uri = "http://133.130.88.202:8080/project/reply.jsp";
        sb = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(uri);
            List params = new ArrayList(); // 파라미터를 List에 담아서 보냅니다.
            params.add(new BasicNameValuePair("number", String.valueOf(number))); //파라미터 이름, 보낼 데이터 순입니다.

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params);
            post.setEntity(ent);
            HttpResponse responsePOST = client.execute(post);
            HttpEntity resEntity = responsePOST.getEntity();
            sb = EntityUtils.toString(resEntity).trim();
            Log.e("EntityUtils", sb);
        } catch (Exception e) {

            sb = null;
        }
    }

    @Override
    public void onPostExecute() {

        list.clear();
        try {
            JSONObject jsonObject = new JSONObject(sb);
            JSONArray jsonArray = jsonObject.getJSONArray("reply");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                String day = c.getString("day");
                String nicname = c.getString("nicname");
                String content = c.getString("content");

                list.add(new Reply_Data(Date.valueOf(day), nicname, content));
            }

        } catch (Exception e) {
        }
        adapter = new ReplyAdapter(this, list);
        listView.setAdapter(adapter);
    }
}
