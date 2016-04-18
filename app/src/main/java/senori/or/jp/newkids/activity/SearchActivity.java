package senori.or.jp.newkids.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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

import senori.or.jp.newkids.R;
import senori.or.jp.newkids.adapter.SearchAdapter;
import senori.or.jp.newkids.data.SearchData;
import senori.or.jp.newkids.info.RecyclerViewOnItemClicklistener;
import senori.or.jp.newkids.thread.ServerThread;

public class SearchActivity extends AppCompatActivity implements ServerThread.OnConnect {

    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private ArrayList<SearchData> list = new ArrayList<SearchData>();
    private String newText;
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new SearchAdapter(this, list);
        recyclerView.setAdapter(adapter);


        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClicklistener(this, recyclerView, new RecyclerViewOnItemClicklistener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("nicname", list.get(position).getNicname());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        }));
        //new ServerThread(SearchActivity.this).execute(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                SearchActivity.this.newText = query;
                list.clear();
                adapter.notifyDataSetChanged();
                if (!SearchActivity.this.newText.toString().trim().equals("") && SearchActivity.this.newText != null) {
                    new ServerThread(SearchActivity.this).execute(0);
                } else {
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchActivity.this.newText = newText;
                list.clear();
                adapter.notifyDataSetChanged();
                if (!SearchActivity.this.newText.toString().trim().equals("") && SearchActivity.this.newText != null) {
                    new ServerThread(SearchActivity.this).execute(0);
                } else {
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onPreExecute() {

    }

    @Override
    public void doInBackground() {

        String uri = "http://133.130.88.202:8080/project/usersearch.jsp";
        try {

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(uri);
            List params = new ArrayList(); // 파라미터를 List에 담아서 보냅니다.
            params.add(new BasicNameValuePair("nicname", newText)); //파라미터 이름, 보낼 데이터 순입니다.

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
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray("user");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);

                list.add(new SearchData(c.getInt("id"), c.getString("nicname"), c.getString("icon")));
            }
        } catch (Exception e) {

        }
        adapter.notifyDataSetChanged();
    }
}
