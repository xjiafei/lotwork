create table GAME_MULTIPLE_ASSIST(ID number not null,PARENT_ID number not null,ASSIT_CODE number not null,MULTIPLE number(10) not null,
REMARK varchar2(500) not null,CREATE_USER_ID number not null,CREATE_TIME timestamp(3) not null,UPDATE_USEER_ID number,UPDATE_TIME timestamp(3));
comment on table GAME_MULTIPLE_ASSIST
  is '投注限制倍数辅助玩法';
-- Add comments to the columns 
comment on column GAME_MULTIPLE_ASSIST.PARENT_ID
  is '投注限制倍数主玩法ID'; 
comment on column GAME_MULTIPLE_ASSIST.ASSIT_CODE
  is '辅助玩法编码';
comment on column GAME_MULTIPLE_ASSIST.MULTIPLE
  is '倍数';
comment on column GAME_MULTIPLE_ASSIST.REMARK
  is '备注';
comment on column GAME_MULTIPLE_ASSIST.CREATE_USER_ID
  is '创建人ID';
comment on column GAME_MULTIPLE_ASSIST.CREATE_TIME
  is '创建时间';
comment on column GAME_MULTIPLE_ASSIST.UPDATE_USEER_ID
  is '修改人ID';
comment on column GAME_MULTIPLE_ASSIST.UPDATE_TIME
  is '修改时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table GAME_MULTIPLE_ASSIST
  add constraint PK_GAME_MULTIPLE_ASSIST primary key (ID);
--Create indexes
create index IDX_GAME_MULTIPLE_ASSIST_PID on GAME_MULTIPLE_ASSIST(PARENT_ID);


create sequence SEQ_GAME_MULTIPLE_ASSIST_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
nocache;

----------------------- 2015-10-15 走势图优化 --------------
create table GAME_TREND_TASK(ID number not null,LOTTERY_ID number not null,ISSUE_CODE number not null,DRAW_NUMBER varchar2(10) not null,
USER_ID number,REMARK varchar2(500),STATUS NUMBER default 1,CREATE_TIME timestamp(3) not null,UPDATE_TIME timestamp(3));
comment on table GAME_TREND_TASK
  is '走势图任务';
-- Add comments to the columns 
comment on column GAME_TREND_TASK.LOTTERY_ID
  is '彩种ID'; 
comment on column GAME_TREND_TASK.ISSUE_CODE
  is '奖期号';
comment on column GAME_TREND_TASK.DRAW_NUMBER
  is '开奖号码';
comment on column GAME_TREND_TASK.USER_ID
  is '秒秒彩用户ID';
comment on column GAME_TREND_TASK.REMARK
  is '备注';
comment on column GAME_TREND_TASK.STATUS
  is '状态（1:未执行2:执行成功3:执行失败4：已撤销)';
comment on column GAME_TREND_TASK.CREATE_TIME
  is '创建时间';
comment on column GAME_TREND_TASK.UPDATE_TIME
  is '修改时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table GAME_TREND_TASK
  add constraint PK_GAME_TREND_TASK primary key (ID);
--Create indexes
create index IDX_GAME_TREND_TASK_LOT_ISSUE on GAME_TREND_TASK(LOTTERY_ID,ISSUE_CODE);

create sequence SEQ_GAME_TREND_TASK_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
nocache;
