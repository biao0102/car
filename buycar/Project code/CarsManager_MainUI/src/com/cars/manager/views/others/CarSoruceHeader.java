package com.cars.manager.views.others;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.db.table.Chexi;
import com.cars.manager.db.table.Chexing;
import com.cars.manager.db.table.Gongyingshang;
import com.cars.manager.db.table.Locations;
import com.cars.manager.db.table.Pinpais;
import com.cars.manager.db.table.Zhuangtais;
import com.cars.manager.managers.UserInfoManager;
import com.cars.manager.views.popupwindows.CarTypeBuilder;
import com.cars.manager.views.popupwindows.CarTypeBuilder.OnPinpaiSelectedListener;
import com.cars.manager.views.popupwindows.ChexiBuilder;
import com.cars.manager.views.popupwindows.ChexiBuilder.OnChexiSelectedListener;
import com.cars.manager.views.popupwindows.ChexingBuilder;
import com.cars.manager.views.popupwindows.ChexingBuilder.OnChexingSelectedListener;
import com.cars.manager.views.popupwindows.EditableInfoBuilder;
import com.cars.manager.views.popupwindows.GongyingshangBuilder;
import com.cars.manager.views.popupwindows.GongyingshangBuilder.OnGYSSelectedListener;
import com.cars.manager.views.popupwindows.LocationBuilder;
import com.cars.manager.views.popupwindows.LocationBuilder.OnLocationSelectedListener;
import com.cars.manager.views.popupwindows.ZhuangtaiBuilder;
import com.cars.manager.views.popupwindows.ZhuangtaiBuilder.OnZhuangtaiSelectedListener;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.text.TextUtil;

