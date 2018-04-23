package com.callcenter.app.service;

import com.callcenter.app.model.call.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The Class IncomingCallService.
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
@Component
public class IncomingCallService {

	/**	Executor service instance */
	private ExecutorService excecutorService;

	/** Contains the thread number used by the executor service. */
	private static final int THREAD_NUMBER = 10;

	/** Dispatch pending calls in the system. */
	private CallDispatcher callDispatcher;

	/** The task results. */
	private List<Future<String>> taskResults = new ArrayList<>();

	/**
	 * Instantiates a new incoming call service.
	 *
	 * @param callDispatcher
	 *            the call dispatcher
	 */
	@Autowired
	public IncomingCallService(final CallDispatcher callDispatcher) {

		this.callDispatcher = callDispatcher;
		this.excecutorService =  Executors.newFixedThreadPool(THREAD_NUMBER);
		taskResults = new ArrayList<>();
	}

	/**
	 * Gets the task results.
	 *
	 * @return the task results
	 */
	public List<Future<String>> getTaskResults() {

		return taskResults;
	}

	/**
	 * Answer all calls.
	 */
	public void answerAllCalls() {

		for (int i = 0; i < callDispatcher.countPendingCalls(); i++) {

			taskResults.add(excecutorService.submit(callDispatcher));
		}
	}

	/**
	 * Answer a single call
	 */
	public void answerSingleCall(){

		taskResults.add(excecutorService.submit(callDispatcher));
	}

	/**
	 * Gets the pending calls.
	 *
	 * @return the pending calls
	 */
	public List<Call> getPendingCalls() {

		return callDispatcher.getPendingCalls();
	}

	/**
	 * Make call.
	 *
	 * @param call
	 *            the call
	 */
	public void makeCall(final Call call) {

		callDispatcher.receiveIncomingCall(call);
	}

	/**
	 * Gets the calls.
	 *
	 * @return the calls
	 */
	public List<Call> getCalls() {

		return callDispatcher.getCalls();
	}

	/**
	 * Send calls.
	 *
	 * @param calls
	 *            the calls
	 */
	public void sendCalls(final List<Call> calls) {

		callDispatcher.receiveIncomingCalls(calls);
	}
}
