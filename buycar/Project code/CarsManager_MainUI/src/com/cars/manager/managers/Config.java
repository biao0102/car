package com.cars.manager.managers;

import com.cars.manager.utils.FileUtil;

public class Config {

	/* 文件存储路径 */
	public static final String SDCARD_PATH = FileUtil.getSavePath();

	/* 首页缓存存储路径 */
	public static final String HOMECACHEFILEPATH = SDCARD_PATH + "/Android/data/com.car.manager/cache/";
	public static final String HOMECACHEFILENAME = "homecache";

	/* 图片缓存存储路径 */
	public static final String IMAGE_PATH = SDCARD_PATH + "/Android/data/com.car.manager/cache/uil-images/";

	/* 商户页每次加载数目 */
	public static final int BUSINESS_REQUEST_LIMIT = 20;

	/* 未上传车辆来源－更新 */
	public static final String UPDATE = "update_source";

	/* 未上传车辆来源－新增 */
	public static final String NEW_ADD = "new_add_source";

	/* 请求MD5 key */
	public static final String MD5_KEY = "jizhicar";

}
