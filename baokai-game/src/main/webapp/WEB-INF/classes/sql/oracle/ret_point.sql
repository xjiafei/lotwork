/* Formatted on 2010/02/26 08:42 (Formatter Plus v4.8.8) */  
CREATE OR REPLACE TYPE TY_RET_POINT AS OBJECT (  
   order_id   NUMBER,
   staus      NUMBER,  
   user_id   NUMBER ,
   point    NUMBER,
   point_total NUMBER
);  
/* Formatted on 2010/02/26 08:43 (Formatter Plus v4.8.8) */  
CREATE OR REPLACE TYPE TY_TABLE_RET_POINT  AS  TABLE OF TY_RET_POINT;  

CREATE OR REPLACE FUNCTION fn_view_RetPoints RETURN TY_TABLE_RET_POINT
  PIPELINED is
  userIdChain VARCHAR2(1000);
  pointChain  VARCHAR2(1000);
  pointChain2 VARCHAR2(1000);
  rowValue    TY_RET_POINT;
  userId      NUMBER;
  point       NUMBER;
  point_sum   NUMBER;
BEGIN
  for ret_point_row in (select * from game_ret_point) loop
    userIdChain := ret_point_row.ret_user_chain || ',';
    pointChain  := ret_point_row.ret_point_chain || ',';
    pointChain2 := pointChain;
    point_sum:=0;
    while instr(pointChain2, ',') > 0 loop
      point_sum   := point_sum +
                     to_number(substr(pointChain2,
                                      1,
                                      instr(pointChain2, ',') - 1));
      pointChain2 := substr(pointChain2, instr(pointChain2, ',') + 1);
    end loop;
    while instr(userIdChain, ',') > 0 loop
      userId      := to_number(substr(userIdChain,
                                      1,
                                      instr(userIdChain, ',') - 1));
      point       := to_number(substr(pointChain,
                                      1,
                                      instr(pointChain, ',') - 1));
      rowValue    := TY_RET_POINT(ret_point_row.game_order_id,
                                  ret_point_row.status,
                                  userId,
                                  point,
                                  point_sum);
      userIdChain := substr(userIdChain, instr(userIdChain, ',') + 1);
      pointChain  := substr(pointChain, instr(pointChain, ',') + 1);
      PIPE ROW(rowValue);
    end loop;
  
  END LOOP;
  RETURN;
END; 


CREATE OR REPLACE VIEW VIEW_USER_INCOME AS
SELECT  a.ID,
 a.account,
 a.user_chain,
 a.parent_id,
 a.IS_FREEZE,
 a.user_lvl,
 c.BET_TYPE_CODE,
 c.MONEY_MODE,
 c.CREATE_TIME,
 c.LOTTERYID,
 (CASE o.STATUS
       WHEN 2
       THEN 1
       WHEN 3
       THEN 1
        ELSE
        0
     END)*(CASE o.userid
       WHEN r.USER_ID
       THEN 1
        ELSE
        0
     END)*decode(c.TOTAMOUNT,null,0,c.TOTAMOUNT) as TOTAMOUNT ,
 (CASE o.STATUS
       WHEN 2
       THEN 1
        ELSE
        0
     END)*(CASE o.userid
       WHEN r.USER_ID
       THEN 1
        ELSE
        0
     END)*decode(c.EVALUATE_WIN,null,0,c.EVALUATE_WIN) as EVALUATE_WIN ,
 (CASE o.STATUS
       WHEN 2
       THEN 1
       WHEN 3
       THEN 1
        ELSE
        0
     END)*decode(c.TOTAMOUNT,null,0,c.TOTAMOUNT)/o.TOTAMOUNT *  r.point  as Ret

       FROM user_customer a, GAME_SLIP c,GAME_ORDER o ,table(fn_view_RetPoints) r
      WHERE a.ID = r.USER_ID(+)
        AND c.ORDERID = o.ID(+)
        AND o.STATUS != 4
		AND o.ID = r.ORDER_ID;
  
  
CREATE OR REPLACE VIEW VIEW_USER_AGENT_INCOME AS
SELECT  a.ID,
 a.account,
 a.user_chain,
 a.parent_id,
 a.IS_FREEZE,
 a.user_lvl,
 c.BET_TYPE_CODE,
 c.MONEY_MODE,
 c.CREATE_TIME,
 c.LOTTERYID,
 (CASE o.STATUS
       WHEN 2
       THEN 1
       WHEN 3
       THEN 1
        ELSE
        0
     END)*(CASE o.userid
       WHEN r.USER_ID
       THEN 1
        ELSE
        0
     END)*decode(c.TOTAMOUNT,null,0,c.TOTAMOUNT) as TOTAMOUNT ,
 (CASE o.STATUS
       WHEN 2
       THEN 1
        ELSE
        0
     END)*(CASE o.userid
       WHEN r.USER_ID
       THEN 1
        ELSE
        0
     END)*decode(c.EVALUATE_WIN,null,0,c.EVALUATE_WIN) as EVALUATE_WIN ,
 (CASE o.STATUS
       WHEN 2
       THEN 1
       WHEN 3
       THEN 1
        ELSE
        0
     END)*decode(c.TOTAMOUNT,null,0,c.TOTAMOUNT)/o.TOTAMOUNT *  r.point  as Ret
       FROM user_customer a, user_customer b, GAME_SLIP c,GAME_ORDER o ,table(fn_view_RetPoints) r
      WHERE b.ID = r.USER_ID(+)
        AND b.user_chain LIKE a.user_chain || '%'
        AND c.ORDERID = o.ID(+)
         AND o.STATUS != 4
        AND o.ID = r.ORDER_ID;

  
