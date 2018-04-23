package com.callcenter.app.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.callcenter.app.jpa.ICallDao;

/**
 * Test the {@link IncomingCallService} class
 * 
 * @author Holmer Gaitan
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class IncomingCallServiceMockTest extends PowerMockTestCase {
    
	/** The call dao. */
	@Mock
	private ICallDao callDao;

	/** The call dispatcher. */
	@InjectMocks
	private CallDispatcher callDispatcher;

	/**
	 * Send multiple calls answering attempts(10). The callDispatcher contains 16
	 * calls. After test execution, there will be 6 calls queued, pending for answer
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Test(priority = 0)
	public void answerMultipleCallsTest() throws InterruptedException {

        final IncomingCallService incomingCallService = new IncomingCallService(callDispatcher);
		Mockito.when(callDao.saveAll(Mockito.any(Iterable.class))).thenReturn(new ArrayList<>());

		for (int i = 0; i < 10; i++) {

			incomingCallService.answerSingleCall();
		}

		TimeUnit.SECONDS.sleep(20);
		Assert.assertEquals(Integer.valueOf(6), callDispatcher.countPendingCalls());
	}

	/**
	 * Try to answer all calls. There is 16 calls and 12 employees After test, there
	 * will be 4 calls pending for answer.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Test(priority = 1)
	public void answerAllCallsTest() throws InterruptedException {

        final IncomingCallService incomingCallService  = new IncomingCallService(callDispatcher);
		Mockito.when(callDao.saveAll(Mockito.any(Iterable.class))).thenReturn(new ArrayList<>());

		incomingCallService.answerSingleCall();
		Assert.assertEquals(Integer.valueOf(6), callDispatcher.countPendingCalls());
	}
}
