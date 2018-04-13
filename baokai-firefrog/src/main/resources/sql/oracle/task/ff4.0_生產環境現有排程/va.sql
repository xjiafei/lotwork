SET ECHO OFF
SET TERMOUT OFF
SET TRIMSPOOL ON
SET PAGESIZE 0
SET LINESIZE 2000
SET FEEDBACK OFF
SET TIMING OFF
column filename new_val filename

select 'va_' || to_char(sysdate-1, 'yyyymmdd' )||'.txt' filename from dual;

ALTER SESSION SET CURRENT_SCHEMA=FIREFOG;

spool &filename
select '交易流水号'||','||'用户名'||','||'时间'||','||'摘要'||','||'收入金额'||','||'冻结金额'||','||'支出金额'||','||'可用余额'||','||'备注' from dual;

SELECT    a.sn
       || ','
       || b.account
       || ','
       || TO_CHAR (a.gmt_created, 'YYYY/MM/DD HH24:MI')
       || ','
       || case substr(a.reason,4,4)
              when 'ADAL' then '充值'
              when 'ADML' then '充值'
              when 'AAXX' then '管理员增'
              when 'BDRX' then '撤销派奖'
              when 'BIRX' then '转入'
              when 'CEXX' then '客户理赔'
              when 'CFCX' then '撤单费用'
              when 'CRVC' then '投注退款'
              when 'CWCR' then '提现退回'
              when 'CWCS' then '提现'
              when 'CWTF' then '提现'
              when 'CWTR' then '提现退回'
              when 'CWTS' then '提现'
              when 'DAXX' then '管理员减'
              when 'DVCB' then '投注扣款'
              when 'DVCN' then '投注扣款'
              when 'IPXX' then '平台奖励'
              when 'MDAX' then '充值'
              when 'PDXX' then '奖金派送'
              when 'PGXX' then '活动礼金'
              when 'RBRC' then '充值让利'
              when 'RHAX' then '投注返点'
              when 'RRHA' then '撤销返点'
              when 'RRSX' then '撤销返点'
              when 'RRXX' then '转入'
              when 'RSXX' then '投注返点'
              when 'SCDX' then '小额扣减'
              when 'SCRX' then '小额接收'
              when 'SOSX' then '转出'
              when 'WPXX' then '转出'
              when 'TPXX' then '来自旧平台'
           end 
       || ','
       || CASE
             WHEN (a.CT_BAL - a.BEFOR_BAL) > 0 THEN (a.CT_BAL - a.BEFOR_BAL) / 10000
             WHEN (a.CT_BAL - a.BEFOR_BAL) < 0 THEN NULL
          END
       || ','
       || (a.CT_DAMT - a.BEFORE_DAMT)
       || ','
       || CASE
             WHEN (a.CT_BAL - a.BEFOR_BAL) > 0 THEN NULL
             WHEN (a.CT_BAL - a.BEFOR_BAL) < 0 AND (a.CT_DAMT - a.BEFORE_DAMT) < 0 THEN (a.CT_DAMT - a.BEFORE_DAMT) / 10000
             WHEN (a.CT_BAL - a.BEFOR_BAL) < 0 AND (a.CT_DAMT - a.BEFORE_DAMT) = 0 AND (a.CT_BAL - a.BEFOR_BAL) != 0 THEN (a.CT_BAL - a.BEFOR_BAL) / 10000
             WHEN (a.CT_BAL - a.BEFOR_BAL) < 0 AND (a.CT_DAMT - a.BEFORE_DAMT) = 0 AND (a.CT_BAL - a.BEFOR_BAL) = 0  THEN NULL
          END
       || ','
       || a.CT_BAL
       || ','
       || ''
 FROM fund_change_log a, user_customer b
WHERE a.user_id = b.id AND a.gmt_created between trunc(SYSDATE - 1) and trunc(SYSDATE)
ORDER BY a.gmt_created DESC
;

SPOOL OFF

exit;
