package senori.or.jp.sharering.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

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

import java.util.ArrayList;
import java.util.List;

import senori.or.jp.sharering.R;
import senori.or.jp.sharering.adapter.UserAdapter;
import senori.or.jp.sharering.data.UserData;
import senori.or.jp.sharering.preference.Pre;
import senori.or.jp.sharering.thread.ServerThread;


public class UserActivity extends AppCompatActivity implements ServerThread.OnConnect {
    private String nicname;
    private String str;
    private ArrayList<UserData> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private ImageView imageView_cover;
    private CircularImageView imageView_icon;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nicname = getIntent().getExtras().getString("nicname");
        getSupportActionBar().setTitle(nicname);

        imageView_cover = (ImageView) findViewById(R.id.imageView_cover);
        imageView_icon = (CircularImageView) findViewById(R.id.imageView_icon);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        view = LayoutInflater.from(this).inflate(R.layout.user_header, recyclerView, false);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });

        new ServerThread(this).execute(0);

    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void doInBackground() {
        String uri = "http://133.130.88.202:8080/project/user.jsp";
        try {

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(uri);
            List params = new ArrayList(); // 파라미터를 List에 담아서 보냅니다.
            params.add(new BasicNameValuePair("nicname", nicname)); //파라미터 이름, 보낼 데이터 순입니다.

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
        list.clear();
        //Log.d("str", str);

        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray("user");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);

                list.add(new UserData(c.getInt("number"), c.getInt("id"), c.getString("content"), c.getString("nicname"), c.getString("uri"), c.getString("icon"), c.getString("cover"), c.getString("day")));
            }
        } catch (Exception e) {

        }

        if (!list.get(0).getCover().equals("null"))
            Glide.with(this).load("http://133.130.88.202:8080/Images/imags/" + list.get(0).getCover()).into(imageView_cover);
        else
            imageView_cover.setImageResource(R.drawable.profile);
        if (!list.get(0).getIcon().equals("null"))
            Glide.with(this).load("http://133.130.88.202:8080/Images/imags/" + list.get(0).getIcon()).into(imageView_icon);
        else
            imageView_icon.setImageResource(R.drawable.profile);

        adapter = new UserAdapter(getApplicationContext(), list, view);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (list.get(0).getNicname().equals(new Pre(this).getUser(getString(R.string.key_nicname)))) {
            view.setVisibility(View.INVISIBLE);
        }
    }

}
