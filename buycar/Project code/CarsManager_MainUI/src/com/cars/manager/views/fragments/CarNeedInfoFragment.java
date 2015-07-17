package com.cars.manager.views.fragments;

import java.util.ArrayList;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.cars.manager.activities.PublishCarActivity;
import com.cars.manager.db.chexingdb.SerachService;
import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.db.table.CarResource;
import com.cars.manager.db.table.Chexi;
import com.cars.manager.db.table.Chexing;
import com.cars.manager.db.table.Gongyingshang;
import com.cars.manager.db.table.Pinpais;
import com.cars.manager.db.table.Tabs;
import com.cars.manager.db.table.UpdateCarInfo;
import com.cars.manager.db.table.Yanse;
import com.cars.manager.managers.UserInfoManager;
import com.cars.manager.views.popupwindows.CarTypeBuilder;
import com.cars.manager.views.popupwindows.CarTypeBuilder.OnPinpaiSelectedListener;
import com.cars.manager.views.popupwindows.ChexiBuilder;
import com.cars.manager.views.popupwindows.ChexiBuilder.OnChexiSelectedListener;
import com.cars.manager.views.popupwindows.ChexingBuilder;
import com.cars.manager.views.popupwindows.ChexingBuilder.OnChexingSelectedListener;
import com.cars.manager.views.popupwindows.ColorBuilder;
import com.cars.manager.views.popupwindows.ColorBuilder.OnYanseSelectedListener;
import com.cars.manager.views.popupwindows.EditableInfoBuilder;
import com.cars.manager.views.popupwindows.GongyingshangBuilder;
import com.cars.manager.views.popupwindows.GongyingshangBuilder.OnGYSSelectedListener;
import com.cars.manager.views.popupwindows.Tabuilder;
import com.cars.manager.views.popupwindows.Tabuilder.OnClickSureCallback;
import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;
import com.standard.kit.format.DateTimeUtil;
import com.standard.kit.text.TextUtil;

