package com.winterframework.firefrog.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import com.winterframework.firefrog.event.base.BaseEvent;
import com.winterframework.firefrog.event.base.BaseEventListener;
import com.winterframework.firefrog.game.dao.vo.GameTrendTask;
 
@Component("notyEventListener")
public class GameTrendEventListener extends BaseEventListener{
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(200);
	
	@Override
	public void onApplicationEvent(BaseEvent event) {
		/*if (event instanceof GameTrendEvent) {
			GameTrendTask trendTask=(GameTrendTask)event.getSource();
			threadPool.execute(new Thread(new GameTrendProcessor(trendTask)));
		}*/
	}

}
