package com.winterframework.firefrog.game.dao.vo;

import com.winterframework.firefrog.game.entity.CancelMode;
import com.winterframework.firefrog.game.entity.EventStatus;
import com.winterframework.firefrog.game.entity.FileMode;
import com.winterframework.firefrog.game.entity.GameAwardEntity.GameAwardStatus;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity.Status;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity.SysAwardGroup;
import com.winterframework.firefrog.game.entity.GameIssuePeriodStatus;
import com.winterframework.firefrog.game.entity.GameIssueStatus;
import com.winterframework.firefrog.game.entity.GameOrder.OrderParentType;
import com.winterframework.firefrog.game.entity.GameOrder.OrderStatus;
import com.winterframework.firefrog.game.entity.GamePackage.GamePackageType;
import com.winterframework.firefrog.game.entity.GamePlan.GamePlanStatus;
import com.winterframework.firefrog.game.entity.GamePlan.GamePlanType;
import com.winterframework.firefrog.game.entity.GamePlan.StopMode;
import com.winterframework.firefrog.game.entity.GamePlanDetail.GamePlanDetailStatus;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity.StatusType;
import com.winterframework.firefrog.game.entity.GameSlipStatus;
import com.winterframework.firefrog.game.entity.GameWarnOrderEntity.GameWarnOrderStatus;
import com.winterframework.firefrog.game.entity.LastIssueStop;
import com.winterframework.firefrog.game.entity.MoneyMode;
import com.winterframework.firefrog.game.entity.PauseStatus;
import com.winterframework.firefrog.game.entity.PlanFinishStatus;

public class EnumTypeConverts {

