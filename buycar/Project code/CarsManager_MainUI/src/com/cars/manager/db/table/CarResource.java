package com.cars.manager.db.table;

import java.io.Serializable;

import com.cars.manager.db.afinal.annotation.sqlite.Id;
import com.cars.manager.db.afinal.annotation.sqlite.Table;

@Table(name = "carresource")
public class CarResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4772979563379457668L;
	/**
	 * "addDate": "2014-12-12",//入库时间 "bargainPrice": "12",//成交价格 "bargainTime":
	 * "",//成交时间 "brandName": "奥迪",//品牌 "firstSignDate": "2014-12-12",//上牌时间
	 * "jizhiPrice": "20",//报价 "seriesName": "奥迪Q3",//车系 "status": 0,//状态
	 * "statusValue": "未售",//状态值 "supplierName": "极致车网",//商家 "travlledDistance":
	 * 5000,//里程 "typeName": "奥迪",//车型 "vehicleNo": "1000001"//车源编号,
	 * "zhengqianfangImage"
	 * :http://118.26.166.173/90x60/1001000339/0d341448-1bb2-
	 * 4ecb-895d-a263bf28df3a.jpg//图片
	 * 
	 * 
	 * */

	private String zhengqianfangImage;

	public void setZhengqianfangImage(String zhengqianfangImage) {
		this.zhengqianfangImage = zhengqianfangImage;
	}

	public String getZhengqianfangImage() {
		return zhengqianfangImage;
	}

	private String vehicleNo;

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	private String typeName;

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	private String travlledDistance;

	public void setTravlledDistance(String travlledDistance) {
		this.travlledDistance = travlledDistance;
	}

	public String getTravlledDistance() {
		return travlledDistance;
	}

	private String supplierName;

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	private String supplierId;

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	private String statusValue;

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getStatusValue() {
		return statusValue;
	}

	private String status;

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	private String addDate;

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public String getAddDate() {
		return addDate;
	}

	private String bargainPrice;

	public void setBargainPrice(String bargainPrice) {
		this.bargainPrice = bargainPrice;
	}

	public String getBargainPrice() {
		return bargainPrice;
	}

	private String bargainTime;

	public void setBargainTime(String bargainTime) {
		this.bargainTime = bargainTime;
	}

	public String getBargainTime() {
		return bargainTime;
	}

	private String brandName;

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandName() {
		return brandName;
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

	private String seriesName;

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getSeriesName() {
		return seriesName;
	}

	@Id
	private String vin;

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getVin() {
		return vin;
	}

	private String carColor;

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public String getCarColor() {
		return carColor;
	}

	private boolean selected;

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean getSelected() {
		return selected;
	}
}
