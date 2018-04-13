package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.util.List;

public class MessageListResponseDTO {

	private List<MsgStrucResponse> receives;
	private int unreadCnt;

	public List<MsgStrucResponse> getReceives() {
		return receives;
	}

	public void setReceives(List<MsgStrucResponse> receives) {
		this.receives = receives;
	}

	public int getUnreadCnt() {
		return unreadCnt;
	}

	public void setUnreadCnt(int unreadCnt) {
		this.unreadCnt = unreadCnt;
	}

	@Override
	public String toString() {
		return "MessageListResponseDTO [receives=" + receives + ", unreadCnt=" + unreadCnt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((receives == null) ? 0 : receives.hashCode());
		result = prime * result + unreadCnt;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageListResponseDTO other = (MessageListResponseDTO) obj;
		if (receives == null) {
			if (other.receives != null)
				return false;
		} else if (!receives.equals(other.receives))
			return false;
		if (unreadCnt != other.unreadCnt)
			return false;
		return true;
	}

}
