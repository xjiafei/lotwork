package com.winterframework.firefrog.acl.dao.vo;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.winterframework.firefrog.acl.entity.Acl;
import com.winterframework.firefrog.acl.entity.AclGroup;
import com.winterframework.firefrog.acl.entity.AclGroupAuthorization;
import com.winterframework.firefrog.acl.entity.AclOperateLog;
import com.winterframework.firefrog.acl.entity.AclUser;

public class VOConverter {

	public static AclGroupAuthorizationVO aclGroupAuthorization2VO(AclGroupAuthorization aclGroupAuth) {
		AclGroupAuthorizationVO vo = new AclGroupAuthorizationVO();
		BeanUtils.copyProperties(aclGroupAuth, vo);
		vo.setAclId(aclGroupAuth.getAcl().getId());
		vo.setGid(aclGroupAuth.getAclGroup().getId());
		return vo;
	}

	public static AclGroupAuthorization vO2AclGroupAuthorization(AclGroupAuthorizationVO vo) {
		AclGroupAuthorization aclGroupAuth = new AclGroupAuthorization();
		aclGroupAuth.setId(vo.getId());
		return aclGroupAuth;
	}

	public static AclVO acl2VO(Acl acl) {
		AclVO vo = new AclVO();
		BeanUtils.copyProperties(acl, vo);
		vo.setPid(acl.getParentAcl().getId());
		return vo;
	}

	public static Acl vO2Acl(AclVO vo) {
		Acl acl = new Acl();
		BeanUtils.copyProperties(vo, acl);
		if (vo.getPid() != null) {
			Acl parentAcl = new Acl();
			parentAcl.setId(vo.getPid());
			acl.setParentAcl(parentAcl);
		}
		return acl;
	}

	public static AclGroup vO2AclGroup(AclGroupVO vo) {
		AclGroup aclGroup = new AclGroup();
		if (vo != null) {
			BeanUtils.copyProperties(vo, aclGroup);
			if (vo.getPid() != null) {
				AclGroup parent = new AclGroup();
				parent.setId(vo.getPid());
				aclGroup.setParentGroup(parent);
			}
		}
		return aclGroup;
	}

	public static AclGroupVO aclGroup2VO(AclGroup aclGroup) {
		AclGroupVO vo = new AclGroupVO();
		BeanUtils.copyProperties(aclGroup, vo);
		if (aclGroup.getParentGroup() != null) {
			vo.setPid(aclGroup.getParentGroup().getId());
		}
		return vo;
	}

	public static AclUserVO transAclUser2VO(AclUser user) {
		AclUserVO vo = new AclUserVO();
		BeanUtils.copyProperties(user, vo);
		vo.setGroupId(user.getGroup().getId());
		if (user.getUserStatus() != null) {
			vo.setStatus((long) user.getUserStatus().ordinal());
		}
		vo.setGmtModified(new Date());
		return vo;
	}

	public static AclUser transVO2AclUser(AclUserVO vo) {
		AclUser user = new AclUser();
		BeanUtils.copyProperties(vo, user);
		AclGroup group = new AclGroup();
		group.setId(vo.getGroupId());
		group.setName(vo.getGroupName());
		user.setGroup(group);
		if (vo.getStatus() != null) {
			switch (vo.getStatus().intValue()) {
			case 0:
				user.setUserStatus(AclUser.Status.normal);
				break;
			case 1:
				user.setUserStatus(AclUser.Status.locked);
				break;
			case 2:
				user.setUserStatus(AclUser.Status.deleting);
				break;
			}
		}
		return user;
	}

	public static AclOperateLogVO transLog2VO(AclOperateLog log) {
		AclOperateLogVO vo = new AclOperateLogVO();
		vo.setAccount(log.getUser().getAccount());
		vo.setAction(log.getAction());
		vo.setDetail((long) log.getDetail().ordinal());
		vo.setGmtCreated(log.getCreateTime());
		vo.setId(log.getId());
		vo.setIp(log.getIp());
		vo.setUrl(log.getUrl());
		return vo;
	}

	public static AclOperateLog transVO2Log(AclOperateLogVO vo) {
		AclOperateLog log = new AclOperateLog();
		log.setAction(vo.getAction());
		log.setCreateTime(vo.getGmtCreated());
		log.setId(vo.getId());
		log.setIp(vo.getIp());
		log.setUrl(vo.getUrl());

		switch (vo.getDetail().intValue()) {
		case 0:
			log.setDetail(AclOperateLog.Type.detail);
			break;
		case 1:
			log.setDetail(AclOperateLog.Type.successful);
			break;
		case 2:
			log.setDetail(AclOperateLog.Type.failed);
			break;
		case 3:
			log.setDetail(AclOperateLog.Type.noAccess);
			break;
		}

		AclUser user = new AclUser();
		user.setAccount(vo.getAccount());
		log.setUser(user);
		return log;
	}
}
