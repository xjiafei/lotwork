--MP DB新增彩種
insert INTO lottery(lot_id,lot_name,3_chan_id,3_lot_id,4_lot_id,sort,enable) VALUES ( (select max(lot_id)+1 from lottery), '超级2000秒秒彩(APP版)',4,23,99113,26,0 );