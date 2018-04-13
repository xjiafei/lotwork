package com.winterframework.firefrog.user.entity;

import com.winterframework.orm.dal.ibatis3.BaseEntity;
import java.util.Date;

public class ImGroupMessage extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long groupId;

	private Long userId;

	private String account;

	private String content;

	private String stompContent;

	private Date createDate;

	private Long targetId;

	private Long targetGroupId;

	private Long targetUserId;

	private String targetAccount;
	
	private String targetReceiverAccount;

	private String targetContent;

	private String targetStompContent;

	private Date targetCreateDateStart;

	private Date targetCreateDateEnd;
	
	private Boolean targetHasUnreadMsg;

	public void setId(Long id){
		 this.id= id;
	}

	public Long getId(){
		return this.id;
	}

	public void setGroupId(Long groupId){
		 this.groupId= groupId;
	}

	public Long getGroupId(){
		return this.groupId;
	}

	public void setUserId(Long userId){
		 this.userId= userId;
	}

	public Long getUserId(){
		return this.userId;
	}

	public void setAccount(String account){
		 this.account= account;
	}

	public String getAccount(){
		return this.account;
	}

	public void setContent(String content){
		 this.content= content;
	}

	public String getContent(){
		return this.content;
	}

	public void setStompContent(String stompContent){
		 this.stompContent= stompContent;
	}

	public String getStompContent(){
		return this.stompContent;
	}

	public void setCreateDate(Date createDate){
		 this.createDate= createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setTargetId(Long targetId){
		 this.targetId= targetId;
	}

	public Long getTargetId(){
		return this.targetId;
	}

	public void setTargetGroupId(Long targetGroupId){
		 this.targetGroupId= targetGroupId;
	}

	public Long getTargetGroupId(){
		return this.targetGroupId;
	}

	public void setTargetUserId(Long targetUserId){
		 this.targetUserId= targetUserId;
	}

	public Long getTargetUserId(){
		return this.targetUserId;
	}

	public void setTargetAccount(String targetAccount){
		 this.targetAccount= targetAccount;
	}

	public String getTargetAccount(){
		return this.targetAccount;
	}

	public void setTargetContent(String targetContent){
		 this.targetContent= targetContent;
	}

	public String getTargetContent(){
		return this.targetContent;
	}

	public void setTargetStompContent(String targetStompContent){
		 this.targetStompContent= targetStompContent;
	}

	public String getTargetStompContent(){
		return this.targetStompContent;
	}

	public void setTargetCreateDateStart(Date targetCreateDateStart){
		 this.targetCreateDateStart= targetCreateDateStart;
	}

	public Date getTargetCreateDateStart(){
		return this.targetCreateDateStart;
	}

	public void setTargetCreateDateEnd(Date targetCreateDateEnd){
		 this.targetCreateDateEnd= targetCreateDateEnd;
	}

	public Date getTargetCreateDateEnd(){
		return this.targetCreateDateEnd;
	}

	public Boolean getTargetHasUnreadMsg() {
		return targetHasUnreadMsg;
	}

	public void setTargetHasUnreadMsg(Boolean targetHasUnreadMsg) {
		this.targetHasUnreadMsg = targetHasUnreadMsg;
	}

	public String getTargetReceiverAccount() {
		return targetReceiverAccount;
	}

	public void setTargetReceiverAccount(String targetReceiverAccount) {
		this.targetReceiverAccount = targetReceiverAccount;
	}

}
