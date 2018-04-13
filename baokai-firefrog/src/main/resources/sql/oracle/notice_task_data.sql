alter session set nls_date_language='american';
Set define OFF;

--------------------------------------------------------
--  File created - Wednesday-February-26-2014   
--------------------------------------------------------
DROP TABLE "FIREFOG"."NOTICE_TASK" cascade constraints;
--------------------------------------------------------
--  DDL for Table NOTICE_TASK
--------------------------------------------------------

  CREATE TABLE "FIREFOG"."NOTICE_TASK" 
   (	"ID" NUMBER, 
	"MODULE" VARCHAR2(20 BYTE), 
	"TASK" VARCHAR2(100 BYTE), 
	"ACTIVATED" NUMBER DEFAULT 0, 
	"INNER_MSG_ACTIVATED" NUMBER DEFAULT 0, 
	"INNER_MSG_USED" NUMBER DEFAULT 0, 
	"EMAIL_ACTIVATED" NUMBER DEFAULT 0, 
	"EMAIL_USED" NUMBER DEFAULT 0, 
	"NOTE_ACTIVATED" NUMBER DEFAULT 0, 
	"NOTE_USED" NUMBER DEFAULT 0, 
	"EMS_ACTIVATED" NUMBER DEFAULT 0, 
	"EMS_USED" NUMBER DEFAULT 0, 
	"INNER_MSG_TEMP" VARCHAR2(2000 BYTE) DEFAULT 0, 
	"EMAIL_TEMP" VARCHAR2(2000 BYTE) DEFAULT 0, 
	"NOTE_TEMP" VARCHAR2(200 BYTE) DEFAULT 0, 
	"EMS_TEMP" VARCHAR2(200 BYTE) DEFAULT 0, 
	"INNER_MSG_TITLE" VARCHAR2(50 BYTE) DEFAULT 0, 
	"EMAIL_TITLE" VARCHAR2(50 BYTE) DEFAULT 0, 
	"SET_BY_USER" NUMBER, 
	"TEMPLATE_PARAMS" VARCHAR2(500 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "FIRE" ;
 

   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."MODULE" IS '模块 信息变动，资金变动';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."TASK" IS '任务';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."ACTIVATED" IS '1)启用 0)禁用 通知任务';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."INNER_MSG_ACTIVATED" IS '1)启用 0)禁用 站内信';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."INNER_MSG_USED" IS '1)可用 0)不可用 站内信';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."EMAIL_ACTIVATED" IS '1)启用 0)禁用 邮箱';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."EMAIL_USED" IS '1)可用 0)不可用 邮箱';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."NOTE_ACTIVATED" IS '1)启用 0)禁用 桌面通知';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."NOTE_USED" IS '1)可用 0)不可用 桌面通知';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."EMS_ACTIVATED" IS '1)启用 0)禁用 短信';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."EMS_USED" IS '1)可用 0)不可用 短信';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."INNER_MSG_TEMP" IS '站内信模板';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."EMAIL_TEMP" IS 'email模板';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."NOTE_TEMP" IS '桌面通知模板';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."EMS_TEMP" IS '短信模板';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."INNER_MSG_TITLE" IS '站内信标题';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."EMAIL_TITLE" IS 'email标题';
 
   COMMENT ON COLUMN "FIREFOG"."NOTICE_TASK"."SET_BY_USER" IS '1)用户可设置  2）用户不可设置';
  GRANT DELETE, INSERT, SELECT, UPDATE ON "FIREFOG"."NOTICE_TASK" TO "FIREFROG";
REM INSERTING into FIREFOG.NOTICE_TASK
SET DEFINE OFF;
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (15,'信息变动','预留信息设置',1,1,1,1,1,0,0,0,0,'亲爱的{$userName}，恭喜你预留信息设置成功。','<table width="599" cellspacing="0" cellpadding="0" border="0"align="center"><tr><td colspan="3" width="599" height="80" style="background: #076256;"align="center"><imgsrc="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png"width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background: #076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520"style="text-align: left; color: #00564d; font-size: 0;"><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size: 14px; line-height: 24px;">恭喜你预留信息设置成功。</td></tr><tr><td height="64" style="font-size: 12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br /> 这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background: #076256; font-size: 0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center"style="background: #076256; color: #FFF; font-size: 12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'预留信息设置成功','预留信息设置成功',1,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (23,'中奖提醒','中奖通知',1,1,1,0,0,1,1,0,0,'亲爱的{$userName}，恭喜您在{$lotteryName}{$rewardTerm} 中奖{$lotteryMoney}，查看详情{$detailUrl}','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">亲爱的{$userName}，恭喜您在{$lotteryName}{$rewardTerm} 中奖{$lotteryMoney}，查看详情{$detailUrl}</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>','亲爱的{userName}，恭喜您在{lotteryName}{rewardTerm} 中奖{lotteryMoney}，查看详情{detailUrl}',null,'中奖通知','中奖通知',1,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (17,'信息变动','登录密码修改',1,1,1,1,1,0,0,0,0,'亲爱的{$userName}，恭喜你登录密码修改成功。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">恭喜你登录密码修改成功。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'登录密码修改成功','登录密码修改成功',1,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (18,'注册','注册成功',1,0,1,0,0,0,0,0,0,'亲爱的{$userName}，欢迎您加入{$platform}，我们将为您提供绝对100%安全的购彩流程，感谢对{$platform}的支持，希望您得到愉悦的游戏体验。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">亲爱的{$userName}，欢迎您加入{$platform}，我们将为您提供绝对100%安全的购彩流程，感谢对{$platform}的支持，希望您得到愉悦的游戏体验。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'注册成功信息','注册成功信息',2,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (5,'信息变动','邮箱绑定时（验证邮箱）',1,0,0,1,1,0,0,0,0,null,'<table width="599" cellpadding="0" cellspacing="0" border="0" align="center">
  <tr>
    <td colspan="3" width="599" height="80" style="background:#076256;" align="center">
      <img src="{$path_img}/images/edm/edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台">
    </td>
  </tr>
  <tr>
    <td style="background:#076256;" height="320" width="8"></td>
    <td align="center">
      <table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;">
        <tr>
          <td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$uName}用户，您好。</td>
        </tr>
        <tr>
          <td style="font-size:14px;line-height:24px;">欢迎使用firefrog绑定邮箱功能，绑定邮箱后可增强账号安全性以及通过邮箱找回其他安全信息。<br/>请点击链接确认绑定邮箱：
            <a href="{$activeLink}">{$activeLink}</a><br/>(该链接在24小时内有效，24小时后需要重新获取)<br/><br/>
            <span style="font-size:12px;font-weight:bold;">如果上面不是链接形式，请将地址复制到您的浏览器(例如IE)的地址栏再访问。</span>
          </td>
        </tr>
        <tr>
          <td style="font-size:12px;" height="64">感谢您对firefrog的支持，希望您在firefrog得到愉悦的游戏体验。<br/>这是一封系统邮件，请勿回复。</td>
        </tr>
      </table>
    </td>
    <td width="8" height="320" style="background:#076256; font-size:0;"></td>
  </tr>
  <tr>
    <td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">&copy;2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td>
  </tr>
</table>',null,null,'请验证绑定邮箱操作','请验证绑定邮箱操作',2,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (6,'信息变动','邮箱修改（验证老邮箱）',1,0,0,1,1,0,0,0,0,null,'<table width="599" cellpadding="0" cellspacing="0" border="0" align="center">
  <tr>
    <td colspan="3" width="599" height="80" style="background:#076256;" align="center">
      <img src="{$path_img}/images/edm/edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台">
    </td>
  </tr>
  <tr>
    <td style="background:#076256;" height="320" width="8"></td>
    <td align="center">
      <table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;">
        <tr>
          <td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$uName}用户，您好。</td>
        </tr>
        <tr>
          <td style="font-size:14px;line-height:24px;">欢迎使用firefrog更换绑定邮箱功能。<br/>请点击链接完成更换绑定邮箱：
            <a href="{$activeLink}">{$activeLink}</a><br/>(该链接在24小时内有效，24小时后需要重新获取)<br/><br/>
            <span style="font-size:12px;font-weight:bold;">如果上面不是链接形式，请将地址复制到您的浏览器(例如IE)的地址栏再访问。</span>
          </td>
        </tr>
        <tr>
          <td style="font-size:12px;" height="64">感谢您对firefrog的支持，希望您在firefrog得到愉悦的游戏体验。<br/>这是一封系统邮件，请勿回复。</td>
        </tr>
      </table>
    </td>
    <td width="8" height="320" style="background:#076256; font-size:0;"></td>
  </tr>
  <tr>
    <td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">&copy;2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td>
  </tr>
</table>',null,null,'请验证更换绑定邮箱操作','请验证更换绑定邮箱操作',2,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (19,'资金变动','充值到账',1,1,1,1,1,0,0,0,0,'感谢您在{$platform}购彩！我们很高兴的通知您，您的充值已经到账，金额为{$rechargeMoney}。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">感谢您在{$platform}购彩！我们很高兴的通知您，您的充值已经到账，金额为{$rechargeMoney}。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'充值到账通知','充值到账通知',1,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (1,'信息变动','银行卡绑定成功',1,1,1,1,1,0,0,0,0,'亲爱的{$userName}，恭喜你银行卡绑定成功。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">恭喜你银行卡绑定成功。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'银行卡绑定成功','银行卡绑定成功',2,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (16,'信息变动','预留信息修改',1,1,1,1,1,0,0,0,0,'亲爱的{$userName}，恭喜你预留信息修改成功。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">恭喜你预留信息修改成功。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'预留信息修改成功','预留信息修改成功',1,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (14,'信息变动','安全密码修改',1,1,1,1,1,0,0,0,0,'亲爱的{$userName}，恭喜你安全密码修改成功。','<table width="599" cellspacing="0" cellpadding="0" border="0"align="center"><tr><td colspan="3" width="599" height="80" style="background: #076256;"align="center"><imgsrc="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png"width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background: #076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520"style="text-align: left; color: #00564d; font-size: 0;"><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size: 14px; line-height: 24px;">恭喜你安全密码修改成功。</td></tr><tr><td height="64" style="font-size: 12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background: #076256; font-size: 0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center"style="background: #076256; color: #FFF; font-size: 12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'安全密码修改提醒','安全密码修改提醒',1,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (21,'资金变动','转账成功',1,1,1,1,1,0,0,0,0,'感谢您在{$platform}购彩！我们很高兴的通知您，您已成功转账，金额为{$transferMoney}。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">感谢您在{$platform}购彩！我们很高兴的通知您，您已成功转账，金额为{$transferMoney}。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'转账成功通知','转账成功通知',1,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (22,'资金变动','提现审核未通过',1,1,1,1,1,0,0,0,0,'感谢您对{$platform}的支持！很遗憾的通知您，您于{$operateDate}发起的提现申请没有审核通过，详情请联系在线客服。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">感谢您对{$platform}的支持！很遗憾的通知您，您于{$operateDate}发起的提现申请没有审核通过，详情请联系在线客服。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'提现审核未通过通知','提现审核未通过通知',1,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (7,'信息变动','邮箱修改（验证新邮箱）',1,0,0,1,1,0,0,0,0,null,'<table width="599" cellpadding="0" cellspacing="0" border="0" align="center">
  <tr>
    <td colspan="3" width="599" height="80" style="background:#076256;" align="center">
      <img src="{$path_img}/images/edm/edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台">
    </td>
  </tr>
  <tr>
    <td style="background:#076256;" height="320" width="8"></td>
    <td align="center">
      <table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;">
        <tr>
          <td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$uName}用户，您好。</td>
        </tr>
        <tr>
          <td style="font-size:14px;line-height:24px;">欢迎使用firefrog绑定邮箱功能,绑定邮箱后可增强账号安全性以及通过邮箱找回其他安全信息。<br/>请点击链接完成绑定邮箱：
            <a href="{$activeLink}">{$activeLink}</a><br/>(该链接在24小时内有效，24小时后需要重新获取)<br/><br/>
            <span style="font-size:12px;font-weight:bold;">如果上面不是链接形式，请将地址复制到您的浏览器(例如IE)的地址栏再访问。</span>
          </td>
        </tr>
        <tr>
          <td style="font-size:12px;" height="64">感谢您对firefrog的支持，希望您在firefrog得到愉悦的游戏体验。<br/>这是一封系统邮件，请勿回复。</td>
        </tr>
      </table>
    </td>
    <td width="8" height="320" style="background:#076256; font-size:0;"></td>
  </tr>
  <tr>
    <td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">&copy;2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td>
  </tr>
</table>',null,null,'请验证绑定邮箱操作','请验证绑定邮箱操作',2,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (8,'信息变动','找回登录密码---通过邮箱找回（验证邮箱）',1,0,0,1,1,0,0,0,0,null,'<table width="599" cellpadding="0" cellspacing="0" border="0" align="center">
  <tr>
    <td colspan="3" width="599" height="80" style="background:#076256;" align="center">
      <img src="{$path_img}/images/edm/edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台">
    </td>
  </tr>
  <tr>
    <td style="background:#076256;" height="320" width="8"></td>
    <td align="center">
      <table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;">
        <tr>
          <td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$uName}用户，您好。</td>
        </tr>
        <tr>
          <td style="font-size:14px;line-height:24px;">欢迎使用firefrog找回密码功能。<br/>请点击链接重置密码：
            <a href="{$activeLink}">{$activeLink}</a><br/>(该链接在24小时内有效，24小时后需要重新获取)<br/><br/>
            <span style="font-size:12px;font-weight:bold;">如果上面不是链接形式，请将地址复制到您的浏览器(例如IE)的地址栏再访问。</span>
          </td>
        </tr>
        <tr>
          <td style="font-size:12px;" height="64">感谢您对firefrog的支持，希望您在firefrog得到愉悦的游戏体验。<br/>这是一封系统邮件，请勿回复。</td>
        </tr>
      </table>
    </td>
    <td width="8" height="320" style="background:#076256; font-size:0;"></td>
  </tr>
  <tr>
    <td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">&copy;2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td>
  </tr>
</table>',null,null,'找回登录密码验证邮件','找回登录密码验证邮件',2,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (9,'信息变动','找回安全密码（验证邮箱）',1,0,0,1,1,0,0,0,0,null,'<table width="599" cellpadding="0" cellspacing="0" border="0" align="center">
  <tr>
    <td colspan="3" width="599" height="80" style="background:#076256;" align="center">
      <img src="{$path_img}/images/edm/edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台">
    </td>
  </tr>
  <tr>
    <td style="background:#076256;" height="320" width="8"></td>
    <td align="center">
      <table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;">
        <tr>
          <td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$uName}用户，您好。</td>
        </tr>
        <tr>
          <td style="font-size:14px;line-height:24px;">欢迎使用firefrog找回安全密码功能。<br/>请点击链接重置安全密码：
            <a href="{$activeLink}">{$activeLink}</a><br/>(该链接在24小时内有效，24小时后需要重新获取)<br/><br/>
            <span style="font-size:12px;font-weight:bold;">如果上面不是链接形式，请将地址复制到您的浏览器(例如IE)的地址栏再访问。</span>
          </td>
        </tr>
        <tr>
          <td style="font-size:12px;" height="64">感谢您对firefrog的支持，希望您在firefrog得到愉悦的游戏体验。<br/>这是一封系统邮件，请勿回复。</td>
        </tr>
      </table>
    </td>
    <td width="8" height="320" style="background:#076256; font-size:0;"></td>
  </tr>
  <tr>
    <td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">&copy;2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td>
  </tr>
