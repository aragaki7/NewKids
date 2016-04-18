package senori.or.jp.newkids.frament;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import senori.or.jp.newkids.R;
import senori.or.jp.newkids.adapter.SNSAdapter;
import senori.or.jp.newkids.data.PhotoData;
import senori.or.jp.newkids.info.RecyclerViewOnItemClicklistener;
import senori.or.jp.newkids.preference.Pre;
import senori.or.jp.newkids.thread.ServerThread;

public class SNSFragment extends Fragment implements ServerThread.OnConnect {

    private RecyclerView recyclerView;
    private SNSAdapter adapter;
    private String sb;
    private ArrayList<PhotoData> list = new ArrayList<PhotoData>();
    private SwipeRefreshLayout refreshLayout;

    public SNSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sns, container, false);

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
                new ServerThread(SNSFragment.this).execute(0);
            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClicklistener(getActivity(), recyclerView, new RecyclerViewOnItemClicklistener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {


            }

            @Override
            public void onItemLongClick(View v, int position) {
                if (new Pre(getActivity()).getUser(getString(R.string.key_id)) != null) {
                    if (list.get(position).getId() == Integer.parseInt(new Pre(getActivity()).getUser(getString(R.string.key_id)))) {
                        myDiglogVIew("게시물 수정", "게시물 삭제", position);
                    } else {
                        diglogVIew("게시물 공유", "");
                    }
                }
            }
        }));

        return view;
    }

    public void diglogVIew(String str1, String str2) {

        final CharSequence items[] = {str1, str2};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());     // 여기서 this는 Activity의 this

// 여기서 부터는 알림창의 속성 설정
        builder.setItems(items, new DialogInterface.OnClickListener() {    // 목록 클릭시 설정
            public void onClick(DialogInterface dialog, int index) {
                Toast.makeText(getActivity(), items[index], Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();    // 알림
    }

    public void myDiglogVIew(String str1, String str2, final int position) {

        final CharSequence items[] = {str1, str2};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());     // 여기서 this는 Activity의 this

// 여기서 부터는 알림창의 속성 설정
        builder.setItems(items, new DialogInterface.OnClickListener() {    // 목록 클릭시 설정
            public void onClick(DialogInterface dialog, int index) {

                if (index == 0) {

                    //
                } else {
                    new ServerThread(new ServerThread.OnConnect() {
                        @Override
                        public void onPreExecute() {

                        }

                        @Override
                        public void doInBackground() {
                            String uri = "http://133.130.88.202:8080/project/sns/del";
                            try {

                                HttpClient client = new DefaultHttpClient();
                                HttpPost post = new HttpPost(uri);
                                List params = new ArrayList(); // 파라미터를 List에 담아서 보냅니다.
                                params.add(new BasicNameValuePair("number", String.valueOf(list.get(position).getNumber()))); //파라미터 이름, 보낼 데이터 순입니다.
                                params.add(new BasicNameValuePair("uri", list.get(position).getUrl()));
                                UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params);
                                post.setEntity(ent);
                                HttpResponse responsePOST = client.execute(post);
                                HttpEntity resEntity = responsePOST.getEntity();
                                //Log.e("ent", EntityUtils.toString(resEntity).trim());//str =;

                            } catch (Exception e) {
                                Log.e("err", e.getMessage());
                            }

                        }

                        @Override
                        public void onPostExecute() {
                            new ServerThread(SNSFragment.this).execute(0);
                        }
                    }).execute(0);
                }

            }
        });

        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();    // 알림
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
        String uri = "http://133.130.88.202:8080/project/sns.jsp";

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
