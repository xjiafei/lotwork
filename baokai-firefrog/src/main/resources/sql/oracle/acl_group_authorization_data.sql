alter session set nls_date_language='american';
Set define OFF;

truncate table ACL_GROUP_AUTHORIZATION;
insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
1 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=1 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
2 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=2 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
3 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=3 and gid=(select id from acl_group where pid=-1));
 
 insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
4 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=4 and gid=(select id from acl_group where pid=-1));
 
insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
5 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=5 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
6 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=6 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
7 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=7 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
8 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=8 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
9 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=9 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
10 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=10 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
11 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=11 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
12 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=12 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
13 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=13 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
14 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=14 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
15 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=15 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
16 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=16 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
17 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=17 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
18 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=18 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
19 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=19 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
20 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=20 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
21 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=21 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
22 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=22 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
23 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=23 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
24 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=24 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
25 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=25 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
26 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=26 and gid=(select id from acl_group where pid=-1));



insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
27 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=27 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
28 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=28 and gid=(select id from acl_group where pid=-1));



insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
29 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=29 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
30 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=30 and gid=(select id from acl_group where pid=-1));



insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
31 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=31 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
32 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=32 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
33 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=33 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
34 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=34 and gid=(select id from acl_group where pid=-1));



insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
35 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=35 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
36 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=36 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
37 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=37 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
38 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=38 and gid=(select id from acl_group where pid=-1));



insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
39 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=39 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
40 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=40 and gid=(select id from acl_group where pid=-1));



insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
41 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=41 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
42 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=42 and gid=(select id from acl_group where pid=-1));



insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
43 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=43 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
44 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=44 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
45 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=45 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
46 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=46 and gid=(select id from acl_group where pid=-1));



insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
47 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=47 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
48 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=48 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
49 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=49 and gid=(select id from acl_group where pid=-1));









insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
100 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=100 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
101 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=101 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
102 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=102 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
103 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=103 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
104 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=104 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
105 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=105 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
106 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=106 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
107 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=107 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
108 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=108 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
109 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=109 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
110 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=110 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
111 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=111 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
112 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=112 and gid=(select id from acl_group where pid=-1));





insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
113 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=113 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
114 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=114 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
115 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=115 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
116 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=116 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
117 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=117 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
118 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=118 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
119 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=119 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
120 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=120 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
121 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=121 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
122 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=122 and gid=(select id from acl_group where pid=-1));





insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
123 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=123 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
124 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=124 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
125 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=125 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
126 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=126 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
127 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=127 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
128 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=128 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
129 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=129 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
130 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=130 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
131 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=131 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
132 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=132 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
133 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=133 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
134 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=134 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
135 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=135 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
136 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=136 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
137 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=137 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
138 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=138 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
139 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=139 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
140 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=140 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
141 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=141 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
142 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=142 and gid=(select id from acl_group where pid=-1));







insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
143 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=143 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
144 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=144 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
145 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=145 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
146 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=146 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
147 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=147 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
148 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=148 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
149 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=149 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
150 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=150 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
151 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=151 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
152 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=152 and gid=(select id from acl_group where pid=-1));




insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
153 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=153 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
154 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=154 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
155 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=155 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
156 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=156 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
157 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=157 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
158 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=158 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
159 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=159 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
160 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=160 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
161 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=161 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
162 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=162 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
163 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=163 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
164 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=164 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
165 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=165 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
201 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=201 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
202 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=202 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
203 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=203 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
204 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=204 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
205 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=205 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
206 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=206 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
207 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=207 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
208 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=208 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
209 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=209 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
210 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=210 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
211 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=211 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
212 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=212 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
213 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=213 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
214 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=214 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
215 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=215 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
216 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=216 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
217 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=217 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
218 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=218 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
219 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=219 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
220 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=220 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
221 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=221 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
222 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=222 and gid=(select id from acl_group where pid=-1));





insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
223 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=223 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
224 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=224 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
225 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=225 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
226 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=226 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
227 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=227 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
228 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=228 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
229 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=229 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
230 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=230 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
231 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=231 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
232 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=232 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
233 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=233 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
234 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=234 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
235 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=235 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
236 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=236 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
237 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=237 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
238 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=238 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
239 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=239 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
240 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=240 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
241 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=241 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
242 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=242 and gid=(select id from acl_group where pid=-1));







insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
243 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=243 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
244 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=244 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
245 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=245 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
246 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=246 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
247 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=247 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
248 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=248 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
249 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=249 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
250 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=250 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
251 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=251 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
252 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=252 and gid=(select id from acl_group where pid=-1));




insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
253 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=253 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
254 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=254 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
255 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=255 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
256 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=256 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
257 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=257 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
258 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=258 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
259 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=259 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
260 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=260 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
261 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=261 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
262 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=262 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
263 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=263 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
264 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=264 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
265 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=265 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
266 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=266 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
267 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=267 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
268 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=268 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
269 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=269 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
270 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=270 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
271 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=271 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
272 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=272 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
273 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=273 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
274 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=274 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
275 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=275 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
276 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=276 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
277 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=277 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
278 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=278 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
279 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=279 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
280 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=280 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
281 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=281 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
282 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=282 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
283 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=283 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
284 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=284 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
285 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=285 and gid=(select id from acl_group where pid=-1));





insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
286 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=286 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
287 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=287 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
288 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=288 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
289 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=289 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
290 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=290 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
291 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=291 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
292 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=292 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
293 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=293 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
294 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=294 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
295 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=295 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
296 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=296 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
297 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=297 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
298 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=298 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
299 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=299 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
300 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=300 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
301 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=301 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
302 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=302 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
303 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=303 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
304 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=304 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
305 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=305 and gid=(select id from acl_group where pid=-1));







insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
306 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=306 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
307 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=307 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
308 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=308 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
309 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=309 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
310 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=310 and gid=(select id from acl_group where pid=-1));


insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
311 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=311 and gid=(select id from acl_group where pid=-1));

insert into ACL_GROUP_AUTHORIZATION
select 
(select nvl(max(id),0)+1 from ACL_GROUP_AUTHORIZATION) id,
(select id from acl_group where pid=-1) gid,
312 acl_id
from dual where not EXISTS(select 1 from ACL_GROUP_AUTHORIZATION where acl_id=312 and gid=(select id from acl_group where pid=-1));


