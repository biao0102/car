package com.cars.manager.model;

import java.io.Serializable;

public class GameForumItem implements Serializable {

	/**
	 * "gameid": "100105", //游戏id "forumid": "47", //论坛板块id "forumname":
	 * "全民斗三国", //论坛名称 "forumpicurl":
	 * "http://image.feeliu.com/Upload/element/logo/20141/element_logo_1401161632001739.png"
	 * , //游戏图标 "sdkname": "com.tencent.dousanguo.fl", //游戏包名 "todayposts":
	 * "225", //当天帖子数量 "itemid": "", //礼包关联id "itemidStatus": "0", //是否有新礼包 0没有
	 * 1有 "posts": "4743", //论坛板块的总发帖数 "gameUserCount": 194802, //游戏人数
	 * "statLastTime": "07月02日 17:07" //游戏最后登陆时间
	 */
	private static final long serialVersionUID = -771589989053989867L;

	private String gameid;

	private String forumid;
	private String forumname;
	private String forumpicurl;
	private String sdkname;
	private String todayposts;
	private String itemid;
	private String itemidStatus;
	private String posts;
	private String gameUserCount;
	private String statLastTime;
	private String website;
	private String aid;
	private String itemidStatusShow;
	public boolean isBlank;
	private String ForumStatus;
	private String ForumContent;
	private String acType;
	private String acUrl;
	private String acImage;

	/**
	 * 签到标识 1已签 0未签
	 */
	private String CheckIn;

	public String getCheckIn() {
		return CheckIn;
	}

	public void setCheckIn(String checkIn) {
		CheckIn = checkIn;
	}

	public GameForumItem() {
	}

	public void setGameid(String gameid) {
		this.gameid = gameid;
	}

	public String getGameid() {
		return gameid;
	}

	public void setForumid(String forumid) {
		this.forumid = forumid;
	}

	public String getForumid() {
		return forumid;
	}

	public void setForumname(String forumname) {
		this.forumname = forumname;
	}

	public String getForumname() {
		return forumname;
	}

	public void setForumpicurl(String forumpicurl) {
		this.forumpicurl = forumpicurl;
	}

	public String getForumpicurl() {
		return forumpicurl;
	}

	public void setSdkname(String sdkname) {
		this.sdkname = sdkname;
	}

	public String getSdkname() {
		return sdkname;
	}

	public void setTodayposts(String todayposts) {
		this.todayposts = todayposts;
	}

	public String getTodayposts() {
		return todayposts;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemidStatus(String itemidStatus) {
		this.itemidStatus = itemidStatus;
	}

	public String getItemidStatus() {
		return itemidStatus;
	}

	public void setPosts(String posts) {
		this.posts = posts;
	}

	public String getPosts() {
		return posts;
	}

	public void setGameUserCount(String gameUserCount) {
		this.gameUserCount = gameUserCount;
	}

	public String getGameUserCount() {
		return gameUserCount;
	}

	public void setStatLastTime(String statLastTime) {
		this.statLastTime = statLastTime;
	}

	public String getStatLastTime() {
		return statLastTime;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getWebsite() {
		return website;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getAid() {
		return aid;
	}

	public void setItemidStatusShow(String itemidStatusShow) {
		this.itemidStatusShow = itemidStatusShow;
	}

	public String getItemidStatusShow() {
		return itemidStatusShow;
	}

	public void setForumStatus(String forumStatus) {
		ForumStatus = forumStatus;
	}

	public String getForumStatus() {
		return ForumStatus;
	}

	public void setForumContent(String forumContent) {
		ForumContent = forumContent;
	}

	public String getForumContent() {
		return ForumContent;
	}

	public void setAcUrl(String acUrl) {
		this.acUrl = acUrl;
	}

	public String getAcUrl() {
		return acUrl;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getAcType() {
		return acType;
	}

	public void setAcImage(String acImage) {
		this.acImage = acImage;
	}

	public String getAcImage() {
		return acImage;
	}

	/** 该板块是否有签到弹窗 */
	private String hasSignTip;

	public void setHasSignTip(String hasSignTip) {
		this.hasSignTip = hasSignTip;
	}

	public String getHasSignTip() {
		return hasSignTip;
	}

}
