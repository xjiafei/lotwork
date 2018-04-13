package com.winterframework.firefrog.schedule;

import java.lang.reflect.Method;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/** 
* @ClassName: DetailQuartzStateJobBean 
* @Description: 有状态的job 处理器，防止并发 
* @author david 
* @date 2013-11-26 上午10:21:28 
*  
*/
@Component("detailQuartzStateJobBean")
public class DetailQuartzStateJobBean extends QuartzJobBean implements StatefulJob {
	private String targetObject;
	private String targetMethod;
	private final ApplicationContext applicationContext;

	public DetailQuartzStateJobBean() {
		this.applicationContext = MyApplicationContextUtil.getContext();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			Object otargetObject = applicationContext.getBean(targetObject);
			Method m = null;
			try {
				m = otargetObject.getClass().getMethod(targetMethod, new Class[] {});
				m.invoke(otargetObject, new Object[] {});
			} catch (SecurityException e) {

			} catch (NoSuchMethodException e) {

			}
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}

	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}
}