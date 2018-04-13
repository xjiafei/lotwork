package com.winterframework.firefrog.global.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.exception.SensitiveWordException;
import com.winterframework.firefrog.global.dao.GlobalSensitiveWordDao;
import com.winterframework.firefrog.global.entity.SensitiveWord;
import com.winterframework.firefrog.global.service.GlobalSensitiveWordService;
import com.winterframework.firefrog.global.web.dto.GlobalSensitiveWordQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("globalSensitiveWordServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class GlobalSensitiveWordServiceImpl implements GlobalSensitiveWordService {

	@Resource(name = "globalSensitiveWordDaoImpl")
	private GlobalSensitiveWordDao globalSensitiveWordDao;

	@Override
	public String save(List<SensitiveWord> words) throws Exception {
		List<String> wordList = new ArrayList<String>();
		SensitiveWord.Type type =null;
		for (SensitiveWord word : words) {
			wordList.add(word.getWord());
			type = word.getType();
		}
		List<String> exsitsList = globalSensitiveWordDao.selectWord(wordList,type.getVal());
		if (!exsitsList.isEmpty()) {
			StringBuffer sb =new StringBuffer(50);
			for (String string : exsitsList) {
				sb.append(string).append(",");
			}
			if(sb.toString().endsWith(",")){
				sb.delete(sb.length()-1,sb.length());
			}
			return sb.toString();
		}
		globalSensitiveWordDao.saveWord(words);
		return null;
	}

	@Override
	public void reName(Long id, String name) throws Exception {
		globalSensitiveWordDao.reNameWord(id, name);
	}

	@Override
	public void delete(List<Long> ids) throws Exception {
		for (Long id : ids) {
			globalSensitiveWordDao.deleteWord(id);
		}
	}

	@Override
	public Page<SensitiveWord> queryList(PageRequest<GlobalSensitiveWordQueryRequest> pageRequest) throws Exception {
		return globalSensitiveWordDao.selectWord(pageRequest);
	}

	@Override
	public Map<Long, Long> queryByType() throws Exception {
		return globalSensitiveWordDao.queryByType();
	}

	@Override
	public List<SensitiveWord> quaryListByType(Long type) throws Exception {
		return globalSensitiveWordDao.quaryListByType(type);
	}

    @Override
    public void match(String content, SensitiveWord.Type type) throws SensitiveWordException{
        if(!StringUtils.isNotEmpty(content)) return;
        List<SensitiveWord> wrodList = globalSensitiveWordDao.quaryListByType(type.getVal());
        for (SensitiveWord sensitiveWord : wrodList) {
            if (content.trim().indexOf(sensitiveWord.getWord().trim())>-1) {
                throw new SensitiveWordException();
            }
        }
    }

    @Override
    public String replace(String content, SensitiveWord.Type type) {
        if(!StringUtils.isNotEmpty(content)) return content;
        List<SensitiveWord> wrodList = globalSensitiveWordDao.quaryListByType(type.getVal());
        content = content.trim();
        for (SensitiveWord sensitiveWord : wrodList) {
            content = content.replaceAll(sensitiveWord.getWord().trim(), makeStar(sensitiveWord.getWord().trim().length()));
        }
        return content;
    }

    private String makeStar(int size) {
        StringBuilder star = new StringBuilder("");
        while (size > 0) {
            star.append("*");
            size--;
        }
        return star.toString();
    }
}