	public static OrderStatus orderStatus2Enum(Long status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 1:
			return OrderStatus.WAITING;
		case 2:
			return OrderStatus.PRIZE;
		case 3:
			return OrderStatus.UN_PRIZE;
		case 4:
			return OrderStatus.CANCEL;
		default:
			return null;
		}
	}

	public static OrderStatus orderStatus3Enum(Long status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 1:
			return OrderStatus.WAITING;
		case 2:
			return OrderStatus.PRIZE;
		case 3:
			return OrderStatus.UN_PRIZE;
		case 4:
			return OrderStatus.CANCEL;
		case 5:
			return OrderStatus.ERROR;
		case 7:
			return OrderStatus.AUDITFAIL;
		case 8:
			return OrderStatus.AUDITSUCC;
		case 9:
			return OrderStatus.AUDITWAIT;
		default:
			return null;
		}
	}
	
	public static CancelMode orderCancelModes2Enum(Long status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 0:
			return CancelMode.DEFAULTS;
		case 1:
			return CancelMode.USER;
		case 2:
			return CancelMode.SYSTEM;
		default:
			return null;
		}
	}

	public static GamePlanStatus convertStatus2GamePlanStatus(Integer status) {
		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 0:
			return GamePlanStatus.EXECUTABLE;
		case 1:
			return GamePlanStatus.WAITING;
		case 2:
			return GamePlanStatus.FINISH;
		case 3:
			return GamePlanStatus.STOP;
		case 4:
			return GamePlanStatus.PAUSE;
		default:
			return null;
		}
	}

	public static StopMode getStopModeByValue(Integer stopMode) {
		switch (stopMode) {
		case 0:
			return StopMode.NO_STOP;
		case 1:
			return StopMode.STOP_BY_BENIFIT;
		case 2:
			return StopMode.WIN_STOP;
		default:
			return StopMode.NO_STOP;
		}

	}

	public static GamePlanDetailStatus convertIntegerStatus2GamePlanDetailStatus(Integer status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 0:
			return GamePlanDetailStatus.UN_EXEC;
		case 1:
			return GamePlanDetailStatus.EXEC;
		case 2:
			return GamePlanDetailStatus.CANCEL;
		case 3:
			return GamePlanDetailStatus.PAUSE;
		case 4:
			return GamePlanDetailStatus.WAIT_EXEC;
		case 5:
			return GamePlanDetailStatus.WAIT_CANCEL;
		case 6:
			return GamePlanDetailStatus.CANCEL_ERROR;
		default:
			return null;
		}
	}

	public static GameIssuePeriodStatus convertStatus2GameIssuePeriodStatus(Long status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 0:
			return GameIssuePeriodStatus.WAIT_SALE;
		case 1:
			return GameIssuePeriodStatus.SALES;
		case 2:
			return GameIssuePeriodStatus.WAIT_DRAW_RESULT;
		case 3:
			return GameIssuePeriodStatus.CALCULATION_PROCESS;
		case 4:
			return GameIssuePeriodStatus.VERIFICATIONP_ROCESS;
		case 5:
			return GameIssuePeriodStatus.PRIZE_PROCESS;
		case 6:
			return GameIssuePeriodStatus.WAIT_ISSUE_OVER;
		case 7:
			return GameIssuePeriodStatus.WAIT_RECONCILIATION;
		default:
			return null;
		}
	}

	public static GameIssueStatus convertStatus2GameIssueStatus(Long status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 0:
			return GameIssueStatus.CREATE;
		case 1:
			return GameIssueStatus.SALE_START;
		case 2:
			return GameIssueStatus.SALE_END;
		case 3:
			return GameIssueStatus.ACK_DRAW_RESULT;
		case 4:
			return GameIssueStatus.CALCULATION;
		case 5:
			return GameIssueStatus.VERIFICATION;
		case 6:
			return GameIssueStatus.PRIZE;
		case 7:
			return GameIssueStatus.ISSUE_OVER;
		case 8:
			return GameIssueStatus.RECONCILIATION;
		default:
			return null;
		}
	}

	public static PauseStatus convertStatus2PauseStatus(Integer status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 0:
			return PauseStatus.PAUSE;
		case 1:
			return PauseStatus.NORMAL;
		case 2:
			return PauseStatus.CANCAL;
		default:
			return null;
		}
	}

	public static EventStatus convertStatus2EventStatus(Integer status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 1:
			return EventStatus.NORMAL;
		case 2:
			return EventStatus.LOCK;
		default:
			return null;
		}
	}

	public static PlanFinishStatus convertStatus2PlanFinishStatus(Integer status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 0:
			return PlanFinishStatus.UN_FINISH;
		case 1:
			return PlanFinishStatus.FINISH;
		default:
			return null;
		}
	}

	public static LastIssueStop convertStatus2LastIssueStop(Integer status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 0:
			return LastIssueStop.PAUSE;
		case 1:
			return LastIssueStop.UN_PAUSE;
		default:
			return null;
		}
	}

	public static Status convertValue2Enum(Long status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 1:
			return Status.CURRENT;
		case 2:
			return Status.DELETE;
		case 3:
			return Status.WATING_AUDIT;
		case 4:
			return Status.WATING_PUBLISH;
		case 5:
			return Status.NotAudit;
		case 6:
			return Status.NotPublish;

		default:
			return null;
		}
	}

	public static SysAwardGroup convertValueToEnum(Integer awardGroup) {

		if (null == awardGroup) {
			return null;
		}

		switch (awardGroup) {
		case 1:
			return SysAwardGroup.SYSTEM;
		case 2:
			return SysAwardGroup.USER;
		default:
			return null;
		}
	}

	public static GameAwardStatus convertValue2GameAwardStatus(Integer status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 1:
			return GameAwardStatus.CURRENT;
		case 2:
			return GameAwardStatus.DELETE;
		case 3:
			return GameAwardStatus.WATING_AUDITING;
		case 4:
			return GameAwardStatus.WATING_PUBLISH;

		default:
			return null;
		}
	}

	public static StatusType convertValue2GameSeriesConfigStatusType(Long status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 2:
			return StatusType.Action;
		case 3:
			return StatusType.Pending;
		case 4:
			return StatusType.Released;
		case 5:
			return StatusType.Unapprove;
		case 6:
			return StatusType.PublishFailed;

		default:
			return null;
		}
	}

	public static GameWarnOrderStatus getGameWarnOrderStatus(Long action) {

		switch (action.intValue()) {
		case 0:
			return GameWarnOrderStatus.PendingAudit;
		case 1:
			return GameWarnOrderStatus.Audit;
		case 2:
			return GameWarnOrderStatus.unAudit;

		default:
			break;
		}

		return null;
	}

	public static MoneyMode convertMoneyMode(Integer action) {

		switch (action.intValue()) {
		case 1:
			return MoneyMode.YUAN;
		case 2:
			return MoneyMode.JIAO;
		case 3:
			return MoneyMode.FEN;
		default:
			break;
		}

		return null;
	}

	public static GameSlipStatus convertGameSlipStatus(Integer action) {

		switch (action.intValue()) {
		case 1:
			return GameSlipStatus.WAITING;
		case 2:
			return GameSlipStatus.WIN;
		case 3:
			return GameSlipStatus.NUWIN;
		case 4:
			return GameSlipStatus.CANCEL;
		case 5:
			return GameSlipStatus.EXCEP;
		default:
			break;
		}
		return null;
	}

	public static FileMode converFileMode(Integer fileMode) {
		switch (fileMode) {
		case 1:
			return FileMode.FILE;
		case 0:
			return FileMode.NUFILE;
		default:
			break;
		}
		return null;
	}

	public static GamePlanType convetGamePlanType2Enum(Integer planType) {

		switch (planType.intValue()) {
		case 1:

			return GamePlanType.GENERAL;
		case 2:
			return GamePlanType.DOUBLE;
		case 3:
			return GamePlanType.PAY_OFF;
		case 4:
			return GamePlanType.EARNINGS_RATE;

		default:
			break;
		}
		return null;
	}

	public static StopMode convertStopMode2Enum(Integer stopMode) {

		switch (stopMode.intValue()) {
		case 0:
			return StopMode.NO_STOP;
		case 1:
			return StopMode.STOP_BY_BENIFIT;
		case 2:
			return StopMode.WIN_STOP;
		default:
			break;
		}
		return null;
	}

	public static GamePackageType convertType2Emun(Integer type) {

		switch (type.intValue()) {
		case 1:
			return GamePackageType.PACKAGES;
		case 2:
			return GamePackageType.PLAN;
		default:
			break;
		}
		return null;
	}
	
	public static OrderParentType convertType2Emun(Long type){
		switch (type.intValue()) {
		case 1:
			return OrderParentType.PACKAGES;
		case 2:
			return OrderParentType.PLAN;
		default:
			break;
		}
		return null;
	}

	public static LastIssueStop convertLastIssueStop(Integer type) {

		switch (type.intValue()) {
		case 1:
			return LastIssueStop.PAUSE;
		case 0:
			return LastIssueStop.UN_PAUSE;
		default:
			break;
		}
		return null;
	}
}
