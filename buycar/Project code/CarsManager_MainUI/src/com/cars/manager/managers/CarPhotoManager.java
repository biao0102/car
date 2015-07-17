package com.cars.manager.managers;

import java.util.ArrayList;

import com.cars.manager.db.table.PhotoInfo;

/**
 * 根据类型获取对应类型图片的地址
 * */
public class CarPhotoManager {
	// 右前45度
	public static final String YOU_QIAN_45 = "youqian45";

	// 车身正面
	public static final String ZHENG_QIAN_FANG = "zhengqianfang";

	// 左前45度
	public static final String ZUO_QIAN_FANG45 = "zuoqian45";

	// 正侧
	public static final String ZHEGNCE = "zhengce";

	// 前灯
	public static final String QIAN_DENG = "qiandeng";

	// 轮毂
	public static final String LUN_TAI = "luntai";

	// 前排座椅
	public static final String QIAN_PAI_ZUOYI = "qianpaizuoyi";

	// 中控
	public static final String ZHONG_KONG = "zhongkongtai";

	// 方向盘
	public static final String FANG_XIANG_PAN = "fangxiangpan";

	// 仪表盘
	public static final String YI_BIAO_PAN = "yibiaopan";

	// 档位
	public static final String DANG_WEI = "dangwei";

	// 后排座椅
	public static final String HOU_PAI_ZUOYI = "houpaizuoyi";

	// 正后方
	public static final String ZHENG_HOU = "zhenghoufang";

	// 右后45度
	public static final String YOU_HOU_45 = "youhou45";

	// HOU灯
	public static final String HOU_DENG = "houdeng";

	// 后备箱
	public static final String HOU_BEI_XIANG = "houbeixiang";

	// 驾驶位
	public static final String JIA_SHI_WEI = "jiashiwei";

	// 副驾
	public static final String FU_JIA = "fujia";

	// 左后45度
	public static final String ZUO_HOU_45 = "zuohou45";

	// 发动机整体
	public static final String FADONGJI_ZHENGTI = "fadongjizhengti";

	// 发动机细节
	public static final String FADONGJI_XIEJIE = "fadongjixijie";

	// 行驶证
	public static final String XING_SHI_ZHENG = "xingshizheng";

	// 亮点一
	public static final String LIANG_DIAN1 = "liangdian1";

	// 亮点二
	public static final String LIANG_DIAN2 = "liangdian2";

	// 亮点3
	public static final String LIANG_DIAN3 = "liangdian3";

	// 亮点4
	public static final String LIANG_DIAN4 = "liangdian4";

	// 亮点5
	public static final String LIANG_DIAN5 = "liangdian5";

	public static final ArrayList<String> photoTypes = new ArrayList<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7335110930517682900L;

		{
			add(YOU_QIAN_45);
			add(ZHENG_QIAN_FANG);
			add(ZUO_QIAN_FANG45);
			add(ZHEGNCE);
			add(QIAN_DENG);
			add(LUN_TAI);
			add(QIAN_PAI_ZUOYI);
			add(ZHONG_KONG);
			add(FANG_XIANG_PAN);
			add(YI_BIAO_PAN);
			add(DANG_WEI);
			add(HOU_PAI_ZUOYI);
			add(ZHENG_HOU);
			add(YOU_HOU_45);
			add(HOU_DENG);
			add(HOU_BEI_XIANG);
			add(FADONGJI_ZHENGTI);
			add(FADONGJI_XIEJIE);
			add(XING_SHI_ZHENG);
			add(LIANG_DIAN1);
			add(LIANG_DIAN2);
			add(LIANG_DIAN3);
			add(LIANG_DIAN4);
			add(LIANG_DIAN5);
		}
	};

	public static PhotoInfo getPhotoInfoWithIndex(ArrayList<PhotoInfo> photos, int index) {

		for (int i = 0; i < photos.size(); i++) {
			if (photos.get(i).getType().equals(photoTypes.get(index))) {
				return photos.get(i);
			}
		}
		return null;
	}
}
