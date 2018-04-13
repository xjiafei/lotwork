package com.winterframework.firefrog.fund.dao;

import com.winterframework.firefrog.fund.dao.vo.WithdrawWhiteList;
import com.winterframework.orm.dal.ibatis3.BaseDao;


/**
 * @author Lex
 * @ClassName: IWithdrawWhiteListDao
 * @Description: 提现白名单
 * @date 2014/9/10 13:24
 */
public interface IWithdrawWhiteListDao  extends BaseDao<WithdrawWhiteList> {
    /**
     * 删除白名单
     * @param account 用户帐号
     */
    void deleteByAccount(String account);


    /**
     * 查询用户白名单
     * @param account 用户帐号
     * @return
     */
    WithdrawWhiteList query(String account);
}