</table>',null,null,'找回安全密码验证邮件','找回安全密码验证邮件',2,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (3,'信息变动','银行解锁成功',1,1,1,1,1,0,0,0,0,'亲爱的{$userName}，恭喜你银行卡功能解锁成功。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">恭喜你银行卡功能解锁成功。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'银行卡解锁成功','银行卡解锁成功',2,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (4,'信息变动','银行卡删除成功',1,1,1,1,1,0,0,0,0,'亲爱的{$userName}，恭喜你银行卡删除成功。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">恭喜你银行卡删除成功。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'银行卡删除成功','银行卡删除成功',2,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (11,'信息变动','安全问题设置',1,1,1,1,1,0,0,0,0,'亲爱的{$userName}，恭喜你安全问题设置成功。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">恭喜你安全问题设置成功。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'安全问题设置成功','安全问题设置成功',1,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (12,'信息变动','安全问题修改',1,1,1,1,1,0,0,0,0,'亲爱的{$userName}，恭喜你安全问题修改成功。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">恭喜你安全问题修改成功。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'安全问题修改成功','安全问题修改成功',1,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (13,'信息变动','安全密码设置',1,1,1,1,1,0,0,0,0,'亲爱的{$userName}，恭喜你安全密码设置成功。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">恭喜你安全密码设置成功。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'安全密码设置成功','安全密码设置成功',1,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (20,'资金变动','提现成功',1,1,1,1,1,0,0,0,0,'感谢您在{$platform}购彩！我们很高兴的通知您，您的提现申请已经成功，金额为{$drawMoney}。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">感谢您在{$platform}购彩！我们很高兴的通知您，您的提现申请已经成功，金额为{$drawMoney}。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'提现到账通知','提现到账通知',1,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (24,'登录','多端登录提醒',1,1,1,1,1,0,0,0,0,'您的账号在另一台设备上登录。如果这不是你本人的操作，那么你的密码可能已经泄露，建议你修改密码。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">您的账号在另一台设备上登录。如果这不是你本人的操作，那么你的密码可能已经泄露，建议你修改密码。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'多端登录提醒','多端登录提醒',2,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (25,'登录','异地登录提醒',1,1,1,1,1,0,0,0,0,'系统检测到您的账号本次登录地点{$currentIpAddr}与上次登录地点{$oldIpAddr}不一致，如不是您本人操作，请尽快修改密码。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">系统检测到您的账号本次登录地点{$currentIpAddr}与上次登录地点{$oldIpAddr}不一致，如不是您本人操作，请尽快修改密码。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'异地登录提醒','异地登录提醒',2,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (26,'密码提醒','定期提醒修改安全密码',1,1,1,1,1,0,0,0,0,'亲爱的{$userName}，您已经两个月没有修改安全密码了，为了提高您的账号安全，请定期修改安全密码。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">亲爱的{$userName}，您已经两个月没有修改安全密码了，为了提高您的账号安全，请定期修改安全密码。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'安全密码修改提醒','安全密码修改提醒',2,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (27,'密码提醒','定期提醒修改登录密码',1,1,1,1,1,0,0,0,0,'亲爱的{$userName}，您已经两个月没有修改登录密码了，为了提高您的账号安全，请定期修改登录密码。','<table width="599" cellspacing="0" cellpadding="0" border="0" align="center" ><tr><td colspan="3" width="599" height="80" style="background:#076256;" align="center"><img src="http://192.168.1.111:8091/8a9c4696-d3f9-425c-8185-678bf1e392a4_edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台"></td></tr><tr><td width="8" style="background:#076256;"></td><td align="center"><table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;"  ><tr><td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$platform}用户，您好。</td></tr><tr><td style="font-size:14px;line-height:24px;">亲爱的{$userName}，您已经两个月没有修改登录密码了，为了提高您的账号安全，请定期修改登录密码。</td></tr><tr><td height="64" style="font-size:12px;">感谢对firefrog的支持，希望您在firefrog得愉悦的游戏体验。<br />这是一封系统邮件，请勿回复。</td></tr></table></td><td width="8" height="220" style="background:#076256; font-size:0;"></td></tr><tr><td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">chr(38)copy; 2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td></tr></table>',null,null,'登录密码修改提醒','登录密码修改提醒',2,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (28,'信息变动','账号申诉--绑定邮箱（验证邮箱）',1,0,0,1,1,0,0,0,0,null,'<table width="599" cellpadding="0" cellspacing="0" border="0" align="center">
	<tr>
		<td colspan="3" width="599" height="80" style="background:#076256;" align="center">
			<img src="{$path_img}/images/edm/edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台">
		</td>
	</tr>
	<tr>
		<td style="background:#076256;" height="320" width="8"></td>
		<td align="center">
			<table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;">
				<tr>
					<td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$uName}用户，您好。</td>
				</tr>
				<tr>
					<td style="font-size:14px;line-height:24px;">欢迎使用firefrog绑定邮箱验证功能。<br/>请点击链接确认绑定邮箱：
						<a href="{$activeLink}">{$activeLink}</a><br/>(该链接在24小时内有效，24小时后需要重新获取)<br/><br/>
						<span style="font-size:12px;font-weight:bold;">如果上面不是链接形式，请将地址复制到您的浏览器(例如IE)的地址栏再访问。</span>
					</td>
				</tr>
				<tr>
					<td style="font-size:12px;" height="64">感谢您对firefrog的支持，希望您在firefrog得到愉悦的游戏体验。<br/>这是一封系统邮件，请勿回复。</td>
        </tr>
      </table>
    </td>
    <td width="8" height="320" style="background:#076256; font-size:0;"></td>
  </tr>
  <tr>
    <td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">&copy;2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td>
  </tr>
</table>',null,null,'绑定邮箱验证','绑定邮箱验证',1,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (30,'信息变动','账号申诉-审核通过',1,0,0,1,1,0,0,0,0,'0','<table width="599" cellpadding="0" cellspacing="0" border="0" align="center">
	<tr>
		<td colspan="3" width="599" height="80" style="background:#076256;" align="center">
			<img src="{$path_img}/images/edm/edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台">
		</td>
	</tr>
	<tr>
		<td style="background:#076256;" height="320" width="8"></td>
		<td align="center">
			<table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;">
        <tr>
          <td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$uName}用户，您好。</td>
        </tr>
        <tr>
          <td style="font-size:14px;line-height:24px;">欢迎使用firefrog账号申诉功能。<br>恭喜您{$thdtitle}审核通过，请点击链接{$thdtitle}：<br>
            <a href="{$activeLink}">{$activeLink}</a><br/>(该链接在24小时内有效，24小时后需要重新获取)<br/><br/>
            <span style="font-size:12px;font-weight:bold;">如果上面不是链接形式，请将地址复制到您的浏览器(例如IE)的地址栏再访问。</span>
          </td>
        </tr>
        <tr>
          <td style="font-size:12px;" height="64">感谢您对firefrog的支持，希望您在firefrog得到愉悦的游戏体验。<br/>这是一封系统邮件，请勿回复。</td>
        </tr>
      </table>
    </td>
    <td width="8" height="320" style="background:#076256; font-size:0;"></td>
  </tr>
  <tr>
    <td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">&copy;2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td>
  </tr>
</table>','0','0','娱乐平台账号申诉成功通知','娱乐平台账号申诉成功通知',1,null);
Insert into FIREFOG.NOTICE_TASK (ID,MODULE,TASK,ACTIVATED,INNER_MSG_ACTIVATED,INNER_MSG_USED,EMAIL_ACTIVATED,EMAIL_USED,NOTE_ACTIVATED,NOTE_USED,EMS_ACTIVATED,EMS_USED,INNER_MSG_TEMP,EMAIL_TEMP,NOTE_TEMP,EMS_TEMP,INNER_MSG_TITLE,EMAIL_TITLE,SET_BY_USER,TEMPLATE_PARAMS) values (29,'信息变动','账号申诉-审核未通过',1,0,0,1,1,0,0,0,0,'0','<table width="599" cellpadding="0" cellspacing="0" border="0" align="center">
	<tr>
		<td colspan="3" width="599" height="80" style="background:#076256;" align="center">
			<img src="{$path_img}/images/edm/edm-logo.png" width="140" height="80" alt="firefrog.com 火蛙平台">
		</td>
	</tr>
	<tr>
		<td style="background:#076256;" height="320" width="8"></td>
		<td align="center">
			<table cellpadding="0" cellspacing="0" width="520" style="text-align:left;color: #00564d; font-size:0;">
				<tr>
					<td height="70" style="font-weight: bold; font-size: 14px;">Hi！亲爱的{$uName}用户，您好。</td>
        </tr>
        <tr>
          <td style="font-size:14px;line-height:24px;">欢迎使用firefrog账号申诉功能。<br></td>
        </tr>
        <tr>
          <td style="font-weight: bold; font-size: 12px; "height="32" >经审核，您提供的资料不够完善，申诉失败，请联系客服重新填写申诉资料。</td>
        </tr>
        <tr>
          <td style="font-size:12px;" height="64">感谢您对firefrog的支持，希望您在firefrog得到愉悦的游戏体验。<br/>这是一封系统邮件，请勿回复。</td>
        </tr>
      </table>
    </td>
    <td width="8" height="320" style="background:#076256; font-size:0;"></td>
  </tr>
  <tr>
    <td colspan="3" width="599" height="50" align="center" style="background:#076256;color:#FFF;font-size:12px;">&copy;2001-2012 firefrog.com All rights reserved. 火蛙游戏平台版权所有</td>
  </tr>
</table>','0','0','娱乐平台账号申诉审核未通过','娱乐平台账号申诉审核未通过',1,null);






insert into NOTICE_TASK  (ID,ACTIVATED,  EMAIL_ACTIVATED, MODULE,TASK,INNER_MSG_TEMP, inner_msg_activated, inner_msg_used , EMAIL_TEMP, INNER_MSG_TITLE, EMAIL_TITLE, SET_BY_USER)values (31, 1, 1,'资金变动','子系統轉出成功', '感谢您在{$platform}购彩！我们很高兴的通知您，您已成功转账，从 彩票帐户 {$ffaccount} 转至 老虎机帐户{$ptaccount}金额为{$transferMoney}元。', 1, 1, '<table width="599" align="center" border="0" cellspacing="0" cellpadding="0"><tbody><tr><td width="599" height="80" align="center" style="background: rgb(7, 98, 86);" colspan="3">&nbsp;</td></tr><tr><td width="8" style="background: rgb(7, 98, 86);">&nbsp;</td><td align="center"><table width="520" style="text-align: left; color: rgb(0, 86, 77); font-size: 0px;" cellspacing="0" cellpadding="0"><tbody><tr><td height="70" style="font-size: 14px; font-weight: bold;"><p>Hi！亲爱的{$userName}用户，您好。</p></td></tr><tr><td style="line-height: 24px; font-size: 14px;">感谢您在娱乐平台游戏！我们很高兴的通知您，您已成功转账，从 彩票帐户 {$ffaccount} 转至 老虎机帐户{$ptaccount}金额为{$transferMoney}元。</td></tr><tr><td height="64" style="font-size: 12px;"><p>感谢对娱乐平台的支持，希望您在娱乐平台得到愉悦的游戏体验。<br /></p><p style="color: rgb(0, 86, 77);">这是一封系统邮件，请勿回复。</p><p style="color: rgb(0, 86, 77);">如果不是您本人进行的操作，您的账号可能存在安全风险，请尽快修改密码或联系客服。</p></td></tr></tbody></table></td><td width="8" height="220" style="background: rgb(7, 98, 86); font-size: 0px;">&nbsp;</td></tr><tr><td width="599" height="50" align="center" style="background: rgb(7, 98, 86); color: rgb(255, 255, 255); font-size: 12px;" colspan="3">&nbsp;</td></tr></tbody></table>', '转账成功通知', '转账成功通知',1)


insert into NOTICE_TASK  (ID,ACTIVATED, EMAIL_ACTIVATED, MODULE,TASK,INNER_MSG_TEMP, inner_msg_activated, inner_msg_used , EMAIL_TEMP, INNER_MSG_TITLE, EMAIL_TITLE, SET_BY_USER)values (32,1, 1,'资金变动','子系統轉回成功', '感谢您在{$platform}购彩！我们很高兴的通知您，您已成功转账，从 老虎机帐户 {$ptaccount} 转至 彩票帐户{$ffaccount}金额为{$transferMoney}元。', 1, 1, '<table width="599" align="center" border="0" cellspacing="0" cellpadding="0"><tbody><tr><td width="599" height="80" align="center" style="background: rgb(7, 98, 86);" colspan="3">&nbsp;</td></tr><tr><td width="8" style="background: rgb(7, 98, 86);">&nbsp;</td><td align="center"><table width="520" style="text-align: left; color: rgb(0, 86, 77); font-size: 0px;" cellspacing="0" cellpadding="0"><tbody><tr><td height="70" style="font-size: 14px; font-weight: bold;"><p>Hi！亲爱的{$userName}用户，您好。</p></td></tr><tr><td style="line-height: 24px; font-size: 14px;">感谢您在娱乐平台游戏！我们很高兴的通知您，您已成功转账，从 老虎机帐户 {$ptaccount} 转至 彩票帐户{$ffaccount}金额为{$transferMoney}元。</td></tr><tr><td height="64" style="font-size: 12px;"><p>感谢对娱乐平台的支持，希望您在娱乐平台得到愉悦的游戏体验。<br /></p><p style="color: rgb(0, 86, 77);">这是一封系统邮件，请勿回复。</p><p style="color: rgb(0, 86, 77);">如果不是您本人进行的操作，您的账号可能存在安全风险，请尽快修改密码或联系客服。</p></td></tr></tbody></table></td><td width="8" height="220" style="background: rgb(7, 98, 86); font-size: 0px;">&nbsp;</td></tr><tr><td width="599" height="50" align="center" style="background: rgb(7, 98, 86); color: rgb(255, 255, 255); font-size: 12px;" colspan="3">&nbsp;</td></tr></tbody></table>', '转账成功通知', '转账成功通知',1)

--------------------------------------------------------
--  DDL for Index SYS_C0018959
--------------------------------------------------------

  CREATE UNIQUE INDEX "FIREFOG"."SYS_C0018959" ON "FIREFOG"."NOTICE_TASK" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "FIRE" ;
--------------------------------------------------------
--  Constraints for Table NOTICE_TASK
--------------------------------------------------------

  ALTER TABLE "FIREFOG"."NOTICE_TASK" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "FIRE"  ENABLE;
