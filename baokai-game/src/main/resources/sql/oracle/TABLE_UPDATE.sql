update GAME_AWARD set ACTUAL_BONUS_DOWN=0 where ACTUAL_BONUS_DOWN is null;
-- 更新1800奖金组 超级对子返点值100 --
update game_award_group set super_ret=100 where lotteryid=99101 and id=130 and super_ret=0;