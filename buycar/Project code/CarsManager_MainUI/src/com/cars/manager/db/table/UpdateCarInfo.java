package com.cars.manager.db.table;

import java.io.Serializable;
import java.util.ArrayList;

import com.cars.manager.db.afinal.annotation.sqlite.Id;
import com.cars.manager.db.afinal.annotation.sqlite.Table;

@Table(name = "updateCarInfo")
public class UpdateCarInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String time;

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	private String brandId;

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandId() {
		return brandId;
	}

	private String brandName;

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandName() {
		return brandName;
	}

	private String color;

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	private String colorName;

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getColorName() {
		return colorName;
	}

	private String firstSignDate;

	public void setFirstSignDate(String firstSignDate) {
		this.firstSignDate = firstSignDate;
	}

	public String getFirstSignDate() {
		return firstSignDate;
	}

	private String jizhiPrice;

	public void setJizhiPrice(String jizhiPrice) {
		this.jizhiPrice = jizhiPrice;
	}

	public String getJizhiPrice() {
		return jizhiPrice;
	}

	private String seriesId;

	public void setSeriesId(String seriesId) {
		this.seriesId = seriesId;
	}

	public String getSeriesId() {
		return seriesId;
	}

	private String seriesName;

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getSeriesName() {
		return seriesName;
	}

	private String supplierId;

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	private String supplierName;

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	private String travlledDistance;

	public void setTravlledDistance(String travlledDistance) {
		this.travlledDistance = travlledDistance;
	}

	public String getTravlledDistance() {
		return travlledDistance;
	}

	private String typeId;

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeId() {
		return typeId;
	}

	private String typeName;

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	private String vehicleNo;

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	private String vin;

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getVin() {
		return vin;
	}

	public String zhengqianfangImg;

	public void setZhengqianfangImg(String zhengqianfangImg) {
		this.zhengqianfangImg = zhengqianfangImg;
	}

	public String getZhengqianfangImg() {
		return zhengqianfangImg;
	}

	private int wanzhengdu;

	public void setWanzhengdu(int wanzhengdu) {
		this.wanzhengdu = wanzhengdu;
	}

	public int getWanzhengdu() {
		return wanzhengdu;
	}

	private ArrayList<PhotoInfo> files;

	public void setFiles(ArrayList<PhotoInfo> files) {
		this.files = files;
	}

	public ArrayList<PhotoInfo> getFiles() {
		return files;
	}

	/* 来源 */
	private String source;

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	/* 状态 */
	private String status;

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	/* 状态名字 */
	private String statusName;

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusName() {
		return statusName;
	}

	private boolean selected;

	public void setIsSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean getIsSelected() {
		return selected;
	}

	private String managerId;

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getManagerId() {
		return managerId;
	}

	private String tabId;

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public String getTabId() {
		return tabId;
	}
}
