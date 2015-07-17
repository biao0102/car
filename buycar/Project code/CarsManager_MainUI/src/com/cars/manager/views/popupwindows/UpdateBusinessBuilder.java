package com.cars.manager.views.popupwindows;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.db.table.Bisnessse;
import com.cars.manager.db.table.Gongyingshang;
import com.cars.manager.db.table.Manager;
import com.cars.manager.utils.AppUtil;
import com.cars.manager.views.fragments.BusinessFragment;
import com.cars.manager.views.popupwindows.GongyingshangBuilder.OnGYSSelectedListener;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.text.TextUtil;

public class UpdateBusinessBuilder implements OnClickListener, OnGYSSelectedListener, OnDismissListener, OnItemClickListener {

	Activity mContext;

	View mView, view;

	Dialog alertDialog;

	Bisnessse mBusiness;

	EditText mSid;
	EditText mSName;
	EditText mTel;
	EditText mLxr;
	EditText mFzr;
	EditText mAddress;
	EditText mBeizhu;

	TextView mCancleText, mSaveText;

	Manager mSelectedManager = new Manager();

	BusinessFragment mBusinessFragment;
	BusinessTypePopupWindow mPopupWindow;

	List<Manager> types = new ArrayList<Manager>();

	public UpdateBusinessBuilder(Activity context, View view, Bisnessse aBusiness, BusinessFragment aBusinessFragment) {
		mContext = context;
		this.view = view;
		this.mBusiness = aBusiness;
		this.mBusinessFragment = aBusinessFragment;
		initUI();

		initData(mBusiness);
	}

