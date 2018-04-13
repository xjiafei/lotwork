package com.winterframework.adbox.service;
import java.util.List;

import com.winterframework.adbox.dao.base.Context;
import com.winterframework.adbox.dao.base.IBaseService;
import com.winterframework.adbox.entity.AdResource;
public interface IAdResourceService extends IBaseService<AdResource> {
	public List<AdResource> getAdResourceByPath(Context ctx,String path,Long userId) throws Exception;
}
