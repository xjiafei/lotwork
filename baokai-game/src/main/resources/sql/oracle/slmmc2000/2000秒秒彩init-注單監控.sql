--四星直选复式
INSERT INTO [ProjectMonitor].[dbo].[prize_level]
           ([platform]
           ,[method_id]
           ,[prize_level]
           ,[prize]
           ,[min_prize]
           )
     VALUES
           ('PH'
           ,20
           ,'2000'
           ,20000
           ,10000
           );

--四星直选单式
INSERT INTO [ProjectMonitor].[dbo].[prize_level]
           ([platform]
           ,[method_id]
           ,[prize_level]
           ,[prize]
           ,[min_prize]
           )
     VALUES
           ('PH'
           ,21
           ,'2000'
           ,20000
           ,10000
           );

--四星组选组选24
INSERT INTO [ProjectMonitor].[dbo].[prize_level]
           ([platform]
           ,[method_id]
           ,[prize_level]
           ,[prize]
           ,[min_prize]
           )
     VALUES
           ('PH'
           ,22
           ,'2000'
           ,833
           ,416
           );

--四星组选组选12
INSERT INTO [ProjectMonitor].[dbo].[prize_level]
           ([platform]
           ,[method_id]
           ,[prize_level]
           ,[prize]
           ,[min_prize]
           )
     VALUES
           ('PH'
           ,23
           ,'2000'
           ,1666
           ,833
           );

--四星组选组选4
INSERT INTO [ProjectMonitor].[dbo].[prize_level]
           ([platform]
           ,[method_id]
           ,[prize_level]
           ,[prize]
           ,[min_prize]
           )
     VALUES
           ('PH'
           ,25
           ,'2000'
           ,5000
           ,2500
           );
		   
--四星组选组选6
INSERT INTO [ProjectMonitor].[dbo].[prize_level]
           ([platform]
           ,[method_id]
           ,[prize_level]
           ,[prize]
           ,[min_prize]
           )
     VALUES
           ('PH'
           ,24
           ,'2000'
           ,3333
           ,1666
           );

--四星不定位一码不定位
INSERT INTO [ProjectMonitor].[dbo].[prize_level]
           ([platform]
           ,[method_id]
           ,[prize_level]
           ,[prize]
           ,[min_prize]
           )
     VALUES
           ('PH'
           ,26
           ,'2000'
           ,5.8
           ,2.9
           );

--四星不定位二码不定位
INSERT INTO [ProjectMonitor].[dbo].[prize_level]
           ([platform]
           ,[method_id]
           ,[prize_level]
           ,[prize]
           ,[min_prize]
           )
     VALUES
           ('PH'
           ,27
           ,'2000'
           ,20
           ,10
           );

--lottery
INSERT INTO [ProjectMonitor].[dbo].[lottery]
           ([lot_id]
           ,[chan_id]
           ,[cnname]
           ,[enname]
           ,[last_input]
		   ,[amount_cycle]
		   ,[amount]
		   ,[enable]
		   ,[is_process]
           )
     VALUES
           (99113
           ,5
           ,'超级2000秒秒彩（AAPP版）'
           ,'SLMMC2000'
		   ,GETDATE()
		   ,'day'
		   ,1440
	       ,1
		   ,0
           );


