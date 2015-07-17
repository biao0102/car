package com.cars.manager.model;

import java.io.Serializable;

public class BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String code;
	private String tips;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTips() 
	{
		if(tips ==null)
		{	
			this.tips = "";
		}
		
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public BaseBean()
	{
		
	}
	
	
	public boolean isSuccess()
	{
		return code!=null&&code.equals("0");
	}
	
	

}
