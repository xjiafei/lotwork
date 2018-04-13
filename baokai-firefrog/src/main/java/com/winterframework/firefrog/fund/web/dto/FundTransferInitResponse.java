package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

import com.winterframework.firefrog.config.web.controller.dto.TransfertoUser;

public class FundTransferInitResponse implements Serializable {

	private static final long serialVersionUID = 2850461341425159239L;
	
	private String transfer;
	//余额
	private Long bal;
	private Long unavailBal;
	//剩余转账次数
	private Long avaliTransferTimeUp;
	//剩余转账次数
	private Long avaliTransferTimeDown;
	private TransfertoUser transferStruc;
	
	public TransfertoUser getTransferStruc() {
		return transferStruc;
	}

	public void setTransferStruc(TransfertoUser transferStruc) {
		this.transferStruc = transferStruc;
	}



	public Long getAvaliTransferTimeUp() {
		return avaliTransferTimeUp;
	}

	public void setAvaliTransferTimeUp(Long avaliTransferTimeUp) {
		this.avaliTransferTimeUp = avaliTransferTimeUp;
	}

	public Long getAvaliTransferTimeDown() {
		return avaliTransferTimeDown;
	}

	public void setAvaliTransferTimeDown(Long avaliTransferTimeDown) {
		this.avaliTransferTimeDown = avaliTransferTimeDown;
	}

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public Long getBal() {
		return bal;
	}

	public void setBal(Long bal) {
		this.bal = bal;
	}


	public Long getUnavailBal() {
		return unavailBal;
	}

	public void setUnavailBal(Long unavailBal) {
		this.unavailBal = unavailBal;
	}

}
