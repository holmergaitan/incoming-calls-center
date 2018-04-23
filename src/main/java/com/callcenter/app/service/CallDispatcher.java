package com.callcenter.app.service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.PriorityBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.callcenter.app.databuilder.DataBuilder;
import com.callcenter.app.jpa.ICallDao;
import com.callcenter.app.model.call.Call;
import com.callcenter.app.model.call.CallState;
import com.callcenter.app.model.employee.AbstractEmployee;
import com.callcenter.app.model.employee.EmployeePriority;

/**
 * CallDispatcher manages all pending calls in the system. Contains a
 * {@link PriorityBlockingQueue} of employees and calls that will be managed by
 * their respective priority.
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
@Component
public class CallDispatcher implements Callable<String> {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(CallDispatcher.class);

	/** Contains all employees ordered by {@link EmployeePriority}. */
	private PriorityBlockingQueue<AbstractEmployee> employees;

	/** Contains all pending calls ordered by default priority */
	private PriorityBlockingQueue<Call> calls;

	/** Contains operations to store calls */
	private ICallDao callDao;

	/**
	 * Instantiates a new call dispatcher.
	 *
	 * @param callDao
	 *            the call dao
	 */
	@Autowired
	public CallDispatcher(final ICallDao callDao) {

		this.callDao = callDao;
		employees = DataBuilder.getEmployeesData();
		calls = DataBuilder.getCallData();

		callDao.saveAll(calls);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public String call() throws Exception {

		return dispatchCall();
	}

	/**
	 * Receive incoming call in the queue system.
	 *
	 * @param call
	 *            the call
	 */
	public void receiveIncomingCall(final Call call) {

		call.setState(CallState.PENDING);
		calls.put(call);
		callDao.saveAll(calls);
	}

	/**
	 * Count pending calls stored in the queue system.
	 *
	 * @return the integer
	 */
	public Integer countPendingCalls() {

		return calls.size();
	}

	/**
	 * Gets all pending calls.
	 *
	 * @return the pending calls
	 */
	public List<Call> getPendingCalls() {

		return callDao.getCallsByState(CallState.PENDING);
	}

	/**
	 * Gets all calls sent to the system.
	 *
	 * @return the calls
	 */
	public List<Call> getCalls() {

		return (List<Call>) callDao.findAll();
	}

	/**
	 * Receive incoming calls and store it in the queue system.
	 *
	 * @param calls
	 *            the calls
	 */
	public void receiveIncomingCalls(final List<Call> calls) {

		calls.forEach(call -> receiveIncomingCall(call));
	}

	/**
	 * Dispatch a call. Process the call with an available employee If there is not
	 * available employees, return the call to the queue,
	 *
	 * @return the string with thread execution status
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	private String dispatchCall() throws InterruptedException {

		final Call currentCall = calls.poll();
		if (currentCall != null) {

			LOGGER.info("Sending call from: [{}]. Processed by thread: [{}]",  
					currentCall.getName(), Thread.currentThread().getName());
			
			final AbstractEmployee availableEmployee = employees.poll();
			if (availableEmployee != null) {

				availableEmployee.answerCall(currentCall);
				employees.add(availableEmployee);
				callDao.save(currentCall);
			} else {

				currentCall.setResponse("This call has not been answered yet!");
				currentCall.setAttempts(Integer.sum(currentCall.getAttempts(), 1));
				currentCall.setState(CallState.PENDING);
				this.receiveIncomingCall(currentCall);
				return currentCall.getResponse();
			}
		}

		return "No more calls!";
	}
}
