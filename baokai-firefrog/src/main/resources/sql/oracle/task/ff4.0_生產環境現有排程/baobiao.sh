#!/bin/bash
source .bash_profile

sqlplus firefog/firefognxs<<eof
set timing on feedback on

@baobiao.sql
exit

eof


