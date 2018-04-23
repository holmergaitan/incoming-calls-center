package com.callcenter.app.controller;

import com.callcenter.app.model.call.Call;
import com.callcenter.app.model.employee.AbstractEmployee;
import com.callcenter.app.service.IncomingCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Rest controller to intercept requests related with {@link Call} operations
 * like update and save.
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
@RestController
@RequestMapping("/calls")
public class IncomingCallController {

	/**
	 * Incoming call service that contains operations to answer calls
	 */
	private IncomingCallService callCenter;

	/**
	 * Instantiates a new incoming call controller.
	 *
	 * @param callCenter
	 *            the call center
	 */
	@Autowired
	public IncomingCallController(final IncomingCallService callCenter) {

		this.callCenter = callCenter;
	}

	/**
	 * Stores a new {@link com.callcenter.app.model.call.CallState#PENDING} calll in
	 * the {@link IncomingCallService} that will be processed by one of
	 * {@link AbstractEmployee} queued in the IncomingCallService.
	 *
	 * @param incomingCall
	 *            the incoming call that will be stored in the
	 *            {@link IncomingCallService}
	 * @return the response entity with HttpStatus
	 */
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> makeCall(@RequestBody Call incomingCall) {

		callCenter.makeCall(incomingCall);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	/**
	 * Stores a list of {@link com.callcenter.app.model.call.CallState#PENDING}
	 * calls in the {@link IncomingCallService} that will be processed by one of
	 * {@link AbstractEmployee} queued in the IncomingCallService.
	 *
	 * @param calls
	 *            the incoming calls that will be stored in the
	 *            {@link IncomingCallService}
	 * @return the response entity with HttpStatus
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/sendcalls")
	public ResponseEntity<?> makeCalls(@RequestBody List<Call> calls) {

		callCenter.sendCalls(calls);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	/**
	 * Answer calls all pending calls in the {@link IncomingCallService} if there
	 * are available Employees.
	 *
	 * @return the response entity with HttpStatus
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/answercalls")
	public ResponseEntity<?> answerCalls() {

		callCenter.answerAllCalls();
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	/**
	 * Gets the call results from the thread task results
	 *
	 * @return the call results that contains thread execution information
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/results")
	public List<Future<String>> getCallResults() {

		return callCenter.getTaskResults();
	}

	/**
	 * Get all pending calls in the {@link IncomingCallService}
	 *
	 * @return the list with all pending calls
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/pendingcalls")
	public List<Call> getPendingCalls() {

		return callCenter.getPendingCalls();
	}

	/**
	 * Gets all calls stored in the {@link IncomingCallService}
	 *
	 * @return all stored calls
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/allcalls")
	public List<Call> getAllCalls() {

		return callCenter.getCalls();
	}
}
