#!/bin/bash
#
# Modification Log
# Date       Modifier        Notes
# 20150529   Jerry           4.0账变列表（账户明细表）下载
### parameters #####
source ~/.bash_profile
export ORACLE_SID=gamenxs1
file_name=va_`date -d'-1 day' +%Y%m%d`.txt
file_name1=va_`date -d'-1 day' +%Y%m%d`_1.txt
file_name2=va_`date -d'-1 day' +%Y%m%d`_2.txt

##################
cd /home/oracle/script/va

sqlplus -s "/as sysdba" @/home/oracle/script/va/va.sql

cnt=`cat $file_name|wc -l`

if [ $cnt -gt 1400001 ]
then
   sed -n '1,1400001p' $file_name > $file_name1
   sed -n '1,1p' $file_name > $file_name2
   sed -n '1400002,2800001 p' $file_name >> $file_name2
   gzip $file_name1
   gzip $file_name2
   rm $file_name

   scp $file_name1.gz jerry@10.6.100.21:/home/DBreport/
   scp $file_name2.gz jerry@10.6.100.21:/home/DBreport/
else
   gzip $file_name
   scp $file_name.gz jerry@10.6.100.21:/home/DBreport/

fi

mv *.gz bak/.

find /home/oracle/script/va/bak/ -mtime +7 -name '*.gz' -print|xargs -l rm -f
