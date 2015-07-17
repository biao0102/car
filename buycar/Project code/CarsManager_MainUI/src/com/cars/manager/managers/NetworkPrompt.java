package com.cars.manager.managers;

import java.util.List;

import android.content.Context;

import com.cars.manager.db.table.Bisnessse;
import com.cars.manager.db.table.PhotoInfo;
import com.cars.manager.db.table.UpdateCarInfo;
import com.cars.manager.networks.httpnetworks.DataCollection;
import com.cars.manager.networks.request.CarResourceListRequest;
import com.cars.manager.networks.request.DelBusinessRequest;
import com.cars.manager.networks.request.LogRequest;
import com.cars.manager.networks.request.LoginRequest;
import com.cars.manager.networks.request.NoCarUpdateRequest;
import com.cars.manager.networks.request.ShanghuRequest;
import com.cars.manager.networks.request.UpdateBusinessInfoRequest;
import com.cars.manager.networks.request.UpdateBusinessRequest;
import com.cars.manager.networks.request.UpdateCarResourceInfoRequest;
import com.cars.manager.networks.request.UpdateCarResourceRequest;
import com.cars.manager.networks.request.UpdateChexiRequest;
import com.cars.manager.networks.request.UpdateChexingRequest;
import com.cars.manager.networks.request.UpdateGYSRequest;
import com.cars.manager.networks.request.UpdateLocationRequest;
import com.cars.manager.networks.request.UpdateManagerRequest;
import com.cars.manager.networks.request.UpdatePinpaiRequest;
import com.cars.manager.networks.request.UpdateTabsRequest;
import com.cars.manager.networks.request.UpdateYanseRequest;
import com.cars.manager.networks.request.UpdateZhuangtaiRequest;
import com.cars.manager.networks.request.UploadChangedCarRequest;
import com.cars.manager.networks.request.UploadNewCarRequest;
import com.cars.manager.networks.request.UploadPhotosRequest;
import com.cars.manager.networks.response.CarResourceListResponse;
import com.cars.manager.networks.response.DelBusinessResponse;
import com.cars.manager.networks.response.LoginResponse;
import com.cars.manager.networks.response.NoCarUpdateResponse;
import com.cars.manager.networks.response.ShanghuResponse;
import com.cars.manager.networks.response.UpdateBusinessInfoResponse;
import com.cars.manager.networks.response.UpdateBusinessResponse;
import com.cars.manager.networks.response.UpdateCarResourceInfoResponse;
import com.cars.manager.networks.response.UpdateCarResourceResponse;
import com.cars.manager.networks.response.UpdateCarResponse;
import com.cars.manager.networks.response.UpdateChexiResponse;
import com.cars.manager.networks.response.UpdateChexingResponse;
import com.cars.manager.networks.response.UpdateGYSResponse;
import com.cars.manager.networks.response.UpdateLocationResponse;
import com.cars.manager.networks.response.UpdateManagerResponse;
import com.cars.manager.networks.response.UpdatePinPaiResponse;
import com.cars.manager.networks.response.UpdateTabsResponse;
import com.cars.manager.networks.response.UpdateYanseResponse;
import com.cars.manager.networks.response.UpdateZhuangtaiResponse;
import com.cars.manager.networks.response.UploadNewCarResponse;
import com.cars.manager.networks.response.UploadPhotosResponse;
import com.standard.kit.protocolbase.NetDataCallBack;
import com.standard.kit.protocolbase.NetDataEngine;
import com.standard.kit.protocolbase.TempNetDataEngine;

/**
 * @author chengyanfang
 * 
 *         处理各种联网请求
 */
public class NetworkPrompt {

	/**
	 * 1.请求登陆接口
	 * 
	 * @param userName
	 *            :用户名；password：密码
	 * */
	public static void requestLoginin(Context context, NetDataCallBack callBack, String userName, String password) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		LoginRequest request = new LoginRequest(mDataCollection);
		request.loginName = userName;
		request.password = password;

		request.setmUrl(UrlManager.LOGIN_URL);
		mDataEngine.setmRequest(request);

		LoginResponse response = new LoginResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 2.商户列表接口
	 * 
	 * @param sid
	 *            :商户ID<不必填> managerId:负责人名称<不必填> currentPage:当前页 limit：页数限制
	 * 
	 * */
	public static void requestShanghu(Context context, NetDataCallBack callBack, String sid, String managerId, String currentPage) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		ShanghuRequest request = new ShanghuRequest(mDataCollection);
		request.sid = sid;
		request.modifyEmp = UserInfoManager.getUuid(context);
		request.managerId = managerId;
		request.currentPage = currentPage;
		request.limit = Config.BUSINESS_REQUEST_LIMIT + "";

