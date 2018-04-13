
truncate table fund_charge;
alter sequence SEQ_FUND_CHARGE_ID increment by 1 cache 20;

truncate table fund_change_log;
alter sequence SEQ_fund_change_log_ID increment by 1 cache 20;

truncate table fund_withdraw;
alter sequence SEQ_fund_withdraw_ID increment by 1 cache 20;

truncate table FUND_CHARGE_EXCEPTION;

truncate table FUND_MANUAL_DEPOSIT;
alter sequence SEQ_FUND_MANUAL_DEPOSIT_ID increment by 1 cache 20;

truncate table FUND_TRANSFER;
alter sequence SEQ_FUND_TRANSFER_ID increment by 1 cache 20;