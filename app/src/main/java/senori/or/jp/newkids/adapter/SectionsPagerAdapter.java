package senori.or.jp.newkids.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import senori.or.jp.newkids.frament.NoticeBoardFragment;
import senori.or.jp.newkids.frament.SNSFragment;
import senori.or.jp.newkids.frament.MyFragment;

/**
 * Created by JupiteR on 2016-02-16.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private TabLayout tabLayout;

    public SectionsPagerAdapter(FragmentManager fm, Context context, TabLayout tabLayout) {
        super(fm);
        this.context = context;
        this.tabLayout = tabLayout;

    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;


        switch (position) {

            case 1:

                fragment = new SNSFragment();

                break;
            case 0:
                fragment = new NoticeBoardFragment();
                break;

            case 2:
                fragment = new MyFragment();
                break;
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
        return 3;
    }


}
