alter table GAME_TREND_TASK add exec_time timestamp(3);
comment on column GAME_TREND_TASK.exec_time  is '执行时间 ';

alter table game_trend_task modify draw_number varchar2(30);