	private void initUI() {
		mView = LayoutInflater.from(mContext).inflate(R.layout.view_del_business_popview, null);

		mSid = (EditText) mView.findViewById(R.id.tv_sid);
		mSName = (EditText) mView.findViewById(R.id.tv_sname);
		mTel = (EditText) mView.findViewById(R.id.tv_tel);
		mLxr = (EditText) mView.findViewById(R.id.tv_lianxiren);
		mFzr = (EditText) mView.findViewById(R.id.tv_fuzeren);
		mAddress = (EditText) mView.findViewById(R.id.tv_address);
		mBeizhu = (EditText) mView.findViewById(R.id.tv_beizhu);

		mCancleText = (TextView) mView.findViewById(R.id.tv_base_bottom_bar_left);
		mSaveText = (TextView) mView.findViewById(R.id.tv_base_bottom_bar_right);

		mCancleText.setOnClickListener(this);
		mSaveText.setOnClickListener(this);

		mCancleText.setText(mContext.getString(R.string.bottombar_quxiao));
		mSaveText.setText(mContext.getString(R.string.bottombar_baocun));

		mFzr.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (mPopupWindow == null) {
						mPopupWindow = new BusinessTypePopupWindow(mContext, mFzr, types, UpdateBusinessBuilder.this, UpdateBusinessBuilder.this);
					} else {
						mPopupWindow.showPopupWindow();
					}
					break;

				default:
					break;
				}
				return true;
			}
		});
	}

	public void initData(Bisnessse aBusiness) {
		if (aBusiness == null)
			return;

		// sid
		if (!TextUtil.isEmpty(aBusiness.getSid()) && mContext.getString(R.string.zanwu).equals(aBusiness.getSid())) {
			mSid.setHint(mContext.getString(R.string.zanwu));
		} else {
			mSid.setText(aBusiness.getSid());
		}

		// sname
		if (!TextUtil.isEmpty(aBusiness.getSname()) && mContext.getString(R.string.zanwu).equals(aBusiness.getSname())) {
			mSName.setHint(mContext.getString(R.string.zanwu));
		} else {
			mSName.setText(aBusiness.getSname());
		}

		// tel
		if (!TextUtil.isEmpty(aBusiness.getCellphone()) && mContext.getString(R.string.zanwu).equals(aBusiness.getCellphone())) {
			mTel.setHint(mContext.getString(R.string.zanwu));
		} else {
			mTel.setText(aBusiness.getCellphone());
		}

		// lxr
		if (!TextUtil.isEmpty(aBusiness.getLinkMan()) && mContext.getString(R.string.zanwu).equals(aBusiness.getLinkMan())) {
			mLxr.setHint(mContext.getString(R.string.zanwu));
		} else {
			mLxr.setText(aBusiness.getLinkMan());
		}

		// fzr
		if (!TextUtil.isEmpty(aBusiness.getChargeMan()) && mContext.getString(R.string.zanwu).equals(aBusiness.getChargeMan())) {
			mFzr.setHint(mContext.getString(R.string.zanwu));
		} else {
			mFzr.setText(aBusiness.getChargeMan());
		}

		// address
		if (!TextUtil.isEmpty(aBusiness.getAddress()) && mContext.getString(R.string.zanwu).equals(aBusiness.getAddress())) {
			mAddress.setHint(mContext.getString(R.string.zanwu));
		} else {
			mAddress.setText(aBusiness.getAddress());
		}

		// beizhu
		if (!TextUtil.isEmpty(aBusiness.getRemark()) && mContext.getString(R.string.zanwu).equals(aBusiness.getRemark())) {
			mBeizhu.setHint(mContext.getString(R.string.zanwu));
		} else {
			mBeizhu.setText(aBusiness.getRemark());
		}

		types = new DBHelper(mContext).selectManagerAll();
	}

	public void show() {
		alertDialog = new Dialog(mContext, R.style.shareDialog);

		alertDialog.setContentView(mView, new LayoutParams(AppUtil.getWidth(mContext) - AppUtil.dip2px(mContext, 20), LayoutParams.WRAP_CONTENT));

		alertDialog.getWindow().setGravity(Gravity.CENTER);

		alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.qhq_alert_transparent_bg);

		alertDialog.setCanceledOnTouchOutside(true);

		alertDialog.show();
	}

	public void dismiss() {
		if (alertDialog != null)
			alertDialog.dismiss();
	}

	/**
	 * EditText mSid; EditText mSName; EditText mTel; EditText mLxr; EditText
	 * mFzr; EditText mAddress; EditText mBeizhu;
	 * */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_base_bottom_bar_left:
			this.dismiss();
			break;
		case R.id.tv_base_bottom_bar_right:
			if (TextUtil.isEmpty(mSid.getText().toString().trim())) {
				SuperToast.show(mContext.getString(R.string.shbianhao_bitian), mContext);
				return;
			}
			if (TextUtil.isEmpty(mSName.getText().toString().trim())) {
				SuperToast.show(mContext.getString(R.string.shname_bitian), mContext);
				return;
			}
			if (TextUtil.isEmpty(mTel.getText().toString().trim())) {
				SuperToast.show(mContext.getString(R.string.shtel_bitian), mContext);
				return;
			}
			if (TextUtil.isEmpty(mLxr.getText().toString().trim())) {
				SuperToast.show(mContext.getString(R.string.shlxr_bitian), mContext);
				return;
			}
			if (TextUtil.isEmpty(mFzr.getText().toString().trim())) {
				SuperToast.show(mContext.getString(R.string.shzfzr_bitian), mContext);
				return;
			}
			mBusinessFragment.onClick(v);
			break;
		case R.id.tv_sname:
			break;
		default:
			break;
		}
		this.alertDialog.dismiss();
	}

	public Bisnessse getToSavedBisness() {
		Bisnessse aBisnessse = new Bisnessse();

		aBisnessse.setSid(mSid.getText().toString().trim());
		aBisnessse.setSname(mSName.getText().toString().trim());
		aBisnessse.setCellphone(mTel.getText().toString().trim());
		aBisnessse.setLinkMan(mLxr.getText().toString().trim());
		aBisnessse.setChargeMan(mFzr.getText().toString().trim());
		Manager aManager = new DBHelper(mContext).getManagerWithName(mFzr.getText().toString().trim());
		aBisnessse.setManagerId(aManager == null ? "" : aManager.getManagerId());
		aBisnessse.setAddress(mAddress.getText().toString().trim());
		aBisnessse.setRemark(mBeizhu.getText().toString().trim());

		return aBisnessse;
	}

	@Override
	public void onSelected(Gongyingshang aGongyingshang) {
		mSName.setText(aGongyingshang.getName());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mFzr.setText(types.get(position).getManagerName());
		mSelectedManager = types.get(position);
		if (mPopupWindow != null) {
			mPopupWindow.dismissPopupWindow();
		}
	}

	@Override
	public void onDismiss() {
		// TODO Auto-generated method stub

	}

}
