package com.standard.kit.usage;

import java.io.Serializable;

/**
 * 应用使用统计 信息类
 * 
 * @author landmark
 * 
 */
public class PkgUsageStats implements Serializable {

	private static final long serialVersionUID = 1L;

	public String packageName;

	public int launchCount;

	public long usageTime;
}
