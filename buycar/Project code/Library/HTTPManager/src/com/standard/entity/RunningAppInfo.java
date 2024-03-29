package com.standard.entity;

import android.graphics.drawable.Drawable;

/**
 * Model类 ，用来存储应用程序信息
 * 
 * @author Taijuk
 * 
 */
public class RunningAppInfo {

	protected String appLabel = ""; // 应用程序标签
	protected Drawable appIcon = null; // 应用程序图像
	protected String pkgName = ""; // 应用程序所对应的包名

	protected int pid = 0; // 该应用程序所在的进程号
	protected String processName = ""; // 该应用程序所在的进程名

	public RunningAppInfo() {
	}

	public String getAppLabel() {
		return appLabel;
	}

	public void setAppLabel(String appName) {
		this.appLabel = appName;
	}

	public Drawable getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

}
