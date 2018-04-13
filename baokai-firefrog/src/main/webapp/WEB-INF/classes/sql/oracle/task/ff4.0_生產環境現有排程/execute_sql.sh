#!/bin/bash
source /home/oracle/.bash_profile

SQLTEXT=$1; export SQLTEXT

NOW=`date +%Y%m%d%H%M%S`
Log=/home/oracle/script/Log/$SQLTEXT.log.$NOW

date > $Log

cd /home/oracle/script

sqlplus firefog/firefognxs << EOF >> $Log
set echo on
set time on
set timing on

@$SQLTEXT

exit
EOF

find /home/oracle/script/Log/ -mtime +15 -name '*.log.*' -print|xargs -l rm -f

