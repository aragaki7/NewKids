package senori.or.jp.sharering.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import senori.or.jp.sharering.R;
import senori.or.jp.sharering.activity.ReplyActivity;
import senori.or.jp.sharering.data.Item;
import senori.or.jp.sharering.thread.ServerThread;


/**
 * Created by JupiteR on 2016-04-02.
 */
public class NoticeBoardAdapter extends RecyclerView.Adapter<NoticeBoardAdapter.ViewHolder> {

    private ArrayList<Item> list;
    private Context context;

    public NoticeBoardAdapter(Context context, ArrayList<Item> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public NoticeBoardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.noticeboard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NoticeBoardAdapter.ViewHolder holder, final int position) {
        holder.text_count.setText("조회수 : " + list.get(position).getCount());
        holder.text_day.setText(list.get(position).getDay().toString());
        holder.text_nicname.setText(list.get(position).getNicname());
        holder.text_number.setText("" + list.get(position).getNumber());
        holder.text_title.setText(list.get(position).getTitle());
        holder.text_content.setText(list.get(position).getContent());
        holder.text_reply.setText("댓글(" + list.get(position).getReply_count() + ")");

        holder.text_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReplyActivity.class);
                intent.putExtra("number", list.get(position).getNumber());
                intent.putExtra("count", list.get(position).getReply_count());
                context.startActivity(intent);
            }
        });

        if (list.get(position).getVisibility() == View.GONE) {
            holder.layout.setVisibility(View.GONE);
        } else {
            holder.layout.setVisibility(View.VISIBLE);
        }

        holder.layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getVisibility() == View.GONE) {
                    holder.layout.setVisibility(View.VISIBLE);
                    list.get(position).setVisibility(View.VISIBLE);

                    new ServerThread(new ServerThread.OnConnect() {
                        int count = list.get(position).getCount() + 1;

                        @Override
                        public void onPreExecute() {

                        }

                        @Override
                        public void doInBackground() {
                            String uri = "http://133.130.88.202:8080/project/updatecount.jsp";

                            try {

                                HttpClient client = new DefaultHttpClient();
                                HttpPost post = new HttpPost(uri);
                                List params = new ArrayList(); // 파라미터를 List에 담아서 보냅니다.
                                params.add(new BasicNameValuePair("number", String.valueOf(list.get(position).getNumber()))); //파라미터 이름, 보낼 데이터 순입니다.
                                params.add(new BasicNameValuePair("count", String.valueOf(count)));

                                UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params);
                                post.setEntity(ent);
                                HttpResponse responsePOST = client.execute(post);
                                HttpEntity resEntity = responsePOST.getEntity();
                                Log.e("resEntity", EntityUtils.toString(resEntity));

                            } catch (Exception e) {
                                Log.e("err", e.getMessage());
                            }
                        }

                        @Override
                        public void onPostExecute() {
                            list.get(position).setCount(count);
                            holder.text_count.setText("조회수 : " + list.get(position).getCount());
                        }
                    }).execute(0);
                } else {
                    holder.layout.setVisibility(View.GONE);
                    list.get(position).setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text_number;
        public TextView text_title;
        public TextView text_nicname;
        public TextView text_content;
        public TextView text_count;
        public TextView text_day;
        public TextView text_reply;
        public LinearLayout layout;
        public LinearLayout layout1;

        public ViewHolder(View itemView) {
            super(itemView);
            text_reply = (TextView) itemView.findViewById(R.id.text_reply);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            layout1 = (LinearLayout) itemView.findViewById(R.id.layout1);
            text_content = (TextView) itemView.findViewById(R.id.text_content);
            text_number = (TextView) itemView.findViewById(R.id.text_number);
            text_title = (TextView) itemView.findViewById(R.id.text_title);
            text_nicname = (TextView) itemView.findViewById(R.id.text_nicname);
            text_count = (TextView) itemView.findViewById(R.id.text_count);
            text_day = (TextView) itemView.findViewById(R.id.text_day);

        }
    }

}
