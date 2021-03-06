package senori.or.jp.sharering.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
import senori.or.jp.sharering.data.UserData;
import senori.or.jp.sharering.preference.Pre;
import senori.or.jp.sharering.thread.ServerThread;


/**
 * Created by JupiteR on 2016-04-11.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.HolderView> implements ServerThread.OnConnect {
    private Context context;
    private ArrayList<UserData> list;
    private static final int ITEM_VIEW_TYPE_HEADER = 1;
    private static final int ITEM_VIEW_TYPE_ITEM = 2;
    private View header;
    private String str;
    private int req;

    public UserAdapter(Context context, ArrayList<UserData> list, View view) {

        this.list = list;
        this.context = context;
        header = view;

    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == 0) {

            header = LayoutInflater.from(context).inflate(R.layout.user_header, parent, false);
            return new HolderView(header, ITEM_VIEW_TYPE_HEADER);
        } else if (viewType == ITEM_VIEW_TYPE_HEADER) {

            return new HolderView(header, ITEM_VIEW_TYPE_HEADER);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.sns_item, parent, false);
        return new HolderView(view, ITEM_VIEW_TYPE_ITEM);
    }


    @Override
    public void onBindViewHolder(final HolderView holder, final int position) {

        new ServerThread(new ServerThread.OnConnect() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void doInBackground() {
                String uri = "http://133.130.88.202:8080/project/accept.jsp";
                try {

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(uri);
                    List params = new ArrayList(); // 파라미터를 List에 담아서 보냅니다.
                    params.add(new BasicNameValuePair("fd_id", String.valueOf(list.get(0).getId()))); //파라미터 이름, 보낼 데이터 순입니다.
                    params.add(new BasicNameValuePair("id", new Pre(context).getUser(context.getString(R.string.key_id))));
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
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    JSONArray jsonArray = jsonObject.getJSONArray("accept");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);
                        req = c.getInt("req");

                    }
                } catch (Exception e) {

                }
                if (isHeader(position)) {
                    if (list.get(0).getNicname().equals(new Pre(context).getUser(context.getString(R.string.key_nicname)))) {
                        holder.button_add.setVisibility(View.GONE);
                    } else {
                        holder.button_add.setVisibility(View.INVISIBLE);
                    }
                    return;
                } else if (position == 1) {

                    if (list.get(0).getNicname().equals(new Pre(context).getUser(context.getString(R.string.key_nicname)))) {
                        holder.button_add.setVisibility(View.GONE);
                    } else {
                        try {


                            holder.button_add.setVisibility(View.VISIBLE);
                            if (req == 0) {
                                holder.button_add.setImageResource(R.drawable.ic_person_add_black_18dp);
                                holder.button_add.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });

                            } else if (req == 1) {
                                holder.button_add.setImageResource(R.drawable.ic_arrow_forward_black_18dp);

                            } else {
                                holder.button_add.setImageResource(R.drawable.ic_person_black_18dp);
                            }

                        } catch (Exception e) {

                        }

                    }
                    return;
                } else {

                    holder.textView.setText(list.get(position - 2).getContent());
                    holder.textView.setTextColor(Color.BLACK);
                    holder.text_nicname.setText(list.get(position - 2).getNicname());
                    holder.text_nicname.setTextColor(Color.BLACK);
                    holder.text_Day.setText(list.get(position - 2).getDay());
                    Glide.with(context).load("http://133.130.88.202:8080/Images/imags/" + list.get(position - 2).getUri()).into(holder.imageView);
                    if (!list.get(position - 2).getIcon().equals("null"))
                        Glide.with(context).load("http://133.130.88.202:8080/Images/imags/" + list.get(position - 2).getIcon()).into(holder.imageView_profile);
                    else
                        holder.imageView_profile.setImageResource(R.drawable.profile);
                }
            }
        }).execute(0);


    }


    @Override
    public int getItemCount() {
        return list.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0)
            return 0;
        else if (position == 1)
            return ITEM_VIEW_TYPE_HEADER;
        else
            return ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void onPostExecute() {


    }


    public class HolderView extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private ImageButton button_add;
        private TextView textView;
        private TextView text_nicname;
        private TextView text_Day;
        private CircularImageView imageView_profile;

        public HolderView(View itemView, int viewType) {
            super(itemView);
            if (viewType == 0) {
                button_add = (ImageButton) itemView.findViewById(R.id.button_add);

            } else if (viewType == ITEM_VIEW_TYPE_HEADER) {
                button_add = (ImageButton) itemView.findViewById(R.id.button_add);
//                if (list.get(viewType).getNicname().equals(new Pre(context).getUser(context.getString(R.string.key_nicname)))) {
//                    button_add.setVisibility(View.GONE);
//                } else {
//                    button_add.setVisibility(View.INVISIBLE);
//                }
            } else {
                button_add = (ImageButton) itemView.findViewById(R.id.button_add);

                imageView = (ImageView) itemView.findViewById(R.id.imageView);
                textView = (TextView) itemView.findViewById(R.id.text_content);
                text_Day = (TextView) itemView.findViewById(R.id.text_day);
                text_nicname = (TextView) itemView.findViewById(R.id.text_nicname);
                imageView_profile = (CircularImageView) itemView.findViewById(R.id.image_profile);
                imageView_profile.setBorderWidth(10);
                imageView_profile.addShadow();
            }


        }


    }

    public boolean isHeader(int position) {
        return position == 0;
    }
}
