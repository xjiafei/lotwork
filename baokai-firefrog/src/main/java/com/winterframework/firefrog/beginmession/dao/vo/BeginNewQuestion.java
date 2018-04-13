package com.winterframework.firefrog.beginmession.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;


/**
 * 
 * @author Ami.Tsai
 *
 */
public class BeginNewQuestion extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -953938156491650591L;

	private String createUser;

	private Date createTime;

	private String modifyUser;

	private Date modifyTime;

	private String question;

	private String crtAnswer;

	private String errAnswer1;

	private String errAnswer2;
	
	private Integer index;
	
	private String deleteFlag;

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getCrtAnswer() {
		return crtAnswer;
	}

	public void setCrtAnswer(String crtAnswer) {
		this.crtAnswer = crtAnswer;
	}

	public String getErrAnswer1() {
		return errAnswer1;
	}

	public void setErrAnswer1(String errAnswer1) {
		this.errAnswer1 = errAnswer1;
	}

	public String getErrAnswer2() {
		return errAnswer2;
	}

	public void setErrAnswer2(String errAnswer2) {
		this.errAnswer2 = errAnswer2;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
