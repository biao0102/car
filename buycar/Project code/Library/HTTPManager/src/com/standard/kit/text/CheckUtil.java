package com.standard.kit.text;

import java.util.Locale;

public class CheckUtil {

	/**
	 * 判断是否使用中文(包括简体和繁体)
	 * */
	public static boolean isChineseUser(){
		boolean isChineseUser = false;
		//简体为：zh-CN 繁体为：zh-TW
		if(Locale.getDefault().getLanguage().toLowerCase().equals("zh"))
			isChineseUser = true;
		
		return isChineseUser;
	}	
}
