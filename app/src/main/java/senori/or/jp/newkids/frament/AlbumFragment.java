package senori.or.jp.newkids.frament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import senori.or.jp.newkids.R;

public class AlbumFragment extends Fragment {


    private ArrayList<Integer> array_image = new ArrayList<Integer>();


    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);

        MyAdapter adapter = new MyAdapter();
        gridView.setAdapter(adapter);


        return view;
    }


    class MyAdapter extends BaseAdapter {

        public MyAdapter() {

        }


        @Override
        public int getCount() {
            return array_image.size();
        }

        @Override
        public Object getItem(int position) {
            return array_image.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.album_gridview_item, parent, false);

            }
            ImageView iv = (ImageView) convertView.findViewById(R.id.imageView);
            iv.setImageResource(array_image.get(position));

            return convertView;
        }
    }


}
