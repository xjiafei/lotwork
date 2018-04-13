  /*
	新彩种的返点由同系列的老彩种复制
  e.g.
  begin
    P_INIT_USER_RET_POINT(99306，99305);
    --commit;
   end;
  */

CREATE OR REPLACE 
PROCEDURE P_INIT_USER_RET_POINT_COPY (p_lotteryId IN VARCHAR2 DEFAULT '99306',p_source_lotteryId in VARCHAR2 DEFAULT '99305')
AS
cursor cuserAward is select DIRECT_RET,THREEONE_RET,USERID,STATUS,BET_TYPE,AWARD_NAME,SET_TYPE,SYS_AWARD_GROUP_ID from 
(select t1.*,t2.AWARD_NAME from game_award_user_group t1,GAME_AWARD_GROUP t2 WHERE t1.lotteryid = p_source_lotteryId and t1.SYS_AWARD_GROUP_ID=t2.id);
n_decRet number:=0;
n_threeoneRet number:=0;
n_userId number:=0;
n_awardName VARCHAR2(256);
n_status number;
n_count number:=0;
n_setType number:=0;
n_betType number:=0;
n_sysAwardGroup number:=0;
BEGIN
	for cur_rec in cuserAward loop
	n_decRet:=cur_rec.DIRECT_RET;
	n_threeoneRet:=cur_rec.THREEONE_RET;
	n_userId:=cur_rec.USERID;
	n_status :=cur_rec.STATUS;
	n_awardName :=cur_rec.AWARD_NAME;
	n_setType :=cur_rec.SET_TYPE;
	n_betType :=cur_rec.BET_TYPE;
	n_sysAwardGroup :=cur_rec.SYS_AWARD_GROUP_ID;
	 select count(1)
        into n_count
        from game_award_user_group
       where userid = n_userId
         and lotteryId = p_lotteryId
				 and BET_TYPE = n_betType
				 and STATUS = n_status
				 and SYS_AWARD_GROUP_ID=(select id from GAME_AWARD_GROUP where lotteryid=p_lotteryId and AWARD_NAME=n_awardName);
      if n_count > 0 then
        --一个奖金组 (无不定位返点）
        update game_award_user_group
           set direct_ret = n_decRet, threeone_ret = n_threeoneRet, update_time = sysdate
         where lotteryid = p_lotteryId
           and userid = n_userId
					and BET_TYPE = n_betType
				 and STATUS = n_status
				 and SYS_AWARD_GROUP_ID=(select id from GAME_AWARD_GROUP where lotteryid=p_lotteryId and AWARD_NAME=n_awardName);
      else
	dbms_output.put_line(n_userId);
        insert into game_award_user_group
          (ID,
           LOTTERYID,
           DIRECT_RET,
           THREEONE_RET,
           CREATE_TIME,
           UPDATE_TIME,
           USERID,
           SET_TYPE,
           STATUS,
           BET_TYPE,
           SYS_AWARD_GROUP_ID)
        VALUES
          (SEQ_GAME_AWARD_USER_GROUP_ID.Nextval,
           p_lotteryId,
           n_decRet,
           n_threeoneRet,
           sysdate,
           sysdate,
           n_userId,
           n_setType,
           n_status,
           n_betType,
           (select id from GAME_AWARD_GROUP where lotteryid=p_lotteryId and AWARD_NAME=n_awardName));

      end if;
	end loop;
exception
  when others then
    dbms_output.put_line(sqlerrm);
END P_INIT_USER_RET_POINT_COPY;
