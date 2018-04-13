insert into acl (ID, PID, NAME, TYPE, ORDERS, LABEL)
values (457, 84, 'FUND_BANKCARD_BANKCARDMANAGE_MOVE', 1, 457, '移动端开关');

insert into acl (ID, PID, NAME, TYPE, ORDERS, LABEL)
values (458, 457, 'FUND_BANKCARD_BANKCARDMANAGE_MOVE_SUBMIT', 3, 458, '提交数据');


insert into acl_group_authorization (ID, GID, ACL_ID)
values (151525, 66, 457);

insert into acl_group_authorization (ID, GID, ACL_ID)
values (151526, 66, 458);
