package senori.or.jp.sharering.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import senori.or.jp.sharering.R;
import senori.or.jp.sharering.activity.UserActivity;
import senori.or.jp.sharering.data.PhotoData;


/**
 * Created by JupiteR on 2016-04-11.
 */
public class SNSAdapter extends RecyclerView.Adapter<SNSAdapter.HolderView> {
    private Context context;
    private ArrayList<PhotoData> list;
    private int position;

    public SNSAdapter(Context context, ArrayList<PhotoData> list) {

        this.list = list;
        this.context = context;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sns_item, parent, false);
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(HolderView holder, final int position) {
        this.position = position;
        // holder.imageSwitcher.setImageResource(R.drawable.cloudqw);
        holder.textView.setText(list.get(position).getContent());
        holder.text_nicname.setText(list.get(position).getNicname());
        holder.text_day.setText(list.get(position).getDay());

        Glide.with(context).load("http://133.130.88.202:8080/Images/imags/" + list.get(position).getUrl()).into(holder.imageView);
        if (!list.get(position).getIcon().equals("null"))
            Glide.with(context).load("http://133.130.88.202:8080/Images/imags/" + list.get(position).getIcon()).into(holder.imageView_profile);
        else
            holder.imageView_profile.setImageResource(R.drawable.profile);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra("nicname", list.get(position).getNicname());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private TextView text_nicname;
        private TextView text_day;
        private CircularImageView imageView_profile;
        private LinearLayout layout;

        public HolderView(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.layout_user);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.text_content);
            text_nicname = (TextView) itemView.findViewById(R.id.text_nicname);
            text_day = (TextView) itemView.findViewById(R.id.text_day);
            imageView_profile = (CircularImageView) itemView.findViewById(R.id.image_profile);
            imageView_profile.setBorderWidth(10);
            imageView_profile.addShadow();
        }
    }
}
