package com.winterframework.firefrog.help.dao;

import java.util.List;

import com.winterframework.firefrog.help.entity.HelpKnowledgeLink;

public interface IHelpKnowledgeLinkDao {

	public void saveLink(HelpKnowledgeLink link) throws Exception;

	public void saveLink(List<HelpKnowledgeLink> links, Long helpId) throws Exception;

	public void updateLink(HelpKnowledgeLink link) throws Exception;

	public void deleteLink(Long id) throws Exception;

	public List<HelpKnowledgeLink> selectByHelpId(Long id) throws Exception;

	public void deleteLinkByKnowledgeId(Long kid) throws Exception;
}
