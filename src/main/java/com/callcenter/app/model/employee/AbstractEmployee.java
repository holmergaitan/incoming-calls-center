package com.callcenter.app.model.employee;

import com.callcenter.app.model.call.Call;
import com.callcenter.app.model.call.CallState;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Contains the principal information related to Employees
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
public abstract class AbstractEmployee {

	/** The employee name. */
	private String name;

	/** Employee priority in calls answering process. */
	private EmployeePriority priority;

	/**
	 * Instantiates a new abstract employee.
	 *
	 * @param name
	 *            the employee name
	 * @param employeePriority
	 *            the employee priority in calls answering process
	 */
	public AbstractEmployee(final String name, final EmployeePriority employeePriority) {

		this.name = name;
		this.priority = employeePriority;
	}

	/**
	 * Gets the employee name.
	 *
	 * @return the name
	 */
	public String getName() {

		return name;
	}

	/**
	 * Gets the employee priority.
	 *
	 * @return the priority
	 */
	public EmployeePriority getPriority() {

		return priority;
	}

	/**
	 * Answer an incoming call, sent by the IncomingCallService. The incoming call
	 * establish a duration that will be usted in the answering process
	 *
	 * @param currentCall
	 *            the current call that will be answered by te current employee
	 * @throws InterruptedException
	 *             if the current {@link Thread} is interrupted
	 */
	public void answerCall(final Call currentCall) throws InterruptedException {

		currentCall.setState(CallState.IN_PROGRESS);
		TimeUnit.SECONDS.sleep(currentCall.getDuration());
		currentCall.setState(CallState.ANSWERED);
		currentCall.setAnswerDate(new Date());
		currentCall.setAttempts(Integer.sum(currentCall.getAttempts(), 1));
		currentCall.setResponse(String.format("Answered by %s[%s]. Executed by thread: %s ", getName(), toString(),
				Thread.currentThread().getName()));
	}
}
