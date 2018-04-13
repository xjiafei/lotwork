SET DEFINE OFF;

Insert into FIREFOG.ACTIVITY_CONFIG
   (ID, BEGIN_TIME, END_TIME, MAX_PRIZE, MIN_PRIZE, 
    MEMO, ODDS, ACTIVITYID, TYPE, RULE)
 Values
   (SEQ_ACTIVITY_CONFIG.Nextval, sysdate, sysdate, 15000000, 0, 
    '金秋凤凰千元大放送', 0, (select  ID from activity where activity_code='160903'), '0', '{"normal":"0","vip":"0"}');

Insert into FIREFOG.ACTIVITY_CONFIG
   (ID, BEGIN_TIME, END_TIME, MAX_PRIZE, MIN_PRIZE, 
    MEMO, ODDS, ACTIVITYID, TYPE, RULE)
 Values
   (SEQ_ACTIVITY_CONFIG.Nextval, sysdate, sysdate, 50000000, 15000000, 
    '金秋凤凰千元大放送', 0, (select  ID from activity where activity_code='160903'), '1', '{"normal":"580000","vip":"680000"}');
    
    Insert into FIREFOG.ACTIVITY_CONFIG
   (ID, BEGIN_TIME, END_TIME, MAX_PRIZE, MIN_PRIZE, 
    MEMO, ODDS, ACTIVITYID, TYPE, RULE)
 Values
   (SEQ_ACTIVITY_CONFIG.Nextval, sysdate, sysdate, 100000000, 50000000, 
    '金秋凤凰千元大放送', 0, (select  ID from activity where activity_code='160903'), '2', '{"normal":"1880000","vip":"2880000"}');


Insert into FIREFOG.ACTIVITY_CONFIG
   (ID, BEGIN_TIME, END_TIME, MAX_PRIZE, MIN_PRIZE, 
    MEMO, ODDS, ACTIVITYID, TYPE, RULE)
 Values
   (SEQ_ACTIVITY_CONFIG.Nextval, sysdate, sysdate, 500000000, 100000000, 
    '金秋凤凰千元大放送', 0, (select  ID from activity where activity_code='160903'), '3', '{"normal":"4880000","vip":"5880000"}');
    
Insert into FIREFOG.ACTIVITY_CONFIG
   (ID, BEGIN_TIME, END_TIME, MAX_PRIZE, MIN_PRIZE, 
    MEMO, ODDS, ACTIVITYID, TYPE, RULE)
 Values
   (SEQ_ACTIVITY_CONFIG.Nextval, sysdate, sysdate, -1, 500000000, 
    '金秋凤凰千元大放送', 0, (select  ID from activity where activity_code='160903'), '4', '{"normal":"26880000","vip":"28880000"}');
    


COMMIT;
