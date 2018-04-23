package com.callcenter.app.scheduler;

import com.callcenter.app.service.IncomingCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Executes tasks to answer pending calls in the system
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
@Component
public class PendingCallsScheduler {

	/** The call service. */
	private IncomingCallService callService;

	/**
	 * Instantiates a new pending calls scheduler.
	 *
	 * @param incomingCallService
	 *            the incoming call service that contains call operations
	 */
	@Autowired
	public PendingCallsScheduler(final IncomingCallService incomingCallService) {

		this.callService = incomingCallService;
	}

	/**
	 * Starts to answer pending calls stored in the {@link IncomingCallService}
	 */
	@Scheduled(cron = "${pendingcallscheduler.task}")
	public void answerPendingCalls() {

		System.out.println("Trying to answer pending calls....");
		callService.answerAllCalls();
	}
}
