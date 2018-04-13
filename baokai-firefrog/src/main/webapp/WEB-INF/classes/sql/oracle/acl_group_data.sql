alter session set nls_date_language='american';
Set define OFF;
truncate table ACL_GROUP;
INSERT INTO ACL_GROUP
select
(select nvl(max(id),0)+1 from acl_group) id, 
'' CREATORER, 
'' MODIFIERER, 
SYSTIMESTAMP GMT_CREATED, 
SYSTIMESTAMP GMT_MODIFIED, 
1 IN_USE, 
'顶级组' NAME, 
'顶级组' FULL_NAME,
 -1 PID, 
 1 LVL
 from dual where not EXISTS(select 1 from acl_group where name='顶级组');