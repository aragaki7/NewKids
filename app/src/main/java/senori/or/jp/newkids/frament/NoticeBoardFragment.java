package senori.or.jp.newkids.frament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;

import senori.or.jp.newkids.R;
import senori.or.jp.newkids.activity.MainActivity;
import senori.or.jp.newkids.adapter.NoticeBoardAdapter;
import senori.or.jp.newkids.data.Item;
import senori.or.jp.newkids.thread.ServerThread;

public class NoticeBoardFragment extends Fragment implements ServerThread.OnConnect {

    private ArrayList<Item> list;
    private NoticeBoardAdapter adapter;
    private RecyclerView recyclerView;
    private String sb;
    private SwipeRefreshLayout refreshLayout;

    public NoticeBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notice_board, container, false);

        list = ((MainActivity) getActivity()).list;

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ServerThread(NoticeBoardFragment.this).execute(0);
            }
        });

        new ServerThread(this).execute(0);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        new ServerThread(this).execute(0);
    }

    @Override
    public void onPreExecute() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void doInBackground() {

        String uri = "http://133.130.88.202:8080/project/noticeboard.jsp";

        BufferedReader bufferedReader;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            StringBuilder stringBuilder = new StringBuilder();

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String json;
            while ((json = bufferedReader.readLine()) != null) {
                stringBuilder.append(json + "\n");
            }
            sb = stringBuilder.toString().trim();
        } catch (Exception e) {

            sb = null;
        }


    }

    @Override
    public void onPostExecute() {

        list.clear();

        try {
            JSONObject jsonObject = new JSONObject(sb);
            JSONArray jsonArray = jsonObject.getJSONArray("list");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                String title = c.getString("title");
                String nicname = c.getString("nicname");
                String content = c.getString("content");
                int count = c.getInt("count");
                int number = c.getInt("number");
                int reply_count = c.getInt("reply_count");
                String day = c.getString("day");

                list.add(new Item(number, count, title, content, nicname, Date.valueOf(day), View.GONE, reply_count));
            }

        } catch (Exception e) {
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        adapter = new NoticeBoardAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refreshLayout.setRefreshing(false);

    }
}
