package com.shichai.www.choume.activity.sponsor.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author Hejianjun
 */
public abstract class BaseFragment extends Fragment {
	private View rootView;
	private Bundle savedInstanceState;



	protected abstract int getLayoutId();
	protected abstract void initViews(View rootView,Bundle savedInstanceState);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		if (rootView == null) {
			rootView = inflater.inflate(getLayoutId(), container, false);
			this.savedInstanceState = savedInstanceState;
			initViews(rootView,savedInstanceState);
		} else {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
		}
		return rootView;
	}



}
