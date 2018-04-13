#!/bin/bash
source .bash_profile

sqlplus / as sysdba <<eof
set time on
@de
exit
eof


sqlplus firefog/firefognxs<<eof
set time on timing on feedback on

@2insert_data_0327.sql 
exit

eof

sqlplus firefog/firefognxs<<eof
set time on timing on feedback on

@game_event.sql
exit

eof


sqlplus / as sysdba <<eof
@en
exit
eof
