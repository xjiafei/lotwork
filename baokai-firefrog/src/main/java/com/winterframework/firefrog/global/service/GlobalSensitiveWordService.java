package com.winterframework.firefrog.global.service;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.common.exception.SensitiveWordException;
import com.winterframework.firefrog.global.entity.SensitiveWord;
import com.winterframework.firefrog.global.web.dto.GlobalSensitiveWordQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: GlobalSensitiveWordService 
* @Description: 敏感词业务接口
* @author Tod
* @date 2013-10-15 下午4:48:46 
*
 */
public interface GlobalSensitiveWordService {

	/**
	 * 
	* @Title: save 
	* @Description: 新加敏感词
	* @param words
	* @throws Exception
	 */
	public String save(List<SensitiveWord> words) throws Exception;

	/**
	 * 
	* @Title: reName 
	* @Description: 重命名敏感词
	* @param id
	* @param name
	* @throws Exception
	 */
	public void reName(Long id, String name) throws Exception;

	/**
	 * 
	* @Title: delete 
	* @Description: 删除敏感词
	* @param ids
	* @throws Exception
	 */
	public void delete(List<Long> ids) throws Exception;

	/**
	 * 
	* @Title: queryList 
	* @Description: 查询敏感词列表
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<SensitiveWord> queryList(PageRequest<GlobalSensitiveWordQueryRequest> pageRequest) throws Exception;

	public Map<Long, Long> queryByType() throws Exception;

	/** 
	* @Title: 根据敏感词类型获取敏感词列表
	* @param words
	* @return
	* @throws Exception
	*/
	public List<SensitiveWord> quaryListByType(Long aa) throws Exception;

    /**
     * 匹配敏感词
     * @param content 内容
     * @param type 类型 register, adv, help, message, comment, service
     * @return
     */
    public void match(String content, SensitiveWord.Type type) throws SensitiveWordException;

    public String replace(String content, SensitiveWord.Type type);
}
