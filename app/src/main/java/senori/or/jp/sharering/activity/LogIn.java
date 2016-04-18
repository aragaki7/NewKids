package senori.or.jp.sharering.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import senori.or.jp.sharering.R;
import senori.or.jp.sharering.preference.Pre;
import senori.or.jp.sharering.thread.ServerThread;


public class LogIn extends AppCompatActivity implements ServerThread.OnConnect {
    private AutoCompleteTextView email;
    private EditText password;
    private Button btn_signIn;
    private Button btn_singup;
    private String str;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Toolbar toolbar = new Toolbar(this);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();
        email = (AutoCompleteTextView) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        imageView = (ImageView) findViewById(R.id.imageView);
        email.setText(new Pre(this).getUser(getString(R.string.key_email)));

        btn_signIn = (Button) findViewById(R.id.email_sign_in_button);
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ServerThread(LogIn.this).execute(0);
            }
        });

        btn_singup = (Button) findViewById(R.id.sign_up);
        btn_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Glide.with(this).fromResource().load(R.drawable.intro).into(imageView);
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void doInBackground() {
        String uri = "http://133.130.88.202:8080/project/login.jsp";
        try {

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(uri);
            List params = new ArrayList(); // 파라미터를 List에 담아서 보냅니다.
            params.add(new BasicNameValuePair("email", email.getText().toString())); //파라미터 이름, 보낼 데이터 순입니다.
            params.add(new BasicNameValuePair("pw", password.getText().toString()));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params);
            post.setEntity(ent);
            HttpResponse responsePOST = client.execute(post);
            HttpEntity resEntity = responsePOST.getEntity();
            str = EntityUtils.toString(resEntity).trim();

        } catch (Exception e) {
            Log.e("err", e.getMessage());
        }

    }

    @Override
    public void onPostExecute() {
        int id = 0;
        String nicname = null;
        String email = null;
        String icon = null;
        String cover = null;
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        try {

            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray("user");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                id = c.getInt("id");
                email = c.getString("email");
                nicname = c.getString("nicname");
                icon = c.getString("icon");
                cover = c.getString("cover");
                Log.e("data", "id:" + id + ",email:" + email + ",name:" + nicname);
            }

            if (email != null) {

                new Pre(this).setUser(getString(R.string.key_email), email);
                new Pre(this).setUser(getString(R.string.key_id), String.valueOf(id));
                new Pre(this).setUser(getString(R.string.key_nicname), nicname);
                if (icon != null) {
                    new Pre(this).setUser(getString(R.string.key_icon), icon);
                }
                if (cover != null) {
                    new Pre(this).setUser(getString(R.string.key_cover), cover);
                }
            }
            if (id != 0) {

                finish();
            } else {
                Toast.makeText(getApplicationContext(), "이메일 또는 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
