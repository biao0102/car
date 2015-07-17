package com.cars.manager.model;

import java.io.Serializable;

public class CarsImagesInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6475601221357638495L;

	private String desc;
	private String path;

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public String photoId;

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getPhotoId() {
		return photoId;
	}

}
