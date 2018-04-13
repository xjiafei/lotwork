create or replace procedure P_INIT_USER_RET_POINT_SUPER(p_lotteryId varchar2,p_awardGroupId number,p_step1 number,p_step2 number,p_splitLvl number,p_agentTotalOld number,p_agentTotalNew number) is
  /*
  已开户用户（非总代）返点初始化
  需求：
  老用户和老注册链接------总返点p_agentTotal(8%)，总代 1代 2代保持返点差1(p_step1=2%)，(p_splitLvl=2)代以下按照返点差2(p_step2=0.5%)的规则，需要在注册链接内赋予对应的返点值。
  新注册链接 --- 开户的新用户，按照4.0现有的规则进行设置。
  仅允许执行一次（无法识别首次初始化之后的数据有没被修改）
  e.g.
  begin
    P_INIT_USER_RET_POINT_SUPER(99101,1, 10,10,99999,100,100);
	P_INIT_USER_RET_POINT_SUPER(99101,10,10,10,99999,100,100);
	P_INIT_USER_RET_POINT_SUPER(99101,12,10,10,99999,100,100);
	                                                          
	P_INIT_USER_RET_POINT_SUPER(99102,7, 10,10,99999,100,100);
	P_INIT_USER_RET_POINT_SUPER(99102,14,10,10,99999,100,100);
	P_INIT_USER_RET_POINT_SUPER(99102,17,10,10,99999,100,100);
	                                                          
	P_INIT_USER_RET_POINT_SUPER(99103,19,10,10,99999,100,100);
	P_INIT_USER_RET_POINT_SUPER(99103,20,10,10,99999,100,100);
	P_INIT_USER_RET_POINT_SUPER(99103,21,10,10,99999,100,100);
	                                                          
	P_INIT_USER_RET_POINT_SUPER(99104,36,10,10,99999,100,100);
	P_INIT_USER_RET_POINT_SUPER(99104,37,10,10,99999,100,100);
	P_INIT_USER_RET_POINT_SUPER(99104,38,10,10,99999,100,100);
	                                                          
	P_INIT_USER_RET_POINT_SUPER(99105,5, 10,10,99999,100,100);
	P_INIT_USER_RET_POINT_SUPER(99105,13,10,10,99999,100,100);
	P_INIT_USER_RET_POINT_SUPER(99105,16,10,10,99999,100,100);

    --commit;
   end;
  */
  /*
   更新超级对子返点：有则更新超级对子返点字段 没有则insert直选和不定位为0
  */
  cursor cur_user is
    select id, user_chain, source from user_customer where user_lvl <> 0;
  n_userId       number;
  v_userChain    user_customer.user_chain%type;
  v_source       user_customer.source%type;
  i              number;
  c              number;
  n_isOld        number := 0;
  n_decRet       number := 0;
  n_ret          number := 0;
  n_awardGroupId number;
  n_agentTotalOld  number;
  n_agentTotalNew  number;
  n_count        number;
begin
  n_agentTotalOld:=p_agentTotalOld;
  n_agentTotalNew:=p_agentTotalNew;
  begin
    select id
      into n_awardGroupId
      from game_award_group
     where lotteryId = p_lotteryId and id=p_awardGroupId;
  exception
    when no_data_found then
      raise_application_error(-20001, '系统奖金组未初始化');
  end;
  if n_awardGroupId is not null then
    open cur_user;
    loop
      fetch cur_user
        into n_userId, v_userChain, v_source;
      exit when cur_user%notfound;
      --计算返点值
      c           := -1;
      n_decRet    := 0;
      n_isOld := case
                   when v_source = '3.0' then
                    1
                   else
                    0
                 end;
      v_userChain := substr(v_userChain, 2);
      i           := instr(v_userChain, '/', 1);
      while i > 1 loop
        v_userChain := substr(v_userChain, i + 1);
        i           := instr(v_userChain, '/', 1);
        c           := c + 1;
      end loop;
      if c > 0 then
        if c <= p_splitLvl then
          n_decRet := c * p_step1;
        else
          n_decRet := p_splitLvl * p_step1 + (c - p_splitLvl) * p_step2;
        end if;
      end if;
      if n_isOld = 1 then
        n_ret := n_agentTotalOld - n_decRet;
      else
        n_ret := n_agentTotalNew - n_decRet;
      end if;

      if n_ret < 0 then
        n_ret := 0;
      end if;
      dbms_output.put_line(n_agentTotalNew);

      --已经初始化奖金组
      select count(1)
        into n_count
        from game_award_user_group
       where userid = n_userId
         and lotteryId = p_lotteryId;
      if n_count > 0 then
        --一个奖金组 (无不定位返点）
        update game_award_user_group
           set super_ret = n_ret, update_time = sysdate
         where lotteryid = p_lotteryId
           and userid = n_userId
           and SYS_AWARD_GROUP_ID=n_awardGroupId;
      else
        insert into game_award_user_group
          (ID,
           LOTTERYID,
           DIRECT_RET,
           THREEONE_RET,
           super_ret,
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
           0,
           0,
           n_ret,
           sysdate,
           sysdate,
           n_userId,
           1,
           1,
           1,
           n_awardGroupId);
      end if;
    end loop;
    close cur_user;
  end if;
exception
  when others then
    dbms_output.put_line(sqlerrm);
end P_INIT_USER_RET_POINT_SUPER;
