drop table view_z;
create table view_z
as
   SELECT o.userid ouserid,
          r.user_id ruserid,
          c.BET_TYPE_CODE,
          c.MONEY_MODE,
          c.CREATE_TIME,
          c.LOTTERYID,
            (CASE o.STATUS WHEN 2 THEN 1 WHEN 3 THEN 1 ELSE 0 END)
          * (CASE o.userid WHEN r.USER_ID THEN 1 ELSE 0 END)
          * DECODE (c.TOTAMOUNT, NULL, 0, c.TOTAMOUNT)
             AS TOTAMOUNT,
            (CASE o.STATUS WHEN 2 THEN 1 ELSE 0 END)
          * (CASE o.userid WHEN r.USER_ID THEN 1 ELSE 0 END)
          * DECODE (c.EVALUATE_WIN, NULL, 0, c.EVALUATE_WIN)
             AS EVALUATE_WIN,
            (CASE o.STATUS WHEN 2 THEN 1 WHEN 3 THEN 1 ELSE 0 END)
          * DECODE (c.TOTAMOUNT, NULL, 0, c.TOTAMOUNT)
          / o.TOTAMOUNT
          * r.point
             AS Ret
     FROM  
         (SELECT *
             FROM game_slip
            WHERE create_time >= trunc(sysdate-1)
              AND create_time < trunc(sysdate)) c,
          (SELECT *
             FROM game_order
            WHERE TRUNC (order_time) = trunc(sysdate-1)) o,
          TABLE (fn_view_retpoints (o.ID)) r
     WHERE  c.ORDERID = o.ID
         AND o.STATUS != 4 
;

         
drop table view_a;

create table view_a as
 select a.ID,
           a.account,
           a.user_chain,
           a.parent_id,
           a.IS_FREEZE,
           a.user_lvl, 
                     z.BET_TYPE_CODE,
                     z.MONEY_MODE   ,
                    z.CREATE_TIME  ,
                    z.LOTTERYID    ,
                    z.TOTAMOUNT    ,
                    z.EVALUATE_WIN ,
                    z.RET          
 FROM user_customer a,
          user_customer b,
          view_z z
     WHERE b.ID = z.rUSERID(+)
        AND b.user_chain LIKE a.user_chain || '%'
        and z.ouserid=b.id;
     

INSERT INTO user_agent_income
            (ID, ACCOUNT, user_chain, parent_id, is_freeze, user_lvl,
             bet_type_code, money_mode, create_time, lotteryid, totamount,
             evaluate_win, ret)
   SELECT   ID, ACCOUNT, user_chain, parent_id, is_freeze, user_lvl,
            bet_type_code, money_mode,trunc(create_time), lotteryid, SUM (totamount)
                                                                 AS totamount,
            SUM (evaluate_win) AS evaluate_win, SUM (ret) AS ret            
       FROM view_a
   GROUP BY ID,
            ACCOUNT,
            user_chain,
            parent_id,
            is_freeze,
            user_lvl,
            bet_type_code,
            money_mode,
            lotteryid,
            trunc(create_time);
commit;

                     
