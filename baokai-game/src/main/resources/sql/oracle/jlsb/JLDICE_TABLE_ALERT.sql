alter table USER_CUSTOMER add nick_name varchar2(50);
ALTER table USER_CUSTOMER add head_img  varchar2(500);
ALTER table USER_CUSTOMER add nick_update_time timestamp(3);
comment on column USER_CUSTOMER.nick_name  is '用户昵称';
comment on column USER_CUSTOMER.head_img  is '用户头像';
comment on column USER_CUSTOMER.nick_update_time  is '昵称更新时间';