		request.setmUrl(UrlManager.SHANGHU_URL);
		mDataEngine.setmRequest(request);

		ShanghuResponse response = new ShanghuResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 3.更新品牌
	 * 
	 * @param timeStamp
	 *            上次更新时间<不填会返回所有>
	 * */
	public static void requestUpdatePinPai(Context context, NetDataCallBack callBack, String timeStamp) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UpdatePinpaiRequest request = new UpdatePinpaiRequest(mDataCollection);
		request.timeStamp = timeStamp;
		request.modifyEmp = UserInfoManager.getUuid(context);

		request.setmUrl(UrlManager.PINPAI_URL);
		mDataEngine.setmRequest(request);

		UpdatePinPaiResponse response = new UpdatePinPaiResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 4.删除商户
	 * 
	 * @param uid
	 *            :操作者的uid；sid:商户id
	 * */
	public static void requestDelBusiness(Context context, NetDataCallBack callBack, String uid, String sid) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		DelBusinessRequest request = new DelBusinessRequest(mDataCollection);
		request.modifyEmp = uid;
		request.sid = sid;

		request.setmUrl(UrlManager.DEL_BUSINESS_URL);
		mDataEngine.setmRequest(request);

		DelBusinessResponse response = new DelBusinessResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 5.更新商户，请求更多商户信息
	 * 
	 * @param sid
	 *            :商户id
	 * */
	public static void requestUpdateBusinessInfo(Context context, NetDataCallBack callBack, String sid) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UpdateBusinessInfoRequest request = new UpdateBusinessInfoRequest(mDataCollection);
		request.sid = sid;
		request.modifyEmp = UserInfoManager.getUuid(context);

		request.setmUrl(UrlManager.UPDATE_BUSINESS_INFO_URL);
		mDataEngine.setmRequest(request);

		UpdateBusinessInfoResponse response = new UpdateBusinessInfoResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 6.更新商户
	 * 
	 * @param Bisnuss
	 *            :商户
	 * */
	public static void requestUpdateBusines(Context context, NetDataCallBack callBack, Bisnessse aBisnessse) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UpdateBusinessRequest request = new UpdateBusinessRequest(mDataCollection);

		request.modifyEmp = UserInfoManager.getUuid(context);
		request.sid = aBisnessse.getSid();
		request.sname = aBisnessse.getSname();
		request.cellphone = aBisnessse.getCellphone();
		request.linkName = aBisnessse.getLinkMan();
		request.managerId = aBisnessse.getManagerId();
		request.address = aBisnessse.getAddress();
		request.remark = aBisnessse.getRemark();

		request.setmUrl(UrlManager.UPDATE_BUSINESS_URL);
		mDataEngine.setmRequest(request);

		UpdateBusinessResponse response = new UpdateBusinessResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 7.商户无车源更新
	 * 
	 * @param sid
	 *            :商户ID
	 * */
	public static void requestNoCarUpdate(Context context, NetDataCallBack callBack, String sid) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		NoCarUpdateRequest request = new NoCarUpdateRequest(mDataCollection);

		request.modifyEmp = UserInfoManager.getUuid(context);
		request.sid = sid;

		request.setmUrl(UrlManager.NOCAR_UPDATE_URL);
		mDataEngine.setmRequest(request);

		NoCarUpdateResponse response = new NoCarUpdateResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 8.更新车系
	 * 
	 * @param timeStamp
	 *            上次更新时间<不填会返回所有>
	 * */
	public static void requestUpdateChexi(Context context, NetDataCallBack callBack, String timeStamp) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UpdateChexiRequest request = new UpdateChexiRequest(mDataCollection);
		request.timeStamp = timeStamp;
		request.modifyEmp = UserInfoManager.getUuid(context);

		request.setmUrl(UrlManager.CHEXI_URL);
		mDataEngine.setmRequest(request);

		UpdateChexiResponse response = new UpdateChexiResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 9.更新车型
	 * 
	 * @param timeStamp
	 *            上次更新时间<不填会返回所有>
	 * */
	public static void requestUpdateChexing(Context context, NetDataCallBack callBack, String timeStamp) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UpdateChexingRequest request = new UpdateChexingRequest(mDataCollection);
		request.timeStamp = timeStamp;
		request.modifyEmp = UserInfoManager.getUuid(context);

