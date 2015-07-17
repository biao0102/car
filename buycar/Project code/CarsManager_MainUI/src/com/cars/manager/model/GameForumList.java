package com.cars.manager.model;

import java.io.Serializable;
import java.util.ArrayList;

public class GameForumList implements Serializable {

	/**
	 * "code": 0, "result": { "msglists": "0", //消息数量 0为没有新消息 "forumlists": [ {
	 * "gameid": "100105", //游戏id "forumid": "47", //论坛板块id "forumname":
	 * "全民斗三国", //论坛名称 "forumpicurl":
	 * "http://image.feeliu.com/Upload/element/logo/20141/element_logo_1401161632001739.png"
	 * , //游戏图标 "sdkname": "com.tencent.dousanguo.fl", //游戏包名 "todayposts":
	 * "225", //当天帖子数量 "itemid": "", //礼包关联id "itemidStatus": "0", //是否有新礼包 0没有
	 * 1有 "posts": "4743", //论坛板块的总发帖数 "gameUserCount": 194802, //游戏人数
	 * "statLastTime": "07月02日 17:07" //游戏最后登陆时间
	 */
	private static final long serialVersionUID = -3259496306962556748L;

	public GameForumList() {

	}

	private String msglists;

	private ArrayList<GameForumItem> forumlists;

	public ArrayList<GameForumItem> getForumlists() {
		if (forumlists == null)
			forumlists = new ArrayList<GameForumItem>();
		return forumlists;
	}

	public void setForumlists(ArrayList<GameForumItem> forumlists) {
		this.forumlists = forumlists;
	}

	public void setMsglists(String msglists) {
		this.msglists = msglists;
	}

	public String getMsglists() {
		return msglists;
	}

}