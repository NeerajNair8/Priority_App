package com.example.priority_app.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

//import com.example.priority_app.InboxFragment1;
import com.example.priority_app.InboxFragment2;
import com.example.priority_app.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            /*case 0:
                InboxFragment1 fg=new InboxFragment1();
                return fg;*/
            case 1:
                InboxFragment2 fg1=new InboxFragment2();
                return fg1;
                default:
                    return null;
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //return mContext.getResources().getString(TAB_TITLES[position]);
        switch (position)
        {
            case 0:
                return "PRODUCIVITY MODE";
            case 1:
                return "SLEEP MODE";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}