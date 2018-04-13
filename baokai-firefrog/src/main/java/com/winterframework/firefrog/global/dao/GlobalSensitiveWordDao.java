package com.winterframework.firefrog.global.dao;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.global.entity.SensitiveWord;
import com.winterframework.firefrog.global.web.dto.GlobalSensitiveWordQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: GlobalSensitiveWordDao 
* @Description: 敏感词数据库操作接口
* @author Tod
* @date 2013-10-15 下午4:55:55 
*
 */
public interface GlobalSensitiveWordDao {

	/**
	 * 
	* @Title: saveWord 
	* @Description: 保存敏感词
	* @param word
	* @throws Exception
	 */
	public void saveWord(List<SensitiveWord> word) throws Exception;

	/**
	 * 
	* @Title: reNameWord 
	* @Description: 重命名敏感词
	* @param id
	* @param name
	* @throws Exception
	 */
	public void reNameWord(Long id, String name) throws Exception;

	/**
	 * 
	* @Title: deleteWord 
	* @Description: 删除敏感词
	* @param id
	* @throws Exception
	 */
	public void deleteWord(Long id) throws Exception;
	
	public Map<Long,Long> queryByType() throws Exception;

	/**
	 * 
	* @Title: selectWord 
	* @Description: 查询敏感词列表
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<SensitiveWord> selectWord(PageRequest<GlobalSensitiveWordQueryRequest> pageRequest) throws Exception;
	
	/** 
	 * 根据类型和关键词获取数据库中存在的关键词
	*/
	public List<String> selectWord(List<String> wordList,Long type) throws Exception;

	/** 
	 * 根据敏感词类型获取敏感词列表
	* @param type
	 * @return 
	*/
	public List<SensitiveWord> quaryListByType(Long type);

}