public class CarSoruceHeader implements OnClickListener, OnPinpaiSelectedListener, OnChexiSelectedListener, OnChexingSelectedListener,
		OnZhuangtaiSelectedListener, OnGYSSelectedListener, OnLocationSelectedListener {

	Context mContext;

	private View mView;

	TextView mTvPinpai;
	TextView mTvChexi;
	TextView mTvChexing;
	TextView mTvZhuangtai;
	TextView mTvVinma;
	TextView mTvBianhao;
	TextView mTvGongyingshang;
	TextView mTvDiyu;
	TextView mTvSousuo;

	Pinpais mSelectedPinpais = null;
	Chexi mSelectedChexi = null;
	Chexing mSelectedChexing = null;
	Zhuangtais mSelectedZhuangtais = null;
	String mVIN = "";
	String mBianhao = "";
	Gongyingshang mSelectedGYS = null;
	Locations mSelectedLocations = null;

	public CarSoruceHeader(Context aContext) {
		this.mContext = aContext;
	}

	public View getView() {
		if (mView == null) {
			LayoutInflater aLayoutInflater = LayoutInflater.from(mContext);
			this.mView = aLayoutInflater.inflate(R.layout.view_carsource_item_header_view, null);
		}

		initUI();

		return mView;
	}

	private void initUI() {
		if (mView == null)
			return;
		mTvPinpai = (TextView) mView.findViewById(R.id.text_1);
		mTvChexi = (TextView) mView.findViewById(R.id.text_2);
		mTvChexing = (TextView) mView.findViewById(R.id.text_3);
		mTvZhuangtai = (TextView) mView.findViewById(R.id.text_4);
		mTvVinma = (TextView) mView.findViewById(R.id.text_5);
		mTvBianhao = (TextView) mView.findViewById(R.id.text_6);
		mTvGongyingshang = (TextView) mView.findViewById(R.id.text_7);
		mTvDiyu = (TextView) mView.findViewById(R.id.text_8);
		mTvSousuo = (TextView) mView.findViewById(R.id.text_9);

		mTvPinpai.setOnClickListener(this);
		mTvChexi.setOnClickListener(this);
		mTvChexing.setOnClickListener(this);
		mTvZhuangtai.setOnClickListener(this);
		mTvVinma.setOnClickListener(this);
		mTvBianhao.setOnClickListener(this);
		mTvGongyingshang.setOnClickListener(this);
		mTvDiyu.setOnClickListener(this);
		mTvSousuo.setOnClickListener(this);

		/* 如果是商户身份直接显示 */
		if (UserInfoManager.isBusiness(mContext)) {
			mTvGongyingshang.setText(new DBHelper(mContext).selectGongyingshangWithId(UserInfoManager.getUuid(mContext)).getName());
			mTvDiyu.setText(new DBHelper(mContext).selectLocationsWithId(UserInfoManager.getCityId(mContext)).getCityName());
			mTvGongyingshang.setClickable(false);
			mTvDiyu.setClickable(false);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_1:// 品牌
			new CarTypeBuilder((Activity) mContext, mView).setTitle(mContext.getString(R.string.pinpai)).setmOnPinpaiSelectedListener(this).show();
			break;
		case R.id.text_2:// 车系
			if (null == mSelectedPinpais || TextUtil.isEmpty(mSelectedPinpais.getId())) {
				SuperToast.show(mContext.getString(R.string.xuanpinpai), mContext);
				return;
			} else {
				new ChexiBuilder((Activity) mContext, mView, mSelectedPinpais.getId()).setTitle(mContext.getString(R.string.chexi))
						.setmOnChexiSelectedListener(this).show();
			}
			break;
		case R.id.text_3:// 车型
			if (null == mSelectedChexi || TextUtil.isEmpty(mSelectedChexi.getId())) {
				SuperToast.show(mContext.getString(R.string.xuanchexi), mContext);
				return;
			} else {
				new ChexingBuilder((Activity) mContext, mView, mSelectedChexi.getId()).setTitle(mContext.getString(R.string.chexing))
						.setmOnChexingSelectedListener(this).show();
			}
			break;
		case R.id.text_4:// 状态
			new ZhuangtaiBuilder((Activity) mContext, mView).setTitle(mContext.getString(R.string.zhuangtai)).setmOnZhuangtaiSelectedListener(this).show();
			break;
		case R.id.text_5:// VIN码
			final EditableInfoBuilder aBuilder = new EditableInfoBuilder((Activity) mContext, mView);
			aBuilder.setTitle(mContext.getString(R.string.vinma)).setLeftBtText(mContext.getString(R.string.quxiao))
					.setMessage(mTvVinma.getText().toString().trim()).setRightBtText(mContext.getString(R.string.queding))
					.setRightBtClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							mTvVinma.setText(aBuilder.getMessage(true));
						}
					}).setLeftBtClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							mTvVinma.setText(mContext.getString(R.string.vinma));
							aBuilder.dismiss();
						}
					}).show();
			break;
		case R.id.text_6:// 车源编号
			final EditableInfoBuilder aCheyuanBuilder = new EditableInfoBuilder((Activity) mContext, mView);
			aCheyuanBuilder.setTitle(mContext.getString(R.string.cheyuanbianhao)).setLeftBtText(mContext.getString(R.string.quxiao))
					.setMessage(mTvBianhao.getText().toString().trim()).setRightBtText(mContext.getString(R.string.queding))
					.setRightBtClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							mTvBianhao.setText(aCheyuanBuilder.getMessage(true));
						}
					}).setLeftBtClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							mTvBianhao.setText(mContext.getString(R.string.cheyuanbianhao));
							aCheyuanBuilder.dismiss();
						}
					}).show();

			break;
		case R.id.text_7:// 供应商
			new GongyingshangBuilder((Activity) mContext, mView).setTitle(mContext.getString(R.string.gongyingshang)).setOnGYSSelectedListener(this).show();
			break;
		case R.id.text_8:// 地域
			new LocationBuilder((Activity) mContext, mView).setTitle(mContext.getString(R.string.diyu)).setOnLocationSelectedListener(this).show();
			break;
		case R.id.text_9:// 搜索
			onSearchBtClick();
			break;

		default:
			break;
		}
	}

	@Override
	public void onSelected(Pinpais aPinpai) {
		mSelectedPinpais = aPinpai;
		mTvPinpai.setText(mSelectedPinpais.getName());
		mSelectedChexi = null;
		mSelectedChexing = null;
		mTvChexi.setText(mContext.getString(R.string.chexi));
		mTvChexing.setText(mContext.getString(R.string.chexing));
	}

	@Override
	public void onSelected(Chexi aChexi) {
		mSelectedChexi = aChexi;
		mSelectedChexing = null;
		if (aChexi.getName().equals("无")) {
			mTvChexi.setText(mContext.getString(R.string.chexi));
		} else {
			mTvChexi.setText(mSelectedChexi.getName());
		}
		mTvChexing.setText(mContext.getString(R.string.chexing));
	}

	@Override
	public void onSelected(Chexing aChexing) {
		mSelectedChexing = aChexing;
		if (TextUtil.isEmpty(aChexing.getId())) {
			mTvChexing.setText(mContext.getString(R.string.chexing));
		} else {
			mTvChexing.setText(mSelectedChexing.getName());
		}
	}

	@Override
	public void onSelected(Zhuangtais aZhuangtais) {
		mSelectedZhuangtais = aZhuangtais;
		if (!TextUtil.isEmpty(mSelectedZhuangtais.getStatus())) {
			mTvZhuangtai.setText(mSelectedZhuangtais.getStatusValue());
		} else {
			mTvZhuangtai.setText(mContext.getString(R.string.zhuangtai));
		}
	}

	@Override
	public void onSelected(Gongyingshang aGongyingshang) {
		mSelectedGYS = aGongyingshang;
		if (!TextUtil.isEmpty(mSelectedGYS.getId())) {
			mTvGongyingshang.setText(mSelectedGYS.getName());
		} else {
			mTvGongyingshang.setText(mContext.getString(R.string.gongyingshang));
		}
	}

	@Override
	public void onSelected(Locations aLocations) {
		mSelectedLocations = aLocations;
		if (aLocations.getCityName().equals(mContext.getString(R.string.wu))) {
			mTvDiyu.setText(mContext.getString(R.string.diyu));
		} else {
			mTvDiyu.setText(mSelectedLocations.getCityName());
		}
	}

	private void onSearchBtClick() {
		if (mOnClickSearchBt != null) {
			String brandId = "";
			if (mSelectedPinpais != null) {
				brandId = mSelectedPinpais.getId();
			}

			String seriesId = "";
			if (mSelectedChexi != null) {
				seriesId = mSelectedChexi.getId();
			}

			String typeId = "";
			if (mSelectedChexing != null) {
				typeId = mSelectedChexing.getId();
			}

			String status = "";
			if (mSelectedZhuangtais != null) {
				status = mSelectedZhuangtais.getStatus();
			}

			String vin = "";
			if (!mContext.getString(R.string.vinma).equals(mTvVinma.getText().toString().trim())) {
				vin = mTvVinma.getText().toString().trim();
			}

			String bianhao = "";
			if (!mContext.getString(R.string.cheyuanbianhao).equals(mTvBianhao.getText().toString().trim())) {
				bianhao = mTvBianhao.getText().toString().trim();
			}

			String gysId = "";
			if (mSelectedGYS != null) {
				gysId = mSelectedGYS.getId();
			} else if (UserInfoManager.isBusiness(mContext)) {
				gysId = UserInfoManager.getUuid(mContext);
			}

			String cityId = "";
			if (mSelectedLocations != null) {
				cityId = mSelectedLocations.getCityId();
			} else {
				cityId = UserInfoManager.getCityId(mContext);
			}
			mOnClickSearchBt.onClickSearch(brandId, seriesId, typeId, status, vin, bianhao, gysId, cityId);
		}
	}

	public interface OnClickSearchBt {
		public void onClickSearch(String brandId, String seriesId, String typeId, String status, String vin, String vehicleNo, String supplierId, String cityId);
	}

	public OnClickSearchBt mOnClickSearchBt;

	public void setmOnClickSearchBt(OnClickSearchBt mOnClickSearchBt) {
		this.mOnClickSearchBt = mOnClickSearchBt;
	}

	/* 设置下架车辆的供应商名称和状态 */
	public void setXJCLName(String gysName, String gysId) {
		mTvGongyingshang.setText(gysName);
		mTvZhuangtai.setText("未售");

		mSelectedGYS = new Gongyingshang();
		mSelectedGYS.setId(gysId);
		mSelectedGYS.setName(gysName);

		mSelectedZhuangtais = new DBHelper(mContext).selectWeiShouZhuangtais();
	}
}