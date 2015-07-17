package com.cars.manager.managers;

/**
 * 提供各种请求地址
 * */
public class UrlManagerTest {

	// 正式IP:118.26.166.173
	// 测试IP:192.168.0.129

	/* 测试地址 */
	private static String TEST_URL = "http://api.8783.com/forum/v1/forumindex/index.json?uuid=14700213";

	public static String getTestUrl() {
		return TEST_URL;
	}

	/* 1.登陆 */
	public static final String LOGIN_URL = "http://118.26.166.173:8099/appInterface_test/user/userlogin";

	/* 2.商户列表 */
	public static final String SHANGHU_URL = "http://118.26.166.173:8099/appInterface_test/supplier/supplierlist";

	/* 3.车源列表 */
	public static final String CHEYUAN_URL = "http://118.26.166.173:8099/appInterface_test/car/carlist";

	/* 4.品牌列表 */
	public static final String PINPAI_URL = "http://118.26.166.173:8099/appInterface_test/brand/brandlist";

	/* 5.车系列表 */
	public static final String CHEXI_URL = "http://118.26.166.173:8099/appInterface_test/series/serieslist";

	/* 6.车型列表 */
	public static final String CHEXING_URL = "http://118.26.166.173:8099/appInterface_test/type/typelist";

	/* 7.状态列表 */
	public static final String ZHUANTTAI_URL = "http://118.26.166.173:8099/appInterface_test/status/statuslist";

	/* 8.供应商列表 */
	public static final String MANAGER_URL = " http://118.26.166.173:8099/appInterface_test/supplier/managerlist";

	/* 9.供应商列表 */
	public static final String GYS_URL = "http://118.26.166.173:8099/appInterface_test/supplier/supplierList";

	/* 10.地域列表 */
	public static final String LOCATION_URL = "http://118.26.166.173:8099/appInterface_test/city/citylist";

	/* 11.颜色列表 */
	public static final String YANSE_URL = "http://118.26.166.173:8099/appInterface_test/color/colorlist";

	/* 12.删除商户 */
	public static final String DEL_BUSINESS_URL = "http://118.26.166.173:8099/appInterface_test/supplier/dodelete";

	/* 13.获取更新商户信息 */
	public static final String UPDATE_BUSINESS_INFO_URL = "http://118.26.166.173:8099/appInterface_test/supplier/toupdate";

	/* 14.更新商户 */
	public static final String UPDATE_BUSINESS_URL = "http://118.26.166.173:8099/appInterface_test/supplier/doupdate";

	/* 15.更新商户 */
	public static final String NOCAR_UPDATE_URL = "http://118.26.166.173:8099/appInterface_test/supplier/novehicleupdate";

	/* 16.更新车辆信息 */
	public static final String UPDATE_CARRESOURCE_URL = "http://118.26.166.173:8099/appInterface_test/status/updatestatus";

	/* 17.更新车辆时获取信息 */
	public static final String UPDATE_CARRESOURCE_INFO_URL = "http://118.26.166.173:8099/appInterface_test/car/toupdate";

	/* 18.上传新车辆 */
	public static final String UPLOAD_NEWCAR_URL = "http://118.26.166.173:8099/appInterface_test/car/createcar";

	/* 19.保存更新车辆 */
	public static final String UPLOAD_UPDATECAR_URL = "http://118.26.166.173:8099/appInterface_test/car/doupdate";

}
