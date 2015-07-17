package com.standard.kit.net;

/**
 * @author wht
 * 
 * @date 2013-7-18 下午5:25:20
 * 
 *       basic认证账户密码
 * 
 * @version
 */
public class BasicAuthorUtil {

	public static final String SYJH_URL = "http://game.feiliu.com/syjh/interface.php";

	public static final String QHQ_URL = "http://game.feiliu.com/qianghaoqi/interface.php";

	public static final String FORUM_URL_URL = "http://game.feiliu.com/forum/";
	
	public static final String FORUM_8783_URL = "http://api.8783.com/forum/";//		Jet Modify
	

	public static boolean isBasicAuthor(String url) {
		return SYJH_URL.equals(url) || QHQ_URL.equals(url) || FORUM_URL_URL.equals(url) || url.contains(FORUM_8783_URL);
		
	}

}
