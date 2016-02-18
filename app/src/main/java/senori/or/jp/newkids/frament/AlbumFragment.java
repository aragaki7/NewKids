package senori.or.jp.newkids.frament;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
=======
import android.widget.GridView;
>>>>>>> 2d755dfb91a7d94d418fb2082e8fc43afc14585d

import senori.or.jp.newkids.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlbumFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Integer> array_image = new ArrayList<Integer>();


    private OnFragmentInteractionListener mListener;

    public AlbumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlbumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumFragment newInstance(String param1, String param2) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
<<<<<<< HEAD
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);

        array_image.add(R.drawable.cloudqw);
        array_image.add(R.drawable.cloudqw);
        array_image.add(R.drawable.cloudqw);
        array_image.add(R.drawable.cloudqw);
        array_image.add(R.drawable.cloudqw);
        array_image.add(R.drawable.cloudqw);
        array_image.add(R.drawable.cloudqw);
        array_image.add(R.drawable.cloudqw);
        array_image.add(R.drawable.cloudqw);
        array_image.add(R.drawable.cloudqw);

        MyAdapter adapter = new MyAdapter();
        gridView.setAdapter(adapter);
=======
        View view = inflater.inflate(R.layout.fragment_album, container, false);


>>>>>>> 2d755dfb91a7d94d418fb2082e8fc43afc14585d
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
                convertView= LayoutInflater.from(getActivity()).inflate(R.layout.album_gridview_item, parent, false);

            }
            ImageView iv = (ImageView) convertView.findViewById(R.id.imageView);
            iv.setImageResource(array_image.get(position));

            return convertView;
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
