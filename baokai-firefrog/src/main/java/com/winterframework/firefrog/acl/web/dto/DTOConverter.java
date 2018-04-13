package com.winterframework.firefrog.acl.web.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.winterframework.firefrog.acl.entity.AclGroup;
import com.winterframework.firefrog.acl.entity.AclOperateLog;
import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.modules.ip.IPSeeker;

public class DTOConverter {
	private static IPSeeker ipseek = IPSeeker.getInstance();

	public static AclGroupStruc aclGroupEntity2Struc(AclGroup aclGroup) {
		AclGroupStruc struc = new AclGroupStruc();
		struc.setCreatorer(aclGroup.getCreatorer());
		struc.setGmtCreated(aclGroup.getGmtCreated());
		struc.setGmtModified(aclGroup.getGmtModified());
		struc.setId(aclGroup.getId());
		struc.setInUser(aclGroup.getInUse());
		struc.setModifierer(aclGroup.getModifierer());
		struc.setName(aclGroup.getName());
		if (aclGroup.getParentGroup() != null) {
			struc.setPid(aclGroup.getParentGroup().getId());
		}
		struc.setLvl(aclGroup.getLvl());
		return struc;
	}

	public static List<AclStruc> handerAclTreeList(List<AclStruc> aclList) {
		List<AclStruc> parents = new ArrayList<AclStruc>();
		List<AclStruc> others = new ArrayList<AclStruc>();
		for (AclStruc acl : aclList) {
			if (acl.getPid() == null || acl.getPid().longValue() == 0) {
				acl.setSubAcls(new ArrayList<AclStruc>());
				parents.add(acl);
			} else {
				others.add(acl);
			}
		}
		buildTree(parents, others);
		return parents;
	}

	private static void buildTree(List<AclStruc> parents, List<AclStruc> others) {
		List<AclStruc> record = new ArrayList<AclStruc>();
		Iterator<AclStruc> it = parents.iterator();
		while (it.hasNext()) {
			AclStruc vi = it.next();
			if (vi.getId() != null) {
				Iterator<AclStruc> otherIt = others.iterator();
				while (otherIt.hasNext()) {
					AclStruc inVi = otherIt.next();
					if (vi.getId().equals(inVi.getPid())) {
						if (null == vi.getSubAcls()) {
							vi.setSubAcls(new ArrayList<AclStruc>());
						}
						vi.getSubAcls().add(inVi);
						record.add(inVi);
						otherIt.remove();
					}
				}
			}
		}
		if (others.size() == 0 || parents.size() == 0) {
			return;
		} else {
			buildTree(record, others);
		}
	}

	public static List<AclGroupStruc> handerAclGroupTreeList(List<AclGroupStruc> aclList) {
		List<AclGroupStruc> parents = new ArrayList<AclGroupStruc>();
		List<AclGroupStruc> others = new ArrayList<AclGroupStruc>();
		for (AclGroupStruc acl : aclList) {
			if (acl.getPid() == null || acl.getPid().longValue() == 0) {
				acl.setSubAclGroups(new ArrayList<AclGroupStruc>());
				parents.add(acl);
			} else {
				others.add(acl);
			}
		}
		buildGroupTree(parents, others);
		return parents;
	}

	private static void buildGroupTree(List<AclGroupStruc> parents, List<AclGroupStruc> others) {
		List<AclGroupStruc> record = new ArrayList<AclGroupStruc>();
		for (Iterator<AclGroupStruc> it = parents.iterator(); it.hasNext();) {
			AclGroupStruc vi = it.next();
			if (vi.getId() != null) {
				for (Iterator<AclGroupStruc> otherIt = others.iterator(); otherIt.hasNext();) {
					AclGroupStruc inVi = otherIt.next();
					if (vi.getId().equals(inVi.getPid())) {
						if (null == vi.getSubAclGroups()) {
							vi.setSubAclGroups(new ArrayList<AclGroupStruc>());
						}
						vi.getSubAclGroups().add(inVi);
						record.add(inVi);
						otherIt.remove();
					}
				}
			}
		}
		if (others.size() == 0) {
			return;
		} else {
			buildGroupTree(record, others);
		}
	}

	public static AclUserStruc transAclUser2Dto(AclUser user) {
		AclUserStruc dto = new AclUserStruc();
		if (user == null) {
			return null;
		}
		BeanUtils.copyProperties(user, dto);
		dto.setGroupId(user.getGroup().getId());
		dto.setGroupName(user.getGroup().getName());
		dto.setAcls(user.getAcls());
		if (user.getUserStatus() != null) {
			dto.setStatus((long) user.getUserStatus().ordinal());
		}
		try{
		if(user.getGmtModified()!=null)
		dto.setLastLoginDate(user.getGmtModified().getTime());
		dto.setLastArea(ipseek.getAddress(IPConverter.longToIp(user.getLastIp())));
		}catch(Exception e){}
		dto.setLastIp(user.getLastIp());
		return dto;
	}

	public static AclUser transDto2AclUser(AclUserStruc dto) {
		AclUser user = new AclUser();
		user.setId(dto.getId());
		BeanUtils.copyProperties(dto, user);
		AclGroup group = new AclGroup();
		group.setId(dto.getGroupId());
		user.setGroup(group);
		if (dto.getStatus() != null) {
			switch (dto.getStatus().intValue()) {
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

	public static AclOperateLogStruc transLog2Dto(AclOperateLog log) {
		AclOperateLogStruc dto = new AclOperateLogStruc();
		dto.setAccount(log.getUser().getAccount());
		dto.setAction(log.getAction());
		dto.setCreateTime(log.getCreateTime());
		dto.setDetail((long) log.getDetail().ordinal());
		dto.setId(log.getId());
		dto.setIp(log.getIp());
		dto.setUrl(log.getUrl());
		return dto;
	}

	public static AclOperateLog transDto2Log(AclOperateLogStruc dto) {
		AclOperateLog log = new AclOperateLog();
		log.setAction(dto.getAction());
		log.setCreateTime(dto.getCreateTime());
		log.setId(dto.getId());
		log.setIp(dto.getIp());
		log.setUrl(dto.getUrl());
		AclUser user = new AclUser();
		user.setAccount(dto.getAccount());
		log.setUser(user);
		switch (dto.getDetail().intValue()) {
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
		return log;
	}
}
