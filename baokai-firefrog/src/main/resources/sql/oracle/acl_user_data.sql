truncate table acl_user;
insert into acl_user
select 
(select nvl(max(id),0)+1 from acl_user) id,
'admin' account,
(select id from acl_group where pid=-1) group_id,
'123456' BIND_PASSWD,
'' CELLPHONE,
'' TELEPHONE,
'' DEP,
0 STATUS,
'123456'  PASSWD,
'' EMAIL,
SYSTIMESTAMP GMT_CREATED,
SYSTIMESTAMP GMT_MODIFIED,
'' CREATEDER,
'' MODIFIEDER
from dual
WHERE not EXISTS(SELECT 1 from acl_user where "ACCOUNT"='admin');