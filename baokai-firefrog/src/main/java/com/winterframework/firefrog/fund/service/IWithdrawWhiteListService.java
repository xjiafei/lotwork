package com.winterframework.firefrog.fund.service;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.WithdrawWhiteList;
import com.winterframework.firefrog.fund.web.dto.WithdrawWhiteListRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * @author Lex
 * @ClassName: IWithdrawWhiteListService
 * @Description: 提现白名单
 * @date 2014/9/10 13:56
 */
public interface IWithdrawWhiteListService {
    /**
     * 根据账户批量删除白名单
     * @param account
     * @throws Exception
     */
    void deleteByAccount(List<String> accountList) throws Exception;

    /**
     * 批量保存白名单
     * @param whiteList
     * @throws Exception
     */
    void save(List<String> accountList,String account,String notes) throws Exception;

    /**
     * 分页查询
     * @param whiteListPageRequest
     * @return
     * @throws Exception
     */
    Response<List<WithdrawWhiteList>> queryPage(Request<WithdrawWhiteListRequest> request) throws Exception;
}
