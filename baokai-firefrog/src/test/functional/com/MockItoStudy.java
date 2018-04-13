package com;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.winterframework.firefrog.sample.dao.TaskDao;
import com.winterframework.firefrog.sample.service.TaskManager;

@RunWith(MockitoJUnitRunner.class)
public class MockItoStudy {
	@Mock
	private TaskManager accountManager;
	@Mock
	private TaskDao mockedUserDao;

	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testA() {
		when(accountManager.getAll()).thenReturn(new ArrayList());
		Object obj = accountManager.getAll();
		Assert.assertTrue(true);
	}
}
