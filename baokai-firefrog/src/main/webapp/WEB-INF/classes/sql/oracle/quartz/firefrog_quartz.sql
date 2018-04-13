declare 
      num   number; 
	  v_owner VARCHAR2(50);
begin 
     v_owner:='FIREFOG';
			select count(1) into num from all_tables where LOWER(TABLE_NAME) = 'qrtz_frog_job_listeners' and OWNER=v_owner; 
      if   num=1   then 
          execute immediate 'drop table qrtz_frog_job_listeners'; 
      end   if; 	
			select count(1) into num from all_tables where LOWER(TABLE_NAME) = 'qrtz_frog_simple_triggers' and OWNER=v_owner; 
      if   num=1   then 
          execute immediate 'drop table qrtz_frog_simple_triggers'; 
      end   if; 
			select count(1) into num from all_tables where LOWER(TABLE_NAME) = 'qrtz_frog_cron_triggers' and OWNER=v_owner; 
      if   num=1   then 
          execute immediate 'drop table qrtz_frog_cron_triggers'; 
      end   if; 
			select count(1) into num from all_tables where LOWER(TABLE_NAME) = 'qrtz_frog_blob_triggers' and OWNER=v_owner; 
      if   num=1   then 
          execute immediate 'drop table qrtz_frog_blob_triggers'; 
      end   if; 
			select count(1) into num from all_tables where LOWER(TABLE_NAME) = 'qrtz_frog_trigger_listeners' and OWNER=v_owner; 
      if   num=1   then 
          execute immediate 'drop table qrtz_frog_trigger_listeners'; 
      end   if; 
			select count(1) into num from all_tables where LOWER(TABLE_NAME) = 'qrtz_frog_calendars' and OWNER=v_owner; 
      if   num=1   then 
          execute immediate 'drop table qrtz_frog_calendars'; 
      end   if; 

			select count(1) into num from all_tables where LOWER(TABLE_NAME) = 'qrtz_frog_paused_trigger_grps' and OWNER=v_owner; 
      if   num=1   then 
          execute immediate 'drop table qrtz_frog_paused_trigger_grps'; 
      end   if; 

			select count(1) into num from all_tables where LOWER(TABLE_NAME) = 'qrtz_frog_fired_triggers' and OWNER=v_owner; 
      if   num=1   then 
          execute immediate 'drop table qrtz_frog_fired_triggers'; 
      end   if; 

			select count(1) into num from all_tables where LOWER(TABLE_NAME) = 'qrtz_frog_scheduler_state' and OWNER=v_owner; 
      if   num=1   then 
          execute immediate 'drop table qrtz_frog_scheduler_state'; 
      end   if; 

			select count(1) into num from all_tables where LOWER(TABLE_NAME) = 'qrtz_frog_locks' and OWNER=v_owner; 
      if   num=1   then 
          execute immediate 'drop table qrtz_frog_locks'; 
      end   if; 

			select count(1) into num from all_tables where LOWER(TABLE_NAME) = 'qrtz_frog_triggers' and OWNER=v_owner; 
      if   num=1   then 
          execute immediate 'drop table qrtz_frog_triggers'; 
      end   if; 

			 select count(1) into num from all_tables where LOWER(TABLE_NAME) = 'qrtz_frog_job_details' and OWNER=v_owner; 
      if   num=1   then 
          execute immediate 'drop table qrtz_frog_job_details'; 
      end   if; 

			
			select count(1) into num from all_tables where LOWER(TABLE_NAME) = 'qrtz_frog_job_details' and OWNER=v_owner; 
      if   num=1   then 
          execute immediate 'drop table qrtz_frog_job_details'; 
      end   if; 
