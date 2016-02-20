package senori.or.jp.newkids.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import senori.or.jp.newkids.R;
import senori.or.jp.newkids.photoalbum.ScaleImageView;

/**
 * Created by JupiteR on 2016-02-19.
 */
public class StaggeredAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Integer> list;

    public StaggeredAdapter(Context context, ArrayList<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.staggered_item, parent, false);
        }

        ScaleImageView imageView = (ScaleImageView) convertView.findViewById(R.id.imageView);
        imageView.setImageResource(list.get(position));

        return convertView;
    }
}
