package com.winterframework.firefrog.acl.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.acl.dao.AclUserDao;
import com.winterframework.firefrog.acl.dao.vo.AclUserVO;
import com.winterframework.firefrog.acl.dao.vo.VOConverter;
import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.acl.web.dto.AclUserStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("aclUserDaoImpl")
public class AclUserDaoImpl extends BaseIbatis3Dao<AclUserVO> implements AclUserDao {

	@Override
	public void saveUser(AclUser user) throws Exception {
		AclUserVO vo = VOConverter.transAclUser2VO(user);
		this.insert(vo);
	}

	@Override
	public void updateUser(AclUser user) throws Exception {
		AclUserVO vo = VOConverter.transAclUser2VO(user);
		this.update(vo);
	}

	@Override
	public void deleteUser(Long id) {
		this.delete(id);
	}

	@Override
	public Page<AclUser> selectList(PageRequest<AclUserStruc> pageRequest) {
		//		AclUserStruc search = pageRequest.getSearchDo();
		//		Map<String, Object> map = new HashMap<String, Object>();
		//		map.put("account", search.getAccount());
		//		map.put("email", search.getEmail());
		//		map.put("telePhone", search.getTelephone());
		//		map.put("cellPhone", search.getCellphone());
		//		map.put("status", search.getStatus());
		//		map.put("groupId", search.getGroupId());
		//
		//		int totalCount = ((Long) sqlSessionTemplate.selectOne("getAclUserCountByPage", map)).intValue();
		//
		//		Page<AclUser> page = new Page<AclUser>(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount);
		//
		//		Map<String, Object> filters = new HashMap<String, Object>();
		//		filters.put("offset", page.getFirstResult());
		//		filters.put("pageSize", page.getPageSize());
		//		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		//		filters.put("sortColumns", pageRequest.getSortColumns());
		//		filters.putAll(map);
		//
		//		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		//		List<AclUserVO> list = sqlSessionTemplate.selectList("getAclUserByPage", filters, rowBounds);

		Page<AclUserVO> list = this.pageQuery(pageRequest, null, "userList");
		Page<AclUser> page = new Page<AclUser>(pageRequest.getPageNumber(), pageRequest.getPageSize(),
				list.getTotalCount());

		List<AclUser> userList = new ArrayList<AclUser>();
		for (AclUserVO vo : list.getResult()) {
			userList.add(VOConverter.transVO2AclUser(vo));
		}
		page.setResult(userList);
		return page;
	}

	/**
	* Title: getByUserId
	* Description:
	* @param id
	* @return 
	* @see com.winterframework.firefrog.acl.dao.AclUserDao#getByUserId(java.lang.Long) 
	*/
	@Override
	public AclUser getByUserId(Long id) throws Exception {
		AclUserVO vo = this.getById(id);
		return VOConverter.transVO2AclUser(vo);
	}

	/**
	* Title: checkUserByGroupIds
	* Description:
	* @param ids
	* @return 
	* @see com.winterframework.firefrog.acl.dao.AclUserDao#checkUserByGroupIds(java.util.List) 
	*/
	@Override
	public Boolean checkUserByGroupIds(List<Long> ids) throws Exception {
		List<AclUserVO> voList = this.sqlSessionTemplate.selectList("getByGroupIds", ids);
		return voList.size() == 0;
	}

	@Override
	public AclUser getUserByBindPwd(String bindPwd) throws Exception {
		AclUserVO vo = new AclUserVO();
		vo.setBindPasswd(bindPwd);
		List<AclUserVO> list = this.getAllByEntity(vo);
		if (!list.isEmpty()) {
			return VOConverter.transVO2AclUser(list.get(0));
		}
		return null;
	}

	@Override
	public List<AclUser> queryUserByBindCard(String bindCard) throws Exception {
		List<AclUserVO> list = this.sqlSessionTemplate.selectList("queryUserByBindCard", bindCard);
		List<AclUser> users = new ArrayList<AclUser>();
		if (!list.isEmpty()) {
			for (AclUserVO user : list) {
				users.add(VOConverter.transVO2AclUser(user));
			}
		}
		return users;
	}

	public String getUserByBindCard(String bindCard) throws Exception {
		Map passwds = this.sqlSessionTemplate.selectOne("getPasswdByBindCard", bindCard);
		if (passwds == null)
			return null;
		Object obj = passwds.get("BIND_PASSWD");
		return obj == null ? null : String.valueOf(obj);
	}

	@Override
	public AclUser getUserByName(String name) throws Exception {
		AclUserVO vo = new AclUserVO();
		vo.setAccount(name);
		List<AclUserVO> list = this.getAllByEntity(vo);
		if (!list.isEmpty()) {
			for (AclUserVO acl : list) {
				if (StringUtils.equalsIgnoreCase(acl.getAccount(), name)) {
					return VOConverter.transVO2AclUser(acl);
				}
			}
		}
		return null;
	}

	@Override
	public AclUser getUserByNamePwd(String name, String pwd) throws Exception {
		AclUserVO vo = new AclUserVO();
		vo.setAccount(name);
		vo.setPasswd(pwd);
		List<AclUserVO> list = this.getAllByEntity(vo);
		if (!list.isEmpty()) {
			return VOConverter.transVO2AclUser(list.get(0));
		}
		return null;
	}

	@Override
	public List<AclUser> getUserByAccount(String account) {
		AclUserVO vo = new AclUserVO();
		vo.setAccount(account);
		List<AclUserVO> list = this.sqlSessionTemplate.selectList("getUserByAccount", vo);
		List<AclUser> aclList = new ArrayList<AclUser>();
		if (!list.isEmpty()) {
			for (AclUserVO aclUserVO : list) {
				aclList.add(VOConverter.transVO2AclUser(aclUserVO));
			}
		}
		return aclList;
	}
}