end; 
/
CREATE TABLE qrtz_frog_job_details
  (
    JOB_NAME  VARCHAR2(200) NOT NULL,
    JOB_GROUP VARCHAR2(200) NOT NULL,
    DESCRIPTION VARCHAR2(250) NULL,
    JOB_CLASS_NAME   VARCHAR2(250) NOT NULL, 
    IS_DURABLE VARCHAR2(1) NOT NULL,
    IS_VOLATILE VARCHAR2(1) NOT NULL,
    IS_STATEFUL VARCHAR2(1) NOT NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,
    JOB_DATA BLOB NULL,
    PRIMARY KEY (JOB_NAME,JOB_GROUP)
)
SEGMENT CREATION IMMEDIATE;
CREATE TABLE qrtz_frog_job_listeners
  (
    JOB_NAME  VARCHAR2(200) NOT NULL, 
    JOB_GROUP VARCHAR2(200) NOT NULL,
    JOB_LISTENER VARCHAR2(200) NOT NULL,
    PRIMARY KEY (JOB_NAME,JOB_GROUP,JOB_LISTENER),
    FOREIGN KEY (JOB_NAME,JOB_GROUP) 
	REFERENCES qrtz_frog_JOB_DETAILS(JOB_NAME,JOB_GROUP)
)
SEGMENT CREATION IMMEDIATE;
CREATE TABLE qrtz_frog_triggers
  (
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    JOB_NAME  VARCHAR2(200) NOT NULL, 
    JOB_GROUP VARCHAR2(200) NOT NULL,
    IS_VOLATILE VARCHAR2(1) NOT NULL,
    DESCRIPTION VARCHAR2(250) NULL,
    NEXT_FIRE_TIME NUMBER(13) NULL,
    PREV_FIRE_TIME NUMBER(13) NULL,
    PRIORITY NUMBER(13) NULL,
    TRIGGER_STATE VARCHAR2(16) NOT NULL,
    TRIGGER_TYPE VARCHAR2(8) NOT NULL,
    START_TIME NUMBER(13) NOT NULL,
    END_TIME NUMBER(13) NULL,
    CALENDAR_NAME VARCHAR2(200) NULL,
    MISFIRE_INSTR NUMBER(2) NULL,
    JOB_DATA BLOB NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (JOB_NAME,JOB_GROUP) 
	REFERENCES qrtz_frog_JOB_DETAILS(JOB_NAME,JOB_GROUP) 
)
SEGMENT CREATION IMMEDIATE;
CREATE TABLE qrtz_frog_simple_triggers
  (
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    REPEAT_COUNT NUMBER(7) NOT NULL,
    REPEAT_INTERVAL NUMBER(12) NOT NULL,
    TIMES_TRIGGERED NUMBER(10) NOT NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP) 
	REFERENCES qrtz_frog_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
)SEGMENT CREATION IMMEDIATE;
CREATE TABLE qrtz_frog_cron_triggers
  (
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    CRON_EXPRESSION VARCHAR2(120) NOT NULL,
    TIME_ZONE_ID VARCHAR2(80),
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP) 
	REFERENCES qrtz_frog_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
)SEGMENT CREATION IMMEDIATE;
CREATE TABLE qrtz_frog_blob_triggers
  (
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    BLOB_DATA BLOB NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP) 
        REFERENCES qrtz_frog_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
)SEGMENT CREATION IMMEDIATE;
CREATE TABLE qrtz_frog_trigger_listeners
  (
    TRIGGER_NAME  VARCHAR2(200) NOT NULL, 
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    TRIGGER_LISTENER VARCHAR2(200) NOT NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_LISTENER),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP) 
	REFERENCES qrtz_frog_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
)SEGMENT CREATION IMMEDIATE;
CREATE TABLE qrtz_frog_calendars
  (
    CALENDAR_NAME  VARCHAR2(200) NOT NULL, 
    CALENDAR BLOB NOT NULL,
    PRIMARY KEY (CALENDAR_NAME)
)SEGMENT CREATION IMMEDIATE;
CREATE TABLE qrtz_frog_paused_trigger_grps
  (
    TRIGGER_GROUP  VARCHAR2(200) NOT NULL, 
    PRIMARY KEY (TRIGGER_GROUP)
)SEGMENT CREATION IMMEDIATE;
CREATE TABLE qrtz_frog_fired_triggers 
  (
    ENTRY_ID VARCHAR2(95) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    IS_VOLATILE VARCHAR2(1) NOT NULL,
    INSTANCE_NAME VARCHAR2(200) NOT NULL,
    FIRED_TIME NUMBER(13) NOT NULL,
    PRIORITY NUMBER(13) NOT NULL,
    STATE VARCHAR2(16) NOT NULL,
    JOB_NAME VARCHAR2(200) NULL,
    JOB_GROUP VARCHAR2(200) NULL,
    IS_STATEFUL VARCHAR2(1) NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NULL,
    PRIMARY KEY (ENTRY_ID)
)SEGMENT CREATION IMMEDIATE;
CREATE TABLE qrtz_frog_scheduler_state 
  (
    INSTANCE_NAME VARCHAR2(200) NOT NULL,
    LAST_CHECKIN_TIME NUMBER(13) NOT NULL,
    CHECKIN_INTERVAL NUMBER(13) NOT NULL,
    PRIMARY KEY (INSTANCE_NAME)
)SEGMENT CREATION IMMEDIATE;
CREATE TABLE qrtz_frog_locks
  (
    LOCK_NAME  VARCHAR2(40) NOT NULL, 
    PRIMARY KEY (LOCK_NAME)
)SEGMENT CREATION IMMEDIATE;
INSERT INTO qrtz_frog_locks values('TRIGGER_ACCESS');
INSERT INTO qrtz_frog_locks values('JOB_ACCESS');
INSERT INTO qrtz_frog_locks values('CALENDAR_ACCESS');
INSERT INTO qrtz_frog_locks values('STATE_ACCESS');
INSERT INTO qrtz_frog_locks values('MISFIRE_ACCESS');
create index idx_qrtz_frog_j_req_recovery on qrtz_frog_job_details(REQUESTS_RECOVERY);
create index idx_qrtz_frog_t_next_fire_time on qrtz_frog_triggers(NEXT_FIRE_TIME);
create index idx_qrtz_frog_t_state on qrtz_frog_triggers(TRIGGER_STATE);
create index idx_qrtz_frog_t_nft_st on qrtz_frog_triggers(NEXT_FIRE_TIME,TRIGGER_STATE);
create index idx_qrtz_frog_t_volatile on qrtz_frog_triggers(IS_VOLATILE);
create index idx_qrtz_frog_ft_trig_name on qrtz_frog_fired_triggers(TRIGGER_NAME);
create index idx_qrtz_frog_ft_trig_group on qrtz_frog_fired_triggers(TRIGGER_GROUP);
create index idx_qrtz_frog_ft_trig_nm_gp on qrtz_frog_fired_triggers(TRIGGER_NAME,TRIGGER_GROUP);
create index idx_qrtz_frog_ft_trig_vlt on qrtz_frog_fired_triggers(IS_VOLATILE);
create index idx_qrtz_frog_ft_inst_name on qrtz_frog_fired_triggers(INSTANCE_NAME);
create index idx_qrtz_frog_ft_job_name on qrtz_frog_fired_triggers(JOB_NAME);
create index idx_qrtz_frog_ft_job_group on qrtz_frog_fired_triggers(JOB_GROUP);
create index idx_qrtz_frog_ft_job_stateful on qrtz_frog_fired_triggers(IS_STATEFUL);
create index idx_qrtz_frog_ft_job_req_rey on qrtz_frog_fired_triggers(REQUESTS_RECOVERY);
commit;
/