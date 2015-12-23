package com.shichai.www.choume.activity.chou;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.outsouring.crowdfunding.R;

/**
 * Created by HeJianjun on 2015/12/23.
 */
public class FragmentList extends Fragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, null);
        return rootView;
    }
}
