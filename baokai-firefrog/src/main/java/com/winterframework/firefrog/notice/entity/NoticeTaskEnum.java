package com.winterframework.firefrog.notice.entity;


public enum NoticeTaskEnum {

	CardBind("CardBind", 1),
	CardModify("CardModify", 3),
	CardUnLock("CardUnLock", 4), 
	BINDMAIL("BindMail", 5), 
	CHANGEMAIL_OLD("ChangeMail", 6), 
	CHANGEMAIL_NEW("ChangeMail", 7), 
	SecurityQAFind("SecurityQAFind",9), 
	SecurityQASet("SecurityQASet", 11), 
	SecurityQAModify("SecurityQAModify", 12), 
	WithdrawPwdSet("WithdrawPwdSet", 13), 
	WithdrawPwdModify("WithdrawPwdModify", 14), 
	CipherSet("CipherSet", 15), 
	CipherModify("CipherModify", 16),
	PasswordModify("PasswordModify", 17), 
	Register("Register", 18), 
	ChargeSuccessful("ChargeSuccessful", 19),
	WithdrawSuccessful("WithdrawSuccessful", 20), 
	TransferSuccessful("TransferSuccessful", 21),
	WithdrawRefuse("WithdrawRefuse",22), 
	MutilateLogin("MutilateLogin", 24), 
	DiffPlaceLogin("DiffPlaceLogin", 25), 
	ModifySecurityPass("ModifySecurityPass", 26),
	ModifyLoginPass("ModifyLoginPass", 27),
	TransfertoSubSysSuccessful("TransfertoSubSysSuccessful", 31),
	TransferbackSubSysSuccessful("TransferbackSubSysSuccessful", 32);
	
	private String name;
	private int index;

	private NoticeTaskEnum(String name,int index) {
		this.name = name;
		this.index = index;
	}

	public static NoticeTaskEnum getEnum(int index) {
		for (NoticeTaskEnum notice : NoticeTaskEnum.values()) {
			if (notice.getIndex() == index) {
				return notice;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
