insert into CONFIG  values((select max(id)+1 from CONFIG )
,'fund','chargeCouteUnionpay','15',null,sysdate,'0',null,null,null,null,null);
