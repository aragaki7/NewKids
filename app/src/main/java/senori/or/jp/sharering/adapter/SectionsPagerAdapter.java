package senori.or.jp.sharering.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import senori.or.jp.sharering.frament.NoticeBoardFragment;
import senori.or.jp.sharering.frament.SNSFragment;


/**
 * Created by JupiteR on 2016-02-16.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);


    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;


        switch (position) {

            case 0:

                fragment = new SNSFragment();

                break;
            case 1:
                fragment = new NoticeBoardFragment();
                break;
//
//            case 2:
//                fragment = new MyFragment();
//                break;
//
//            case 3:
//                fragment = new SuggestsFragment();
//
//                break;
//
//            case 4:
//                fragment = new NoticeBoardFragment();
//                break;
        }
        return fragment;
    }


    @Override
    public Object instantiateItem(View container, int position) {

        return getItem(position);
    }


    @Override
    public int getCount() {
        return 2;
    }


}