public class CarNeedInfoFragment extends Fragment implements OnClickListener, OnPinpaiSelectedListener, OnChexiSelectedListener, OnChexingSelectedListener,
		OnGYSSelectedListener, OnYanseSelectedListener, OnDateSetListener, OnClickSureCallback {

	PublishCarActivity mActivity;

	TextView mVin, mPrice, mTvGYS, mTvPinpai, mTvChexi, mTvChexing, mTvSCSP, mTvXSLC, mTvYanse, mTvYongtu;

	Pinpais mSelectedPinpais = null;
	Chexi mSelectedChexi = null;
	Chexing mSelectedChexing = null;
	String mVIN = "";
	String mBianhao = "";
	Gongyingshang mSelectedGYS = null;
	Yanse mSelectedYanse = null;
	ArrayList<Tabs> mSelectedTabs = new ArrayList<Tabs>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_publishcar_needsinfo_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mActivity = (PublishCarActivity) getActivity();

		initUI();

		updateData();

	}

	private void initUI() {
		mVin = (TextView) getView().findViewById(R.id.edt_vin);
		mPrice = (TextView) getView().findViewById(R.id.edt_price);
		mTvGYS = (TextView) getView().findViewById(R.id.edt_gys);
		mTvPinpai = (TextView) getView().findViewById(R.id.edt_pinpai);
		mTvChexi = (TextView) getView().findViewById(R.id.edt_chexi);
		mTvChexing = (TextView) getView().findViewById(R.id.edt_chexing);
		mTvSCSP = (TextView) getView().findViewById(R.id.edt_scsp);
		mTvXSLC = (TextView) getView().findViewById(R.id.edt_xslc);
		mTvYanse = (TextView) getView().findViewById(R.id.edt_yanse);
		mTvYongtu = (TextView) getView().findViewById(R.id.edt_yongtu);

		mVin.setOnClickListener(this);
		mPrice.setOnClickListener(this);
		mTvGYS.setOnClickListener(this);
		mTvPinpai.setOnClickListener(this);
		mTvChexi.setOnClickListener(this);
		mTvChexing.setOnClickListener(this);
		mTvSCSP.setOnClickListener(this);
		mTvXSLC.setOnClickListener(this);
		mTvYanse.setOnClickListener(this);
		mTvYongtu.setOnClickListener(this);

		/* 如果是商户身份直接显示 */
		if (UserInfoManager.isBusiness(mActivity)) {
			mTvGYS.setText(new DBHelper(mActivity).selectGongyingshangWithId(UserInfoManager.getUuid(mActivity)).getName());
			mTvGYS.setClickable(false);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edt_vin:
			final EditableInfoBuilder aBuilder = new EditableInfoBuilder(mActivity, getView());
			aBuilder.setTitle(mActivity.getString(R.string.vinma)).setLeftBtText(mActivity.getString(R.string.quxiao)).setMessageNoChinese()
					.setMessage(getTextContent(mVin)).setRightBtText(mActivity.getString(R.string.queding)).setRightBtClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							mVin.setText(aBuilder.getMessage(false));
						}
					}).setLeftBtClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							aBuilder.dismiss();
						}
					}).show();
			break;
		case R.id.edt_price:
			final EditableInfoBuilder priceBuilder = new EditableInfoBuilder(mActivity, getView());
			priceBuilder.setTitle(mActivity.getString(R.string.price) + "(万元)").setLeftBtText(mActivity.getString(R.string.quxiao)).setMessageDigitalOnly()
					.setMessage(getTextContent(mPrice)).setRightBtText(mActivity.getString(R.string.queding)).setRightBtClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							mPrice.setText(priceBuilder.getMessage(false));
						}
					}).setLeftBtClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							priceBuilder.dismiss();
						}
					}).show();
			break;
		case R.id.edt_gys:
			new GongyingshangBuilder(mActivity, getView()).setTitle(mActivity.getString(R.string.gongyingshang)).setOnGYSSelectedListener(this).show();
			break;
		case R.id.edt_pinpai:
			new CarTypeBuilder(mActivity, getView()).setTitle(mActivity.getString(R.string.pinpai)).setmOnPinpaiSelectedListener(this).show();
			break;
		case R.id.edt_chexi:
			if (mSelectedPinpais == null) {
				SuperToast.show(mActivity.getString(R.string.xuanpinpai), mActivity);
				return;
			} else {
				new ChexiBuilder(mActivity, getView(), mSelectedPinpais.getId()).setTitle(mActivity.getString(R.string.chexi))
						.setmOnChexiSelectedListener(this).show();
			}
			break;
		case R.id.edt_chexing:
			if (null == mSelectedChexi || TextUtil.isEmpty(mSelectedChexi.getId())) {
				SuperToast.show(mActivity.getString(R.string.xuanchexi), mActivity);
				return;
			} else {
				new ChexingBuilder(mActivity, getView(), mSelectedChexi.getId()).setTitle(mActivity.getString(R.string.chexi))
						.setmOnChexingSelectedListener(this).show();
			}
			break;
		case R.id.edt_scsp:
			TimePickerFragment timePicker = new TimePickerFragment(this);
			timePicker.show(getFragmentManager(), "");
			break;
		case R.id.edt_xslc:
			final EditableInfoBuilder aXslcBuilder = new EditableInfoBuilder(mActivity, getView());
			aXslcBuilder.setTitle(mActivity.getString(R.string.xslc) + "(万公里)").setLeftBtText(mActivity.getString(R.string.quxiao)).setMessageDigitalOnly()
					.setMessage(getTextContent(mTvXSLC)).setRightBtText(mActivity.getString(R.string.queding)).setRightBtClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							try {
								float num = Float.parseFloat(aXslcBuilder.getMessage(false).toString());

								if (num < 0 || num > 100) {
									SuperToast.show("行驶里程只能介于0到100公里之间", mActivity);
									return;
								}

								mTvXSLC.setText(aXslcBuilder.getMessage(false));
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}).setLeftBtClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							aXslcBuilder.dismiss();
						}
					}).show();
			break;
		case R.id.edt_yanse:
			new ColorBuilder(mActivity, getView()).setTitle(mActivity.getString(R.string.yanse)).setOnYanseSelectedListener(this).show();
			break;
		case R.id.edt_yongtu:
			new Tabuilder(mActivity, getView(), mSelectedTabs).setTitle(mActivity.getString(R.string.yongtu)).setmOnClickSureCallback(this).show();
			break;

		default:
			break;
		}
	}

	public void updateData() {
		UpdateCarInfo aResource = mActivity.mForUpLoadCarInfo;

		if (aResource == null) {
			return;
		}

		mVin.setText(aResource.getVin());
		mTvSCSP.setText(aResource.getFirstSignDate());
		mTvXSLC.setText(aResource.getTravlledDistance());
		mPrice.setText(aResource.getJizhiPrice());

		// 供应商
		if (!TextUtil.isEmpty(aResource.getSupplierId())) {
			Gongyingshang aGYS = new DBHelper(mActivity).selectGongyingshangWithId(aResource.getSupplierId());
			mSelectedGYS = aGYS;
			if (aGYS != null) {
				mTvGYS.setText(aGYS.getName());
			}

		}

		// 品牌
		if (!TextUtil.isEmpty(aResource.getBrandId())) {
			Pinpais aPinpais = new DBHelper(mActivity).selectPinpaisiWithId(aResource.getBrandId());
			mSelectedPinpais = aPinpais;
			if (aPinpais != null) {
				mTvPinpai.setText(aPinpais.getName());
			}
		}

		// 车系
		if (!TextUtil.isEmpty(aResource.getSeriesId())) {
			Chexi aChexi = new SerachService(mActivity).getChexisWithId(aResource.getSeriesId());
			mSelectedChexi = aChexi;
			if (aChexi != null) {
				mTvChexi.setText(aChexi.getName());
			}
		}

		// 车型
		if (!TextUtil.isEmpty(aResource.getTypeId())) {
			Chexing aChexing = new SerachService(mActivity).getChexingsWithId(aResource.getTypeId());
			mSelectedChexing = aChexing;
			if (aChexing != null) {
				mTvChexing.setText(aChexing.getName());
			}
		}

		// 颜色
		if (!TextUtil.isEmpty(aResource.getColor())) {
			Yanse aYanse = new DBHelper(mActivity).selectYanseWithId(aResource.getColor());
			mSelectedYanse = aYanse;
			if (aYanse != null) {
				mTvYanse.setText(aYanse.getColorName());
			}
		}

		// 用途
		if (!TextUtil.isEmpty(aResource.getTabId())) {
			mSelectedTabs = new DBHelper(mActivity).selectTabslistWithId(aResource.getTabId());
			updateSelectedText();
		}
	}

	@Override
	public void onSelected(Pinpais aPinpai) {
		mSelectedPinpais = aPinpai;
		if (mSelectedPinpais.getName().equals(mActivity.getString(R.string.pinpai))) {
			mTvPinpai.setText("");
		} else {
			mTvPinpai.setText(mSelectedPinpais.getName());
		}
		mTvChexi.setText("");
		mTvChexing.setText("");
	}

	@Override
	public void onSelected(Chexi aChexi) {
		mSelectedChexi = aChexi;
		if (aChexi.getName().equals("无")) {
			mTvChexi.setText("");
		} else {
			mTvChexi.setText(mSelectedChexi.getName());
		}
		mTvChexing.setText("");
	}

	@Override
	public void onSelected(Chexing aChexing) {
		mSelectedChexing = aChexing;
		if (aChexing.getName().equals("无")) {
			mTvChexing.setText("");
		} else {
			mTvChexing.setText(mSelectedChexing.getName());
		}
	}

	@Override
	public void onSelected(Gongyingshang aGongyingshang) {
		mSelectedGYS = aGongyingshang;
		mTvGYS.setText(mSelectedGYS.getName());
	}

	@Override
	public void onSelected(Yanse aYanse) {
		mSelectedYanse = aYanse;
		mTvYanse.setText(mSelectedYanse.getColorName());
	}

	public CarResource getCarResource() {

		CarResource aCarResource = new CarResource();
		aCarResource.setAddDate(DateTimeUtil.getCurrentTime(DateTimeUtil.PATTERN_BIRTHDAY));
		aCarResource.setBrandName(getTextContent(mTvPinpai));
		aCarResource.setVin(getTextContent(mVin));
		aCarResource.setSupplierName(getTextContent(mTvGYS));
		aCarResource.setSeriesName(getTextContent(mTvChexi));
		aCarResource.setTypeName(getTextContent(mTvChexing));
		aCarResource.setFirstSignDate(getTextContent(mTvSCSP));
		aCarResource.setTravlledDistance(getTextContent(mTvXSLC));
		aCarResource.setCarColor(getTextContent(mTvYanse));

		return aCarResource;
	}

	public void setmActivity(PublishCarActivity mActivity) {
		this.mActivity = mActivity;
	}

	private String getTextContent(TextView aTextView) {
		return aTextView.getText().toString().trim();
	}

	/* 保存数据 */
	public UpdateCarInfo saveCarInfo(UpdateCarInfo aUpdateCarInfo) {

		aUpdateCarInfo.setBrandId(mSelectedPinpais == null ? "" : mSelectedPinpais.getId());
		aUpdateCarInfo.setBrandName(mSelectedPinpais == null ? "" : mSelectedPinpais.getName());

		aUpdateCarInfo.setColor(mSelectedYanse == null ? "" : mSelectedYanse.getColor());
		aUpdateCarInfo.setColorName(mSelectedYanse == null ? "" : mSelectedYanse.getColorName());

		aUpdateCarInfo.setSeriesId(mSelectedChexi == null ? "" : mSelectedChexi.getId());
		aUpdateCarInfo.setSeriesName(mSelectedChexi == null ? "" : mSelectedChexi.getName());

		aUpdateCarInfo.setSupplierId(mSelectedGYS == null ? "" : mSelectedGYS.getId());
		aUpdateCarInfo.setSupplierName(mSelectedGYS == null ? "" : mSelectedGYS.getName());

		aUpdateCarInfo.setTypeId(mSelectedChexing == null ? "" : mSelectedChexing.getId());
		aUpdateCarInfo.setTypeName(mSelectedChexing == null ? "" : mSelectedChexing.getName());

		aUpdateCarInfo.setTabId(getSelectedTabId());

		aUpdateCarInfo.setTravlledDistance(getTextContent(mTvXSLC));
		aUpdateCarInfo.setFirstSignDate(getTextContent(mTvSCSP));
		aUpdateCarInfo.setVin(getTextContent(mVin));
		aUpdateCarInfo.setJizhiPrice(getTextContent(mPrice));
		aUpdateCarInfo.setManagerId(UserInfoManager.getUuidAndType(mActivity));

		if (UserInfoManager.isBusiness(mActivity))
			aUpdateCarInfo.setSupplierId(UserInfoManager.getUuid(mActivity));

		return aUpdateCarInfo;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		mTvSCSP.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
	}

	public int getWanzhengduCount() {
		int count = 0;

		if (mSelectedPinpais != null)
			count++;

		if (mSelectedYanse != null)
			count++;

		if (mSelectedChexi != null)
			count++;

		if (mSelectedGYS != null)
			count++;

		if (mSelectedChexing != null)
			count++;

		if (TextUtil.isEmpty(getTextContent(mTvXSLC)))
			count++;

		if (TextUtil.isEmpty(getTextContent(mTvSCSP)))
			count++;

		if (TextUtil.isEmpty(getTextContent(mVin)))
			count++;

		if (TextUtil.isEmpty(getTextContent(mPrice)))
			count++;

		return (count * 100) / 18;
	}

	@Override
	public void onClickSure(ArrayList<Tabs> selectedList) {
		mSelectedTabs.clear();
		mSelectedTabs.addAll(selectedList);
		updateSelectedText();
	}

	private void updateSelectedText() {

		String selectedTabName = "";

		for (int i = 0; i < mSelectedTabs.size(); i++) {
			if (mSelectedTabs.get(i).isSelected()) {
				selectedTabName = selectedTabName + mSelectedTabs.get(i).getTabname() + "、";
			}
		}

		if (TextUtil.isEmpty(selectedTabName)) {
			mTvYongtu.setText("");
		} else {
			mTvYongtu.setText(selectedTabName.substring(0, selectedTabName.length() - 1));
		}
	}

	private String getSelectedTabId() {
		String id = "";

		for (int i = 0; i < mSelectedTabs.size(); i++) {
			if (mSelectedTabs.get(i).isSelected()) {
				id = id + mSelectedTabs.get(i).getTabid() + ",";
			}
		}

		if (!TextUtil.isEmpty(id)) {
			id = id.substring(0, id.length() - 1);
		}

		return id;
	}

}