		request.setmUrl(UrlManager.CHEXING_URL);
		mDataEngine.setmRequest(request);

		UpdateChexingResponse response = new UpdateChexingResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 10.请求状态
	 * 
	 * */
	public static void requestUpdateZhuangtai(Context context, NetDataCallBack callBack) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UpdateZhuangtaiRequest request = new UpdateZhuangtaiRequest(mDataCollection);
		request.modifyEmp = UserInfoManager.getUuid(context);

		request.setmUrl(UrlManager.ZHUANTTAI_URL);
		mDataEngine.setmRequest(request);

		UpdateZhuangtaiResponse response = new UpdateZhuangtaiResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 11.请求供应商
	 * 
	 * */
	public static void requestUpdateGYS(Context context, NetDataCallBack callBack, String timeStamp) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UpdateGYSRequest request = new UpdateGYSRequest(mDataCollection);
		request.modifyEmp = UserInfoManager.getUuid(context);
		request.timeStamp = timeStamp;

		request.setmUrl(UrlManager.GYS_URL);
		mDataEngine.setmRequest(request);

		UpdateGYSResponse response = new UpdateGYSResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 12.请求地域
	 * 
	 * */
	public static void requestUpdateLocation(Context context, NetDataCallBack callBack) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UpdateLocationRequest request = new UpdateLocationRequest(mDataCollection);
		request.modifyEmp = UserInfoManager.getUuid(context);

		request.setmUrl(UrlManager.LOCATION_URL);
		mDataEngine.setmRequest(request);

		UpdateLocationResponse response = new UpdateLocationResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 13.请求颜色
	 * 
	 * */
	public static void requestUpdateYanse(Context context, NetDataCallBack callBack) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UpdateYanseRequest request = new UpdateYanseRequest(mDataCollection);
		request.modifyEmp = UserInfoManager.getUuid(context);

		request.setmUrl(UrlManager.YANSE_URL);
		mDataEngine.setmRequest(request);

		UpdateYanseResponse response = new UpdateYanseResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 14.请求车辆列表
	 * 
	 * @param cityId
	 *            请求人所在城市ID
	 * 
	 * */
	public static void requestCarList(Context context, NetDataCallBack callBack, String page, String cityId, String brandId, String seriesId, String typeId,
			String status, String vin, String vehicleNo, String supplierId) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		CarResourceListRequest request = new CarResourceListRequest(mDataCollection);

		request.cityId = cityId;
		request.currentPage = page;
		request.brandId = brandId;
		request.seriesId = seriesId;
		request.typeId = typeId;
		request.vin = vin;
		request.status = status;
		request.vehicleNo = vehicleNo;
		request.supplierId = supplierId;

		request.modifyEmp = UserInfoManager.getUuid(context);

		request.setmUrl(UrlManager.CHEYUAN_URL);
		mDataEngine.setmRequest(request);

		CarResourceListResponse response = new CarResourceListResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 15.请求商户负责人
	 * */
	public static void requestUpdateManager(Context context, NetDataCallBack callBack) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UpdateManagerRequest request = new UpdateManagerRequest(mDataCollection);
		request.modifyEmp = UserInfoManager.getUuid(context);

		request.setmUrl(UrlManager.MANAGER_URL);
		mDataEngine.setmRequest(request);

		UpdateManagerResponse response = new UpdateManagerResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 16.更新车辆状态
	 * 
	 * 
	 * vehicleNo;// 车辆编号 modifyEmp;// 操作人 supplierId;// 商户编号 status;// 状态:状态
	 * saleStatus;// 销售状态 bargainPrice;// 成交价 bargainTime;// 成交时间
	 * */
	public static void requestUpdateCarResourceState(Context context, NetDataCallBack callBack, String vehicleNo, String modifyEmp, String supplierId,
			String status, String saleStatus, String bargainPrice, String bargainTime, String sellChannel) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UpdateCarResourceRequest request = new UpdateCarResourceRequest(mDataCollection);
		request.vehicleNo = vehicleNo;
		request.modifyEmp = modifyEmp;
		request.supplierId = supplierId;
		request.status = status;
		request.saleStatus = saleStatus;
		request.bargainPrice = bargainPrice;
		request.bargainPrice = bargainPrice;
		request.bargainTime = bargainTime;
		request.saleChannel = sellChannel;

