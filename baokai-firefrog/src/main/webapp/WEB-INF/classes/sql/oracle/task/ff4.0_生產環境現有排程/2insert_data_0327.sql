spool 2insert_0327.log
set timing on feedback on echo on

INSERT INTO  FIREFOG.MVIEW_TOP_CHARGE_WITHDRW_RPT   
   SELECT m."ACCOUNT",
          m."GAME_1",
          m."GAME_2",
          m."GAME_3",
          m."GAME_4",
          m."GAME_5",
          m."MONEY_1",
          m."MONEY_2",
          m."MONEY_3",
          m."MONEY_4",
          m."DAY",
          c.money_5
     FROM (  SELECT /*+ INDEX (b IX_USERIDCREA ) INDEX (a SYS_C0013012)*/   a.top_acc AS account,
                    SUM (
                       DECODE (b.reason,
                               'OT,AAXX,null,3', ct_bal - befor_bal,
                               0))
                       AS game_1,                                        ---ok
                    SUM (
                       DECODE (b.REASON,
                               'PM,PGXX,null,3', ct_bal - befor_bal,
                               0))
                       AS game_2,                                        ---ok
                    SUM (
                       DECODE (b.REASON,
                               'PM,IPXX,null,3', ct_bal - befor_bal,
                               0))
                       AS game_3,                                        ---ok
                    SUM (
                       DECODE (b.REASON,
                               'OT,DAXX,null,3', befor_bal - ct_bal,
                               0))
                       AS game_4,                                        ---ok
                    SUM (
                       DECODE (b.REASON,
                               'PM,RBRC,null,1', ct_bal - befor_bal,
                               0))
                       AS game_5,                                        ---ok
                    SUM (
                       DECODE (b.REASON,
                               'FD,ADAL,null,3', ct_bal - befor_bal,
                               'FD,ADML,null,8', ct_bal - befor_bal,
                               0))
                       AS money_1,
                    SUM (
                       DECODE (b.REASON,
                               'FD,MDAX,null,5', ct_bal - befor_bal,
                               0))
                       AS money_2,                                       ---ok
                    SUM (
                       DECODE (b.REASON,
                               'FD,CWTS,null,5', before_damt - ct_damt,
                               'FD,CWTS,null,6', before_damt - ct_damt,
                               0))
                       AS money_3,                                        --ok
                    SUM (
                       DECODE (b.REASON,
                               'FD,CWCS,null,5', before_damt - ct_damt,
                               'FD,CWCS,null,4', before_damt - ct_damt,
                               0))
                       AS money_4,                                        --ok
                    TRUNC (b.gmt_created) AS day
               FROM VIEW_RPT_USER a, fund_change_log b
              WHERE a.user_id = b.user_id AND b.gmt_created BETWEEN TRUNC (SYSDATE - 1) AND TRUNC (SYSDATE)
           GROUP BY a.top_acc, TRUNC (b.gmt_created)) m
          LEFT JOIN
          (  SELECT b.top_acc AS rcv_account,
                    SUM (NVL (MC_AMOUNT, 0)) AS money_5,
                    TRUNC (mc_notice_time) AS day
               FROM FUND_MANUAL_DEPOSIT t, VIEW_RPT_USER b
              WHERE type_id = 1 AND status IN (5, 6) AND t.rcv_id = b.user_id
           GROUP BY b.top_acc, TRUNC (mc_notice_time)) c
             ON m.account = c.rcv_account AND m.day = c.day;

COMMIT;

delete from  FIREFOG.MVIEW_TOP_CHARGE_WITHDRW_RPT where trunc(day) < trunc(sysdate - 14) ;
commit;

spool off;


