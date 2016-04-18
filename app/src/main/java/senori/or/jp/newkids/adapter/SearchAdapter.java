package senori.or.jp.newkids.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import senori.or.jp.newkids.R;
import senori.or.jp.newkids.data.SearchData;

/**
 * Created by JupiteR on 2016-04-16.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SearchData> list;

    public SearchAdapter(Context context, ArrayList<SearchData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text_nicname.setText(list.get(position).getNicname());
        Glide.with(context).load("http://133.130.88.202:8080/Images/imags/" + list.get(position).getIcon()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView text_nicname;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            text_nicname = (TextView) itemView.findViewById(R.id.text_nicname);
        }
    }
}
