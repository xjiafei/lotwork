CREATE TABLE `ad_user` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名' ,
`password`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码' ,
`type`  int(1) NOT NULL COMMENT '用户类型 1管理员 2用户' ,
`status`  int(1) NULL DEFAULT 1 COMMENT '0不可用  1可用' ,
`parent_id`  int(11) NULL DEFAULT NULL ,
`remark`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`creator_id`  decimal(22,0) NOT NULL COMMENT '创建人' ,
`create_time`  datetime NOT NULL COMMENT '创建时间' ,
`updator_id`  decimal(22,0) NULL DEFAULT NULL COMMENT '更新人' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=7
ROW_FORMAT=COMPACT
;

CREATE TABLE `ad_device` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`user_id`  int(11) NULL DEFAULT NULL COMMENT '用户id' ,
`code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备号' ,
`address`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址' ,
`status`  int(1) NULL DEFAULT NULL ,
`heartbeat_time`  datetime NULL DEFAULT NULL ,
`onffline`  int(1) NULL DEFAULT 0 COMMENT '0不在线  1在线' ,
`battery`  int(11) NULL DEFAULT NULL COMMENT '电量' ,
`lock_screen`  int(1) NULL DEFAULT NULL COMMENT '0锁屏  1不锁屏' ,
`remark`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`creator_id`  decimal(22,0) NOT NULL COMMENT '创建人' ,
`create_time`  datetime NOT NULL COMMENT '创建时间' ,
`updator_id`  decimal(22,0) NULL DEFAULT NULL COMMENT '更新人' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

CREATE TABLE `ad_resource` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`user_id`  int(11) NOT NULL COMMENT '用户id' ,
`ext_Type`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展类型(jpg,png,mp3...)' ,
`file_Path`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径' ,
`file_Name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称' ,
`remark`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`creator_id`  decimal(22,0) NOT NULL COMMENT '创建人' ,
`create_time`  datetime NOT NULL COMMENT '创建时间' ,
`updator_id`  decimal(22,0) NULL DEFAULT NULL COMMENT '更新人' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=56
ROW_FORMAT=COMPACT
;

CREATE TABLE `ad_publish_device` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`version`  int(11) NOT NULL COMMENT '版本' ,
`device_id`  int(11) NOT NULL COMMENT '设备id' ,
`status`  int(1) NOT NULL COMMENT '设备更新状态  0无  1有' ,
`remark`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`creator_id`  decimal(22,0) NOT NULL COMMENT '创建人' ,
`create_time`  datetime NOT NULL COMMENT '创建时间' ,
`updator_id`  decimal(22,0) NULL DEFAULT NULL COMMENT '更新人' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=124
ROW_FORMAT=COMPACT
;

CREATE TABLE `ad_version_resource` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`version`  int(11) NOT NULL COMMENT '版本' ,
`user_id`  int(11) NOT NULL ,
`resource_id`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' ,
`status`  int(1) NOT NULL ,
`remark`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`creator_id`  decimal(22,0) NOT NULL COMMENT '创建人' ,
`create_time`  datetime NOT NULL COMMENT '创建时间' ,
`updator_id`  decimal(22,0) NULL DEFAULT NULL COMMENT '更新人' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=13
ROW_FORMAT=COMPACT
;

insert into ad_user(name,password,type,creator_id,create_time) values('admin','123456',1,-1,now());
