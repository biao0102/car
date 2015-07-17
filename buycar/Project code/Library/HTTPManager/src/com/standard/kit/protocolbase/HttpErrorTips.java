package com.standard.kit.protocolbase;

import java.util.Locale;

/**
 * 
 * @author chnage by liaoyongxiong
 * 20140402增加国际化支持
 *
 */
public class HttpErrorTips {
	private static final String language= Locale.getDefault().getLanguage();///* zh中文  en英文 ko韩文*/
	private static final String locale=Locale.getDefault().toString();//zh_cn: 简体中文  zh_hk: 繁体中文(中国香港) zh_tw: 繁体中文(中国台湾地区)	  en-hk: 英语(香港)	  en_us: 英语(美国)	  en_gb: 英语(英国)	  en_ww: 英语(全球)	  ja_jp: 日语(日本)	  ko_kr: 韩文(韩国)
	public static final int LANGUAGEINDEX=getLanguageIndex(true);
	public static final int LANGUAGECODE=getLanguageIndex(false);
	public static int getLanguageIndex(boolean isIndex){
		/*各种语言的INDEX值
		0	86		zh_cn		简体中文
		1	852		zh_hk 		繁体中文
		2	82		ko		韩文
		3	44		en		英文
		4	33		fr		法文
		5	81		ja		日文
		*/
		int retvalue=0;//默认简体中文
		int langeage_code=86;
		if (language.equalsIgnoreCase("zh")){
			if((locale.equalsIgnoreCase("zh_hk")) || (locale.equalsIgnoreCase("zh_tw"))){
				retvalue=1;//繁体中文
				langeage_code=852;
			}else{
				retvalue=0;//简体中文
				langeage_code=86;
			}
		}if (language.equalsIgnoreCase("ko")){
			retvalue=2;//韩文
			langeage_code=82;
		}if (language.equalsIgnoreCase("en")){
			retvalue=3;//英文
			langeage_code=44;
		}if (language.equalsIgnoreCase("fr")){
			retvalue=4;//法文
			langeage_code=33;
		}if (language.equalsIgnoreCase("ja")){
			retvalue=5;//日文
			langeage_code=81;
		}
		if (!isIndex){
			retvalue=langeage_code;
		}
		return retvalue;
	}
	/** 8000-9000 exception */
	public static final String[] HTTP_ERROR_CONN_DATA={"通信数据异常","","통신 데이터에 오류가 발생했습니다","","",""};
	/** 9000-9600 http response code >= 500 */
	public static final String[] HTTP_ERROR_SERVER={"服务器繁忙","","서버접속이 원활하지 않습니다","","",""};
	/** 9000 500 > http response code >= 400- */
	public static final String[] HTTP_ERROR_HTTP={"网络请求出错","","네트워크 요청에 오류가 발생했습니다","","",""};
	/** -1 解密失败 一般是 网关错误 */
	public static final String[] HTTP_ERROR_FILE={"文件解析失败","","구문 분석에 오류가 발생했습니다","","",""};
}
