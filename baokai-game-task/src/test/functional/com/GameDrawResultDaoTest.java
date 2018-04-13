package com;

import static org.junit.Assert.*;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;

/**
 * @author Pogi.Lin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/applicationContext-resource.xml",
		"classpath:/applicationContext-dao.xml"
		})
@Transactional
public class GameDrawResultDaoTest {
	@Resource(name = "gameDrawResultDaoImpl")
	private IGameDrawResultDao gameDrawResultDao;
	
	/**
	 * 測試重複新增 PK 資料。
	 */
	@Test
	public void testInsertRepeat() {
		Date time = new Date();
		GameDrawResult drawResult = new GameDrawResult();
		drawResult.setLotteryid(99105L);
		drawResult.setIssueCode(20161202105083L);
		drawResult.setWebIssueCode("0173918");
		drawResult.setNumberRecord("49790");
		drawResult.setCreateTime(time);
		drawResult.setOpenDrawTime(time);
		drawResult.setType(0L);
		try {
			gameDrawResultDao.insert(drawResult);
		} catch(Exception e) {
			if(!e.getMessage().contains("ORA-00001")) {
				fail("沒有成功擋下 PK 重複");
			} else {
				System.out.println(e.getMessage());
			}
		}
	}

}
