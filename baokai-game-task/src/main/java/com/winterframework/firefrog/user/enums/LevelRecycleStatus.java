package com.winterframework.firefrog.user.enums;


public class LevelRecycleStatus {

	public enum TaskStatus {
		UNDO(0), DOING(1), FINISH(2), FAIL(3);
		private int value;

		TaskStatus(int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}
	}

}
