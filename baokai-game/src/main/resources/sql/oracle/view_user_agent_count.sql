create or replace view view_user_agent_count as
select k.id,k.user_id,k.day,k.time,k.charge,k.bet,k.with_draw,k.reward,k.new_User
  from (select 1 as id,
               a.user_id as user_id,
               nvl(a.day, b.day) as day,
               nvl(a.hh, b.hh) as time,
               nvl(b.charge, 0) as charge,
               nvl(b.bet, 0) as bet,
               nvl(b.withdraw, 0) as with_draw,
               nvl(b.ret, 0) as reward,
               nvl(a.user_count, 0) as new_User
          from (select tt.id as user_id,
                       to_char(t2.register_date, 'HH24') as hh,
                       trunc(t2.register_date) as day,
                       sum(case
                             when t2.id is null then
                              0
                             else
                              1
                           end) as user_count
                  from user_customer tt
                  left join (select *
                              from user_customer
                             where register_date > sysdate - 7) t2
                    on t2.user_chain like tt.user_chain || '_%'
                 group by tt.id,
                          to_char(t2.register_date, 'HH24'),
                          trunc(t2.register_date)) a
          left join (select tt.id as user_id,
                           to_char(GMT_CREATED, 'HH24') as hh,
                           trunc(GMT_CREATED) as day,
                           sum(decode(t.REASON,
                                      'FD,ADAL,null,3',
                                      ct_bal - befor_bal,
                                      'FD,ADML,null,8',
                                      ct_bal - befor_bal,
                                      'FD,MDAX,null,5',
                                      ct_bal - befor_bal,
                                      'OT,AAXX,null,3',
                                      ct_bal - befor_bal,
                                      0)) as charge,
                           sum((case
                                 when t.REASON in
                                      ('GM,DVCN,null,2', 'GM,DVCB,null,2') then
                                  1
                                 when t.ex_code is not null and
                                      t.REASON in ('GM,CRVC,null,1',
                                                   'GM,CRVC,null,3',
                                                   'GM,CRVC,null,6') then
                                  -1
                                 else
                                  0
                               end) * (before_damt - ct_damt)) as bet,
                           sum(decode(t.REASON,
                                      'FD,CWCS,null,6',
                                      before_damt - ct_damt,
                                      'FD,CWCS,null,5',
                                      before_damt - ct_damt,
                                      'FD,CWTS,null,5',
                                      before_damt - ct_damt,
                                      'FD,CWTS,null,6',
                                      before_damt - ct_damt,
                                      0)) as withdraw,
                           sum(decode(t.REASON,
                                      'GM,RSXX,null,1',
                                      ct_bal - befor_bal,
                                      'GM,RHAX,null,2',
                                      ct_bal - befor_bal,
                                      'GM,RRHA,null,2',
                                      -1 * (ct_bal - befor_bal),
                                      'GM,RRSX,null,1',
                                      -1 * (ct_bal - befor_bal),
                                      0)) as ret
                      from user_customer tt
                     inner join view_fund_change_log t
                        on t.user_chain like tt.user_chain || '_%'
                       and t.GMT_CREATED > sysdate - 7
                     group by tt.id,
                              to_char(GMT_CREATED, 'HH24'),
                              trunc(GMT_CREATED)) b
            on a.user_id = b.user_id
           and a.hh = b.hh
           and a.day = b.day
        union
        select 1 as id,
               b.user_id as user_id,
               b.day as day,
               b.hh as time,
               nvl(b.charge, 0) as charge,
               nvl(b.bet, 0) as bet,
               nvl(b.withdraw, 0) as with_draw,
               nvl(b.ret, 0) as reward,
               nvl(a.user_count, 0) as new_User
          from (select tt.id as user_id,
                       to_char(GMT_CREATED, 'HH24') as hh,
                       trunc(GMT_CREATED) as day,
                       sum(decode(t.REASON,
                                  'FD,ADAL,null,3',
                                  ct_bal - befor_bal,
                                  'FD,ADML,null,8',
                                  ct_bal - befor_bal,
                                  'FD,MDAX,null,5',
                                  ct_bal - befor_bal,
                                  'OT,AAXX,null,3',
                                  ct_bal - befor_bal,
                                  0)) as charge,
                       sum((case
                             when t.REASON in ('GM,DVCN,null,2', 'GM,DVCB,null,2') then
                              1
                             when t.ex_code is not null and
                                  t.REASON in ('GM,CRVC,null,1',
                                               'GM,CRVC,null,3',
                                               'GM,CRVC,null,6') then
                              -1
                             else
                              0
                           end) * (before_damt - ct_damt)) as bet,
                       sum(decode(t.REASON,
                                  'FD,CWCS,null,6',
                                  before_damt - ct_damt,
                                  'FD,CWCS,null,5',
                                  before_damt - ct_damt,
                                  'FD,CWTS,null,5',
                                  before_damt - ct_damt,
                                  'FD,CWTS,null,6',
                                  before_damt - ct_damt,
                                  0)) as withdraw,
                       sum(decode(t.REASON,
                                  'GM,RSXX,null,1',
                                  ct_bal - befor_bal,
                                  'GM,RHAX,null,2',
                                  ct_bal - befor_bal,
                                  'GM,RRHA,null,2',
                                  -1 * (ct_bal - befor_bal),
                                  'GM,RRSX,null,1',
                                  -1 * (ct_bal - befor_bal),
                                  0)) as ret
                  from user_customer tt
                 inner join view_fund_change_log t
                    on t.user_chain like tt.user_chain || '_%'
                   and t.GMT_CREATED > sysdate - 7
                 group by tt.id,
                          to_char(GMT_CREATED, 'HH24'),
                          trunc(GMT_CREATED)) b
          left join (select tt.id as user_id,
                            to_char(t2.register_date, 'HH24') as hh,
                            trunc(t2.register_date) as day,
                            sum(case
                                  when t2.id is null then
                                   0
                                  else
                                   1
                                end) as user_count
                       from user_customer tt
                       left join (select *
                                   from user_customer
                                  where register_date > sysdate - 7) t2
                         on t2.user_chain like tt.user_chain || '_%'
                      group by tt.id,
                               to_char(t2.register_date, 'HH24'),
                               trunc(t2.register_date)) a
            on a.user_id = b.user_id
           and a.day = b.day
           and a.hh = b.hh) k
