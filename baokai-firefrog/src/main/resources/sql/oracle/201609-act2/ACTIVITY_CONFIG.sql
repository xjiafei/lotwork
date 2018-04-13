alter table ACTIVITY_CONFIG modify MAX_PRIZE number ;

alter table ACTIVITY_CONFIG modify MIN_PRIZE number ;

Insert into FIREFOG.ACTIVITY_CONFIG
   (ID, BEGIN_TIME, END_TIME, MAX_PRIZE, MIN_PRIZE, 
    MEMO, ODDS, ACTIVITYID, TYPE, RULE)
 Values
   (SEQ_ACTIVITY_CONFIG.Nextval, sysdate, sysdate, 0, 0, 
    '金玉满堂', 0, (select  ID from activity where activity_code='160902'), '9-0', '{"normal":"9990000","vip":"9990000"}');
Insert into FIREFOG.ACTIVITY_CONFIG
   (ID, BEGIN_TIME, END_TIME, MAX_PRIZE, MIN_PRIZE, 
    MEMO, ODDS, ACTIVITYID, TYPE, RULE)
 Values
   (SEQ_ACTIVITY_CONFIG.Nextval, sysdate, sysdate, 17050000000, 17050000000, 
    '凤凰发财', 0, (select  ID from activity where activity_code='160902'), '9-1', '{"normal":"51990000","vip":"59990000"}');
Insert into FIREFOG.ACTIVITY_CONFIG
   (ID, BEGIN_TIME, END_TIME, MAX_PRIZE, MIN_PRIZE, 
    MEMO, ODDS, ACTIVITYID, TYPE, RULE)
 Values
   (SEQ_ACTIVITY_CONFIG.Nextval, sysdate, sysdate, 17050000000,11250000000, 
    '金猴献寿', 0, (select  ID from activity where activity_code='160902'), '9-2', '{"normal":"36990000","vip":"39990000"}');
Insert into FIREFOG.ACTIVITY_CONFIG
   (ID, BEGIN_TIME, END_TIME, MAX_PRIZE, MIN_PRIZE, 
    MEMO, ODDS, ACTIVITYID, TYPE, RULE)
 Values
   (SEQ_ACTIVITY_CONFIG.Nextval, sysdate, sysdate, 11250000000,5850000000,  
    '福禄寿喜', 0, (select  ID from activity where activity_code='160902'), '9-3', '{"normal":"16990000","vip":"19990000"}');
Insert into FIREFOG.ACTIVITY_CONFIG
   (ID, BEGIN_TIME, END_TIME, MAX_PRIZE, MIN_PRIZE, 
    MEMO, ODDS, ACTIVITYID, TYPE, RULE)
 Values
   (SEQ_ACTIVITY_CONFIG.Nextval, sysdate, sysdate, 5850000000,3050000000,
    '五福临门', 0, (select  ID from activity where activity_code='160902'), '9-4', '{"normal":"10990000","vip":"11990000"}');
Insert into FIREFOG.ACTIVITY_CONFIG
   (ID, BEGIN_TIME, END_TIME, MAX_PRIZE, MIN_PRIZE, 
    MEMO, ODDS, ACTIVITYID, TYPE, RULE)
 Values
   (SEQ_ACTIVITY_CONFIG.Nextval, sysdate, sysdate, 3050000000, 1250000000,
    '招财进宝', 0, (select  ID from activity where activity_code='160902'), '9-5', '{"normal":"5190000","vip":"5990000"}');
Insert into FIREFOG.ACTIVITY_CONFIG
   (ID, BEGIN_TIME, END_TIME, MAX_PRIZE, MIN_PRIZE, 
    MEMO, ODDS, ACTIVITYID, TYPE, RULE)
 Values
   (SEQ_ACTIVITY_CONFIG.Nextval, sysdate, sysdate, 1250000000, 350000000, 
    '吉祥如意', 0, (select  ID from activity where activity_code='160902'), '9-6', '{"normal":"1690000","vip":"1990000"}');
Insert into FIREFOG.ACTIVITY_CONFIG
   (ID, BEGIN_TIME, END_TIME, MAX_PRIZE, MIN_PRIZE, 
    MEMO, ODDS, ACTIVITYID, TYPE, RULE)
 Values
   (SEQ_ACTIVITY_CONFIG.Nextval, sysdate, sysdate, 350000000,50000000,
    '前程万里', 0, (select  ID from activity where activity_code='160902'), '9-7', '{"normal":"190000","vip":"290000"}');
COMMIT;
