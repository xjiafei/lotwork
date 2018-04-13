create or replace PROCEDURE         P_INIT_USER_RET_POINT (
   p_lotteryId    VARCHAR2)
IS
   /*
   快三已开户用户（非总代）返点初始化
   需求：
   老用户和老注册链接------总返点8%，总代 1代 2代保持2%的返点点差，2代以下按照逐级-0.5%的规则，需要在注册链接内赋予对应的返点值。
   新注册链接 --- 开户的新用户，按照4.0现有的规则进行设置。
   仅允许执行一次（无法识别首次初始化之后的数据有没被修改）
   e.g.
   begin
     P_INIT_USER_RET_POINT(88101);
     --commit;
    end;
   */
   CURSOR cur_user
   IS
      SELECT id, user_chain, source
        FROM user_customer
       WHERE user_lvl <> 0;

   n_userId         NUMBER;
   v_userChain      user_customer.user_chain%TYPE;
   v_source         user_customer.source%TYPE;
   i                NUMBER;
   c                NUMBER;
   n_step1          NUMBER := CASE p_lotteryId WHEN 99701 THEN 100 ELSE 100 END;
   n_step2          NUMBER := 100;  
   n_step1_color          NUMBER := CASE p_lotteryId WHEN 99701 THEN 20 ELSE 20 END;
   n_step2_color          NUMBER := 20;   
   n_step1_year          NUMBER := CASE p_lotteryId WHEN 99701 THEN 50 ELSE 50 END;
   n_step2_year          NUMBER := 50;   
   n_isOld          NUMBER := 0;   
   n_slipLvl        NUMBER := 5;
   n_decRet         NUMBER := 0;
   n_year           NUMBER := 0;
   n_color          NUMBER := 0; 
   n_year1           NUMBER := 0;
   n_color1          NUMBER := 0;   
   n_year2           NUMBER := 0;
   n_color2          NUMBER := 0;             
   n_ret            NUMBER := 0;   
   n_agentTotal1    NUMBER := CASE p_lotteryId WHEN 99701 THEN 100 ELSE 100 END;
   n_agentTotal2    NUMBER := n_agentTotal1;
   n_awardGroupId   NUMBER;
   n_count          NUMBER;
BEGIN
   BEGIN
      SELECT id, direct_ret, direct_ret , LHC_YEAR,LHC_COLOR
        INTO n_awardGroupId, n_agentTotal1, n_agentTotal2,n_year , n_color
        FROM game_award_group
       WHERE lotteryId = p_lotteryId;
   EXCEPTION
      WHEN NO_DATA_FOUND
      THEN
         raise_application_error (-20001, '系统奖金组未初始化');
   END;

   IF n_awardGroupId IS NOT NULL
   THEN
      OPEN cur_user;

      LOOP
         FETCH cur_user INTO n_userId, v_userChain, v_source;
         EXIT WHEN cur_user%NOTFOUND;
         --计算返点值
         c := -1;
         n_decRet := 0;
         n_isOld := CASE WHEN v_source = '3.0' THEN 1 ELSE 0 END;
         
         v_userChain := SUBSTR (v_userChain, 2);
         i := INSTR (v_userChain, '/', 1);

         WHILE i > 1
         LOOP
            v_userChain := SUBSTR (v_userChain, i + 1);
            i := INSTR (v_userChain, '/', 1);
            c := c + 1;
         END LOOP;

         IF c > 0
         THEN
            IF c <= n_slipLvl
            THEN
               n_decRet := c * n_step1;
               n_year1  := c * n_step1_year;
               n_color1 := c * n_step1_color;
            ELSE
               n_decRet := n_slipLvl * n_step1 + (c - n_slipLvl) * n_step2;
               n_year1  := n_slipLvl *n_step1_year + (c - n_slipLvl) * n_step2_year;
               n_color1 := n_slipLvl *n_step1_color + (c - n_slipLvl) * n_step2_color;              
            END IF;
         END IF;

         IF n_isOld = 1
         THEN
            n_ret := n_agentTotal1 - n_decRet;
            n_year2  := n_year-n_year1;
            n_color2 := n_color-n_color1;
         ELSE
            n_ret := n_agentTotal2 - n_decRet;
            n_year2  := n_year-n_year1;
            n_color2 := n_color-n_color1;
         END IF;

         IF n_ret < 0
         THEN
            n_ret := 0;
         END IF;
         
          IF n_year2 < 0
         THEN
            n_year2 := 0;
         END IF;
         
          IF n_color2 < 0
         THEN
            n_color2 := 0;
         END IF;

         DBMS_OUTPUT.put_line ('********');

         --已经初始化奖金组
         SELECT COUNT (1)
           INTO n_count
           FROM game_award_user_group
          WHERE userid = n_userId AND lotteryId = p_lotteryId;

         IF n_count > 0
         THEN
            --一个奖金组 (无不定位返点）
            DBMS_OUTPUT.put_line ('count > 0');

            UPDATE game_award_user_group
               SET direct_ret = n_ret,
                   threeone_ret = 0,
                   update_time = SYSDATE,
                   LHC_YEAR = n_year2 ,
                   LHC_COLOR =n_color2
                   WHERE lotteryid = p_lotteryId AND userid = n_userId;
         ELSE
            DBMS_OUTPUT.put_line ('count < 0');
            DBMS_OUTPUT.put_line (n_userId);
            DBMS_OUTPUT.put_line (p_lotteryId);

            INSERT INTO game_award_user_group (ID,
                                               LOTTERYID,
                                               DIRECT_RET,
                                               THREEONE_RET,
                                               CREATE_TIME,
                                               UPDATE_TIME,
                                               USERID,
                                               SET_TYPE,
                                               STATUS,
                                               BET_TYPE,
                                               SYS_AWARD_GROUP_ID,
                                               LHC_YEAR,
                                               LHC_COLOR)
                 VALUES (SEQ_GAME_AWARD_USER_GROUP_ID.NEXTVAL,
                         p_lotteryId,
                         n_ret,
                         0,
                         SYSDATE,
                         SYSDATE,
                         n_userId,
                         1,
                         1,
                         1,
                         n_awardGroupId,
                         n_year2,
                         n_color2);
         END IF;
      END LOOP;

      CLOSE cur_user;
   END IF;
EXCEPTION
   WHEN OTHERS
   THEN
      DBMS_OUTPUT.put_line (SQLERRM);
END P_INIT_USER_RET_POINT;