package senori.or.jp.newkids.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import senori.or.jp.newkids.R;
import senori.or.jp.newkids.data.Reply_Data;

/**
 * Created by JupiteR on 2016-04-06.
 */
public class ReplyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Reply_Data> list;

    public ReplyAdapter(Context context, ArrayList<Reply_Data> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.reply_item, parent, false);
        }

        TextView text_nicname = (TextView) convertView.findViewById(R.id.text_nicname);
        TextView text_content = (TextView) convertView.findViewById(R.id.text_content);
        TextView text_day = (TextView) convertView.findViewById(R.id.text_day);

        text_nicname.setText("└ " + list.get(position).getNicname());
        text_content.setText(list.get(position).getContent());
        text_day.setText(list.get(position).getDay().toString());

        return convertView;
    }
}
