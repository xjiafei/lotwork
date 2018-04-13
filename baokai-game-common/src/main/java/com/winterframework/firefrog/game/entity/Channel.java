package com.winterframework.firefrog.game.entity;

/**
 * 
* @ClassName: Channel 
* @Description: 渠道实例
* @author Richard
* @date 2014-4-1 下午5:37:29 
*
 */
public class Channel {
	/**渠道類型*/
	private ChannelType channelType;
	private String version;
	
	public Channel(){
		
	}
	/**
	 * 取得渠道類型。
	 * @return
	 */
	public ChannelType getChannelType() {
		return channelType;
	}
	/**
	 * 設定渠道類型。
	 * @param channelType
	 */
	public void setChannelType(ChannelType channelType) {
		this.channelType = channelType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
}
