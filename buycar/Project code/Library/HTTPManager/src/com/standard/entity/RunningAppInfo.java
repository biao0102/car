package com.standard.entity;

import android.graphics.drawable.Drawable;

/**
 * Model�� �������洢Ӧ�ó�����Ϣ
 * 
 * @author Taijuk
 * 
 */
public class RunningAppInfo {

	protected String appLabel = ""; // Ӧ�ó����ǩ
	protected Drawable appIcon = null; // Ӧ�ó���ͼ��
	protected String pkgName = ""; // Ӧ�ó�������Ӧ�İ���

	protected int pid = 0; // ��Ӧ�ó������ڵĽ��̺�
	protected String processName = ""; // ��Ӧ�ó������ڵĽ�����

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
