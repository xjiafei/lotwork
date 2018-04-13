package com.winterframework.firefrog.notice.entity;
public enum UserGroup {
		ALL("ALL"), TOP_AGENT("TOP_AGENT"), AGENT_1("AGENT_1"), AGENT_2("AGENT_2"), AGENT_3("AGENT_3"), AGENT_4(
				"AGENT_4"), AGENT_5("AGENT_5"), AGENT_xx("AGENT_xx"),OTHER_AGENT("OTHER_AGENT"), VIP("VIP"), NONVIP("NONVIP"), PASSANGE(
				"PASSANGE");
		private String value;

		private UserGroup(String ug) {
			this.value = ug;
		}
		
	}