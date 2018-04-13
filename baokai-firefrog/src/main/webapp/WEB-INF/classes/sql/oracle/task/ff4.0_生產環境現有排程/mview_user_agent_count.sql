--TRUNCATE TABLE
TRUNCATE TABLE FIREFOG.USER_REGISTER_REPORT ;
TRUNCATE TABLE FIREFOG.FUND_CHANGE_LOG_REPORT ;

---INSERT TABLE
INSERT INTO USER_REGISTER_REPORT
     SELECT t.id,
            TRUNC (tt.register_date) AS DAY,
            TO_CHAR (tt.register_date, 'HH24') AS hh,
            COUNT (1) AS user_count
       FROM (    SELECT DISTINCT id, user_chain
                   FROM user_customer
             CONNECT BY PRIOR parent_id = id
             START WITH register_date >= trunc(sysdate,'hh24')-1/24) t,
            user_customer tt
      WHERE     t.id <> tt.id
            AND tt.user_chain LIKE t.user_chain || '%'
            AND tt.register_date >= trunc(sysdate,'hh24')-1/24
            AND tt.register_date < trunc(sysdate,'hh24')
   GROUP BY t.id,
            TRUNC (tt.register_date),
            TO_CHAR (tt.register_date, 'HH24');
COMMIT;	

INSERT INTO FUND_CHANGE_LOG_REPORT 		
  select /*+ INDEX (a IX_USERIDCREA)*/ a."ID",a.plan_code,a.note,a.ex_code,a."USER_ID",a."BEFOR_BAL",a."BEFORE_DAMT",a."CT_BAL",a."CT_DAMT",a."REASON",a."OPERATOR",a."FUND_ID",a."SN",a."OLD_FREEZE_AMT",a."CURRENT_FREEZE_AMT",a."GMT_CREATED",a."FUND_SN",a."ISACLUSER",a."ISVISIBLEBYFRONTUSER",a.before_avail_bal,a.ct_avail_bal,b.account,b.user_chain
    from fund_change_log a,user_customer b
   where a.user_id = b.id
   and a.gmt_created between trunc(sysdate,'hh24')-1/24 and trunc(sysdate,'hh24');
COMMIT;   
   
INSERT INTO firefog.mview_user_agent_count
   SELECT 1 AS id,
          NVL (a.user_id, b.id) AS user_id,
          NVL (a.day, b.day) AS day,
          NVL (a.hh, b.hh) AS time,
          NVL (a.charge, 0) AS charge,
          NVL (a.bet, 0) AS bet,
          NVL (a.withdraw, 0) AS with_draw,
          NVL (a.ret, 0) AS reward,
          NVL (b.user_count, 0) AS new_User
     FROM (  SELECT tt.id AS user_id,
                    TO_CHAR (GMT_CREATED, 'HH24') AS hh,
                    TRUNC (GMT_CREATED) AS day,
                    SUM (
                       DECODE (t.REASON,
                               'FD,ADAL,null,3', ct_bal - befor_bal,
                               'FD,ADML,null,8', ct_bal - befor_bal,
                               'FD,MDAX,null,5', ct_bal - befor_bal,
                               'OT,AAXX,null,3', ct_bal - befor_bal,
                               0))
                       AS charge,
                    SUM (
                       DECODE (t.REASON,
                               'GM,DVCN,null,2', before_damt - ct_damt,
                               'GM,DVCB,null,2', before_damt - ct_damt,
                               0))
                       AS bet,
                    SUM (
                       DECODE (t.REASON,
                               'FD,CWCS,null,6', before_damt - ct_damt,
                               'FD,CWCS,null,5', before_damt - ct_damt,
                               'FD,CWTS,null,5', before_damt - ct_damt,
                               'FD,CWTS,null,6', before_damt - ct_damt,
                               0))
                       AS withdraw,
                    SUM (
                       DECODE (t.REASON,
                               'GM,RSXX,null,1', ct_bal - befor_bal,
                               'GM,RHAX,null,2', ct_bal - befor_bal,
                               0))
                       AS ret
               FROM FUND_CHANGE_LOG_REPORT t, user_customer tt
              WHERE     (t.USER_ID - tt.id > 0 OR tt.id - t.USER_ID > 0)
                    AND t.user_chain LIKE tt.user_chain || '%'
           GROUP BY tt.id, TO_CHAR (GMT_CREATED, 'HH24'), TRUNC (GMT_CREATED))
          a
          FULL JOIN (SELECT rt.id,
                            rt.day AS day,
                            rt.hh AS HH,
                            rt.user_count AS user_count
                       FROM user_register_report rt) b
             ON a.user_id = b.id AND a.day = b.day AND a.hh = b.hh;
COMMIT; 

delete from  firefog.mview_user_agent_count where trunc(day) < trunc(sysdate - 14) ; 			 
