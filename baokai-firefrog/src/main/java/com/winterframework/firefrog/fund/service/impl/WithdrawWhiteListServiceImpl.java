package com.winterframework.firefrog.fund.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.fund.dao.IFundDao;
import com.winterframework.firefrog.fund.dao.impl.WithdrawWhiteListDaoImpl;
import com.winterframework.firefrog.fund.dao.vo.WithdrawWhiteList;
import com.winterframework.firefrog.fund.service.IWithdrawWhiteListService;
import com.winterframework.firefrog.fund.web.dto.WithdrawWhiteListRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * @author Lex
 * @ClassName: WithdrawWhiteListServiceImpl
 * @Description: 提现白名单
 * @date 2014/9/10 14:01
 */
@Service("withdrawWhiteListServiceImpl")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class WithdrawWhiteListServiceImpl implements IWithdrawWhiteListService {
	@Resource(name = "withdrawWhiteListDaoImpl")
	private WithdrawWhiteListDaoImpl iWithdrawWhiteListDao;

	@Override
	public void deleteByAccount(List<String> accountList) throws Exception {
		for (String account : accountList) {
			iWithdrawWhiteListDao.deleteByAccount(account);
		}
	}

	@Override
	public void save(List<String> accountList, String account, String note) throws Exception {
		List<WithdrawWhiteList> list = new ArrayList<WithdrawWhiteList>();
		for (String ac : accountList) {
			WithdrawWhiteList ww = new WithdrawWhiteList();
			ww.setAccount(ac);
			ww.setOperator(account);
			ww.setNote(note);
			ww.setGmtCreated(new Date(System.currentTimeMillis()));
			fundDao.addWhite(ac);
			list.add(ww);
		}
		iWithdrawWhiteListDao.insert(list);
	}
	@Resource(name = "fundDaoImpl")
	protected IFundDao fundDao;

	@Override
	public Response<List<WithdrawWhiteList>> queryPage(Request<WithdrawWhiteListRequest> request) throws Exception {

		PageRequest<WithdrawWhiteList> pr = PageRequest.getRestPageRequest(request.getBody().getPager().getStartNo(),
				request.getBody().getPager().getEndNo());
		Response<List<WithdrawWhiteList>> resp = new Response<List<WithdrawWhiteList>>(request);
		pr.setSortColumns("gmtCreated desc");
		WithdrawWhiteList wd = new WithdrawWhiteList();
		if (request.getBody().getParam().getAccountList() != null
				&& request.getBody().getParam().getAccountList().size() > 0)
			wd.setAccount(request.getBody().getParam().getAccountList().get(0));
		pr.setSearchDo(wd);

		Page<WithdrawWhiteList> urls = iWithdrawWhiteListDao.getAllByPage(pr);
		ResultPager resultPager = new ResultPager(urls.getThisPageFirstElementNumber(),
				urls.getThisPageLastElementNumber(), urls.getTotalCount());
		resp.setResultPage(resultPager);
		resp.setResult(urls.getResult());

		return resp;
	}
}
