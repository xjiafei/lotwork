package com.winterframework.firefrog.fund.dao.impl;

import com.winterframework.firefrog.fund.dao.IWithdrawWhiteListDao;
import com.winterframework.firefrog.fund.dao.vo.WithdrawWhiteList;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;
import org.springframework.stereotype.Repository;

/**
 * @author Lex
 * @ClassName: WithdrawWhiteListDaoImpl
 * @Description:
 * @date 2014/9/10 13:32
 */
@Repository("withdrawWhiteListDaoImpl")
public class WithdrawWhiteListDaoImpl extends BaseIbatis3Dao<WithdrawWhiteList> implements IWithdrawWhiteListDao {
    @Override
    public void deleteByAccount(String account) {
        sqlSessionTemplate.delete("com.winterframework.firefrog.fund.dao.vo.WithdrawWhiteList.deleteByAccount", account);
    }

    @Override
    public WithdrawWhiteList query(String account) {
        return null;
    }

}
