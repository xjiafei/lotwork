Insert into FIREFOG.ACL (ID,PID,NAME,TYPE,ORDERS,LABEL) values (SEQ_ACL_ID.NEXTVAL,(select t.PID from acl t where t.LABEL='奖金模式'),'USER_MANAGE_LIST_SUPER2000',3,1001,'超级2000');

Insert into FIREFOG.ACL (ID,PID,NAME,TYPE,ORDERS,LABEL) values (SEQ_ACL_ID.NEXTVAL,(select t.ID from acl t where t.LABEL='超级2000'),'USER_MANAGE_LIST_SUPER2000_ONOFF',1,1002,'关闭/开启超级2000');

	

insert into ACL_GROUP_AUTHORIZATION
select
SEQ_ACL_GROUP_AUTH_ID.NEXTVAL,
(select id from acl_group where pid=-1) gid,
(select id from acl WHERE LABEL='超级2000') acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=(select id from acl WHERE LABEL='超级2000') and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select
SEQ_ACL_GROUP_AUTH_ID.NEXTVAL,
(select id from acl_group where pid=-1) gid,
(select id from acl WHERE LABEL='关闭/开启超级2000') acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=(select id from acl WHERE LABEL='关闭/开启超级2000') and gid=(select id from acl_group where pid=-1)); 