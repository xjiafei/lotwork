package com.winterframework.firefrog.game.test.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.winterframework.firefrog.game.service.IGameCheckDrawService;
import com.winterframework.firefrog.game.test.BaseTestCase;



public class GameTest extends BaseTestCase {
	Logger log=LoggerFactory.getLogger(GameTest.class);

	@Resource(name = "gameCheckDrawServiceImpl")
	private IGameCheckDrawService gameCheckDrawService;
	
	@Test
	@Rollback(false)
	public void test() throws Exception{
		Long win=gameCheckDrawService.preCalculateWin("11111", 42027213L);
				System.out.println(win);
	}
	
}