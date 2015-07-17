package com.cars.manager.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cars.manager.activities.PublishCarActivity;
import com.cars.managers.R;

public class CarDescFragment extends Fragment {

	PublishCarActivity mActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_publishcar_desc_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mActivity = (PublishCarActivity) getActivity();

		initUI();

		initData();

	}

	private void initUI() {

	}

	private void initData() {

	}
}
