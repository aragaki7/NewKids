package senori.or.jp.newkids.frament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import senori.or.jp.newkids.adapter.SNSAdapter;
import senori.or.jp.newkids.data.PhotoData;
import senori.or.jp.newkids.preference.Pre;
import senori.or.jp.newkids.thread.ServerThread;


public class MyFragment extends Fragment implements ServerThread.OnConnect {

    private ArrayList<PhotoData> list = new ArrayList<PhotoData>();
    private RecyclerView recyclerView;
    private String str;
    private SNSAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new SNSAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ServerThread(MyFragment.this).execute(0);
            }
        });

        new ServerThread(this).execute(0);
        return view;
    }


    @Override
    public void onPreExecute() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void doInBackground() {
        String uri = "http://133.130.88.202:8080/project/my.jsp";
        try {

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(uri);
            List params = new ArrayList(); // 파라미터를 List에 담아서 보냅니다.
            params.add(new BasicNameValuePair("id", new Pre(getActivity()).getUser(getString(R.string.key_id)))); //파라미터 이름, 보낼 데이터 순입니다.

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
            JSONArray jsonArray = jsonObject.getJSONArray("sns");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);

                list.add(new PhotoData(c.getInt("number"), c.getInt("id"), c.getString("content"), c.getString("uri"), c.getString("nicname"), c.getString("icon"), c.getString("day")));
            }
        } catch (Exception e) {

        }

        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }
}
