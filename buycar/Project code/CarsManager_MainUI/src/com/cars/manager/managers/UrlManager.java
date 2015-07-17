package com.cars.manager.managers;

/**
 * 提供各种请求地址
 * */
public class UrlManager {

	// 正式IP:118.26.166.173
	// 测试IP:192.168.0.129

	/* 测试地址 */
	// private static String PUBLIC_URL =
	// "http://118.26.166.173:8099/appInterface_test";
	private static String PUBLIC_URL = "http://118.26.166.173:8088/appInterface";

	/* 1.登陆 */
	public static final String LOGIN_URL = PUBLIC_URL + "/user/userlogin";

	/* 2.商户列表 */
	public static final String SHANGHU_URL = PUBLIC_URL + "/supplier/supplierlist";

	/* 3.车源列表 */
	public static final String CHEYUAN_URL = PUBLIC_URL + "/car/carlist";

	/* 4.品牌列表 */
	public static final String PINPAI_URL = PUBLIC_URL + "/brand/brandlist";

	/* 5.车系列表 */
	public static final String CHEXI_URL = PUBLIC_URL + "/series/serieslist";

	/* 6.车型列表 */
	public static final String CHEXING_URL = PUBLIC_URL + "/type/typelist";

	/* 7.状态列表 */
	public static final String ZHUANTTAI_URL = PUBLIC_URL + "/status/statuslist";

	/* 8.供应商列表 */
	public static final String MANAGER_URL = PUBLIC_URL + "/supplier/managerlist";

	/* 9.供应商列表 */
	public static final String GYS_URL = PUBLIC_URL + "/supplier/supplierList";

	/* 10.地域列表 */
	public static final String LOCATION_URL = PUBLIC_URL + "/city/citylist";

	/* 11.颜色列表 */
	public static final String YANSE_URL = PUBLIC_URL + "/color/colorlist";

	/* 12.删除商户 */
	public static final String DEL_BUSINESS_URL = PUBLIC_URL + "/supplier/dodelete";

	/* 13.获取更新商户信息 */
	public static final String UPDATE_BUSINESS_INFO_URL = PUBLIC_URL + "/supplier/toupdate";

	/* 14.更新商户 */
	public static final String UPDATE_BUSINESS_URL = PUBLIC_URL + "/supplier/doupdate";

	/* 15.更新商户 */
	public static final String NOCAR_UPDATE_URL = PUBLIC_URL + "/supplier/novehicleupdate";

	/* 16.更新车辆信息 */
	public static final String UPDATE_CARRESOURCE_URL = PUBLIC_URL + "/status/updatestatus";

	/* 17.更新车辆时获取信息 */
	public static final String UPDATE_CARRESOURCE_INFO_URL = PUBLIC_URL + "/car/toupdate";

	/* 18.上传新车辆 */
	public static final String UPLOAD_NEWCAR_URL = PUBLIC_URL + "/car/createcar";

	/* 19.保存更新车辆 */
	public static final String UPLOAD_UPDATECAR_URL = PUBLIC_URL + "/car/doupdate";

	/* 20.上传车辆图片 */
	public static final String UPLOAD_CARPHOTOES_URL = PUBLIC_URL + "/image/upload";

	/* 21.获取用途列表 */
	public static final String YONGTU_URL = PUBLIC_URL + "/tabs/tabslist";

	/* 22.日志记录 */
	public static final String LOG_RECORD = "http://182.92.238.194/test/savelog";

}
