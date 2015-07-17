package com.cars.manager.db.table;

import java.io.Serializable;

import com.cars.manager.db.afinal.annotation.sqlite.Id;
import com.cars.manager.db.afinal.annotation.sqlite.Table;

@Table(name = "photoInfo")
public class PhotoInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String photoId;

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getPhotoId() {
		return photoId;
	}

	private String type;

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	private String url;

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	private String sdCardPath;// SDCard路径

	public void setSdCardPath(String sdCardPath) {
		this.sdCardPath = sdCardPath;
	}

	public String getSdCardPath() {
		return sdCardPath;
	}

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Id
	private String idType;

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdType() {
		return idType;
	}

	private String photoInfoId;

	public void setPhotoInfoId(String photoInfoId) {
		this.photoInfoId = photoInfoId;
	}

	public String getPhotoInfoId() {
		return photoInfoId;
	}

	private String image;

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

}
