alter table GAME_TREND_TASK add exec_time timestamp(3);
comment on column GAME_TREND_TASK.exec_time  is '执行时间 ';

-- 奖金返点切换 -----------------------------
alter table user_customer add super_pair_status number default 1;
comment on column user_customer.super_pair_status  is '超级对子状态(0:关闭 1:开启)';

alter table GAME_AWARD modify ACTUAL_BONUS_DOWN number default 0;
comment on column GAME_AWARD.ACTUAL_BONUS_DOWN  is '保底奖金';
alter table game_award_group add super_ret number default 0;
comment on column game_award_group.super_ret  is '超级对子返点'; 
alter table Game_Award_Group_Check add super_ret number default 0;
comment on column Game_Award_Group_Check.super_ret  is '超级对子返点'; 
alter table game_award_group add super_pair_status number default 1;
comment on column game_award_group.super_pair_status  is '超级对子状态(0:关闭 1:开启)'; 
alter table game_award_user_group add super_ret number default 0;
comment on column game_award_user_group.super_ret  is '超级对子返点';
alter table game_slip add single_win_down number default 0;
comment on column game_slip.single_win_down  is '单注最小奖金';
alter table game_slip_assist add evaluate_award_down number default 0;
comment on column game_slip_assist.evaluate_award_down  is '预计最小奖金';
alter table GAME_PACKAGE_ASSIST add evaluate_award_down number default 0;
comment on column GAME_PACKAGE_ASSIST.evaluate_award_down  is '预计最小奖金'; 