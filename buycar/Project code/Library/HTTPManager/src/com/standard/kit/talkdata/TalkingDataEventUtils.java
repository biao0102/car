package com.standard.kit.talkdata;

/**
 * @author wht
 * 
 * @date 2013年7月12日14:32:13
 * 
 *       Event ID无需提前在数据平台中定义，可自行定义名称，直接加入到应用中 需要跟踪的位置即可生效。
 *       1,格式：32个字符以内的中文、英文、数字、下划线，注意eventId中不要加 空格或其他的转义字符。
 *       2,TalkingData最多支持100个不同的Event ID。 如果您要跟踪更多的事件，我们提供了Label参数的用法，可以给多个要跟踪
 *       的同类型或类似的事件使用相同的Event ID，通过给他们分配不同Label来 达到区分跟踪多个事件的目的。这可理解为Event
 *       ID成为了多个事件的目录， EventID+Label形成了一个具体事件。请对事件做好分类，这也对您管理和 查阅事件数据有利
 * 
 * 
 *       在应用程序要跟踪的事件处加入下面格式的代码，也就成功的添加了一个简单 事件到您的应用程序中了：
 *       TCAgent.onEvent(Activity activity, String EVENT_ID); 
 *       跟踪多个同类型事件，无需定义多个Event ID，可以使用Event ID做为目录
 *       名，而使用Label标签来区分这些事件，可按照下面格式添加代码： TCAgent.onEvent(Activity activity,
 *       String EVENT_ID, String EVENT_LABEL);
 */
public class TalkingDataEventUtils {

	public static final String EVENT_ID_NETWORK = "联网";

	public static final String EVENT_ID_DOWNLOAD = "下载";

	public static final String EVENT_LABEL_DOWNLOAD_BEGIN = "开始下载";

	public static final String EVENT_LABEL_DOWNLOAD_PAUSE = "暂停下载";
	
	public static final String EVENT_LABEL_DOWNLOAD_CANCEL = "取消下载";
	
	public static final String EVENT_LABEL_DOWNLOAD_SUCCESS = "下载成功";

}
