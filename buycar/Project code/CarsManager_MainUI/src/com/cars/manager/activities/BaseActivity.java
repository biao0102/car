package com.cars.manager.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.cars.manager.networks.AsyncHttpClient;
import com.cars.manager.utils.HandlerTypeUtils;
import com.cars.manager.views.widgts.progressHudView.ProgressHUD;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.manager.views.widgts.swipeLayout.SwipeBackActivityBase;
import com.cars.manager.views.widgts.swipeLayout.SwipeBackActivityHelper;
import com.cars.manager.views.widgts.swipeLayout.SwipeBackLayout;
import com.cars.manager.views.widgts.swipeLayout.Utils;
import com.cars.managers.R;

@SuppressLint("HandlerLeak")
public class BaseActivity extends SherlockFragmentActivity implements SwipeBackActivityBase {

	protected AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();

	protected Handler mHandler;

	public Handler mThreadHandler;

	protected ProgressHUD mProgressHUD;

	protected boolean mIsFirstAnim = false;

	private SwipeBackActivityHelper mHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initHandler();

		initSwipeHelper();

		initHandlerThread();

		getSupportActionBar().hide();
	}

	protected void initUI() {
		initTitle();
	}

	protected void initTitle() {

	}

	protected void initData() {

	}

	private void initHandlerThread() {
		MyHandlerThread aHandlerThread = new MyHandlerThread("net-request");
		mThreadHandler = new Handler(aHandlerThread.getLooper(), aHandlerThread);
	}

	private void initHandler() {
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				parserMessage(msg);
			}
		};
	}

	/**
	 * 解析处理message
	 * */
	protected void parserMessage(Message msg) {
		if (msg == null)
			return;
		String tips = (String) msg.obj;
		switch (msg.what) {
		case HandlerTypeUtils.HANDLER_TYPE_TOAST_TIPS:
			dismissProgressHUD();
			SuperToast.show(tips, this);
			break;
		default:
			break;
		}
	}

	private void initSwipeHelper() {
		mHelper = new SwipeBackActivityHelper(this);

		mHelper.onActivityCreate();

		this.getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
	}

	/**
	 * 显示ProgressHUD
	 * */
	protected void showProgressHUD(Context context, String showMessage) {
		if (this.isFinishing())
			return;
		mProgressHUD = ProgressHUD.show(context, showMessage, true, null);
	}

	/**
	 * 显示ProgressHUD
	 * */
	protected void showProgressHUD(Context context, String showMessage, boolean canCancled) {
		if (this.isFinishing())
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

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		if (mIsFirstAnim) {
			inFromRightOutToLeft();
			mIsFirstAnim = false;
		} else {
			inFromLeftOutToRight();
		}
	}

	/**
	 * 设置切换动画，从右边进入，左边退出
	 */
	protected void inFromRightOutToLeft() {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

	/**
	 * 设置切换动画，从右边进入，左边退出
	 */
	private void inFromLeftOutToRight() {
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate();
	}

	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v == null && mHelper != null)
			return mHelper.findViewById(id);
		return v;
	}

	@Override
	public SwipeBackLayout getSwipeBackLayout() {
		return mHelper.getSwipeBackLayout();
	}

	/**
	 * 设置是否可右滑
	 */
	@Override
	public void setSwipeBackEnable(boolean enable) {
		getSwipeBackLayout().setEnableGesture(enable);
	}

	@Override
	public void scrollToFinishActivity() {
		Utils.convertActivityToTranslucent(this);
		getSwipeBackLayout().scrollToFinishActivity();
	}

	protected class MyHandlerThread extends HandlerThread implements android.os.Handler.Callback {

		public MyHandlerThread(String name) {
			super(name);
			start();
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			parserMessage(msg);
			return true;
		}

	}

}
