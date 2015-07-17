package com.cars.manager.activities;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cars.manager.managers.NetworkPrompt;
import com.cars.manager.managers.UserInfoManager;
import com.cars.manager.networks.response.LoginResponse;
import com.cars.manager.utils.ActivityStack;
import com.cars.manager.utils.IntentUtil;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.protocolbase.NetDataCallBack;
import com.standard.kit.protocolbase.ResponseData;
import com.standard.kit.protocolbase.ResponseErrorInfo;
import com.standard.kit.text.TextUtil;

public class LoginInActivity extends BaseTitleActivity implements NetDataCallBack {

	/* 登陆UI */
	/* 用户名 */
	EditText mUserNameEdt;
	/* 密码 */
	EditText mPasswordEdt;
	/* 输入框布局 */
	LinearLayout mEditLayout;
	/* 登录 */
	RelativeLayout mLoginIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_layout);
		setSwipeBackEnable(false);

		initUI();
	}

	@Override
	protected void initUI() {
		mUserNameEdt = (EditText) findViewById(R.id.edt_input_username);
		mPasswordEdt = (EditText) findViewById(R.id.edt_input_password);
		mLoginIn = (RelativeLayout) findViewById(R.id.rl_loginin_btlayout);
		mEditLayout = (LinearLayout) findViewById(R.id.fl_forum_login_edit_linearLayout);

		mLoginIn.setVisibility(View.VISIBLE);
		mEditLayout.setVisibility(View.VISIBLE);

		mLoginIn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_loginin_btlayout:
			onLoginInClick();
			break;
		default:
			break;
		}
	}

	private void onLoginInClick() {

		if (TextUtil.isEmpty(mUserNameEdt.getText().toString().trim())) {
			SuperToast.show(getString(R.string.shuru_username), this);
		} else if (TextUtil.isEmpty(mPasswordEdt.getText().toString().trim())) {
			SuperToast.show(getString(R.string.shuru_password), this);
		} else {
			showProgressHUD(this, getString(R.string.loading), false);
			NetworkPrompt.requestLoginin(this, this, mUserNameEdt.getText().toString().trim(), mPasswordEdt.getText().toString().trim());
		}

	}

	@Override
	public void onResult(ResponseData aData) {
		if (aData instanceof LoginResponse) {
			dismissProgressHUD();
			final LoginResponse aResponse = (LoginResponse) aData;
			UserInfoManager.setUuid(this, aResponse.userId);
			UserInfoManager.setCityId(this, aResponse.cityId);
			UserInfoManager.setType(this, aResponse.type);
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					SuperToast.show(aResponse.tips, LoginInActivity.this);
					ActivityStack.getActivityStack().pushActivity(LoginInActivity.this);
					IntentUtil.forwordToActivity(LoginInActivity.this, HomeActivity.class);
				}
			});
		}
	}

	@Override
	public void onError(final ResponseErrorInfo aErrorInfo) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				SuperToast.show(aErrorInfo.mErrorTips, LoginInActivity.this);
				dismissProgressHUD();
			}
		});
	}

	@Override
	public void onProtocolError(final ResponseData aData) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				SuperToast.show(aData.tips, LoginInActivity.this);
				dismissProgressHUD();
			}
		});
	}

	/**
	 * 退出应用
	 * */
	private long clickBackTime = 0;

	@SuppressWarnings("deprecation")
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if (clickBackTime == 0 || System.currentTimeMillis() - clickBackTime > 2000) {
				SuperToast.show(getString(R.string.exit_tips), this);
				clickBackTime = System.currentTimeMillis();
			} else {
				ActivityStack.getActivityStack().popAllActivityExceptOne(HomeActivity.class);
				if (Build.VERSION.SDK_INT < 8) {
					super.finish();
					System.exit(0);
					manager.restartPackage(getPackageName());
				} else {
					super.finish();
					System.exit(0);
				}
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

}
