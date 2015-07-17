package com.cars.manager.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.cars.manager.views.widgts.progressHudView.ProgressHUD;

public class BaseFragment extends Fragment {

	protected ProgressHUD mProgressHUD;

	/**
	 * 显示ProgressHUD
	 * */
	protected void showProgressHUD(Context context, String showMessage) {
		if (((Activity) context).isFinishing())
			return;
		mProgressHUD = ProgressHUD.show(context, showMessage, true, null);
	}

	/**
	 * 显示ProgressHUD
	 * */
	protected void showProgressHUD(Context context, String showMessage, boolean canCancled) {
		if (((Activity) context).isFinishing())
			return;
		mProgressHUD = ProgressHUD.show(context, showMessage, canCancled, null);
	}

	/**
	 * 隐藏ProgressHUD
	 * */
	protected void dismissProgressHUD() {
		try {
			if (mProgressHUD != null)
				mProgressHUD.dismiss();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