		request.setmUrl(UrlManager.UPDATE_CARRESOURCE_URL);
		mDataEngine.setmRequest(request);

		UpdateCarResourceResponse response = new UpdateCarResourceResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 17.更新车辆时，获取车辆信息
	 * 
	 * @param vehicleNo
	 *            :车辆编号
	 * */
	public static void requestUpdateCarResourceInfo(Context context, NetDataCallBack callBack, String vehicleNo) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UpdateCarResourceInfoRequest request = new UpdateCarResourceInfoRequest(mDataCollection);
		request.vehicleNo = vehicleNo;
		request.modifyEmp = UserInfoManager.getUuid(context);

		request.setmUrl(UrlManager.UPDATE_CARRESOURCE_INFO_URL);
		mDataEngine.setmRequest(request);

		UpdateCarResourceInfoResponse response = new UpdateCarResourceInfoResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 18.上传车辆
	 * 
	 * @param UpdateCarInfo
	 *            ：车辆基本信息
	 * 
	 * @param List
	 *            <PhotoInfo>：车辆图片信息
	 * 
	 * */
	public static void requestUploadNewCar(Context context, NetDataCallBack callBack, UpdateCarInfo aUpdateCarInfo, List<PhotoInfo> mPhotoes) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UploadNewCarRequest request = new UploadNewCarRequest(mDataCollection);
		request.cityId = UserInfoManager.getCityId(context);
		request.modifyEmp = UserInfoManager.getUuid(context);
		request.mPhotoInfos = mPhotoes;
		request.mUpdateCarInfo = aUpdateCarInfo;

		request.setmUrl(UrlManager.UPLOAD_NEWCAR_URL);
		mDataEngine.setmRequest(request);

		UploadNewCarResponse response = new UploadNewCarResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 19.车辆修改保存
	 * 
	 * @param UpdateCarInfo
	 *            ：车辆基本信息
	 * 
	 * @param List
	 *            <PhotoInfo>：车辆图片信息
	 * 
	 * */
	public static void requestUploadUpdateCar(Context context, NetDataCallBack callBack, UpdateCarInfo aUpdateCarInfo, List<PhotoInfo> mPhotoes) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UploadChangedCarRequest request = new UploadChangedCarRequest(mDataCollection);
		request.modifyEmp = UserInfoManager.getUuid(context);
		request.mPhotoInfos = mPhotoes;
		request.mUpdateCarInfo = aUpdateCarInfo;

		request.setmUrl(UrlManager.UPLOAD_UPDATECAR_URL);
		mDataEngine.setmRequest(request);

		UpdateCarResponse response = new UpdateCarResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 20.分批上传车辆图片
	 * 
	 * @param mPhotoes
	 *            ：车辆基本信息
	 * 
	 * */
	public static void requestUploadPhotos(Context context, NetDataCallBack callBack, List<PhotoInfo> mPhotoes, String vehicleNo) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UploadPhotosRequest request = new UploadPhotosRequest(mDataCollection);
		request.vehicleNo = vehicleNo;
		request.mPhotoInfos = mPhotoes;

		request.setmUrl(UrlManager.UPLOAD_CARPHOTOES_URL);
		mDataEngine.setmRequest(request);

		UploadPhotosResponse response = new UploadPhotosResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 21.请求用途列表
	 * 
	 * */
	public static void requestUpdateTabs(Context context, NetDataCallBack callBack) {
		NetDataEngine mDataEngine = new NetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		UpdateTabsRequest request = new UpdateTabsRequest(mDataCollection);
		request.modifyEmp = UserInfoManager.getUuid(context);

		request.setmUrl(UrlManager.YONGTU_URL);
		mDataEngine.setmRequest(request);

		UpdateTabsResponse response = new UpdateTabsResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}

	/**
	 * 22.日志纪录
	 * 
	 * */
	public static void requestLogRecord(Context context, NetDataCallBack callBack, String cnt, String type) {
		TempNetDataEngine mDataEngine = new TempNetDataEngine(callBack, context);
		DataCollection mDataCollection = new DataCollection(context);

		LogRequest request = new LogRequest(mDataCollection);
		request.userId = UserInfoManager.getUuid(context);
		request.count = cnt;
		request.type = type;

		request.setmUrl(UrlManager.LOG_RECORD);
		mDataEngine.setmRequest(request);

		UploadNewCarResponse response = new UploadNewCarResponse(mDataCollection);
		mDataEngine.setmResponse(response);

		mDataEngine.connection();
	}
}
