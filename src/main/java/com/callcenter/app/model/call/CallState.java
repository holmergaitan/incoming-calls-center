package com.callcenter.app.model.call;

/**
 * Contains all {@link Call} states in the system
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
public enum CallState {

	/**
	 * This state is settled to a call when an employee answer it
	 */
	ANSWERED,

	/**
	 * This state is settled to a call when an employee starts the answering process
	 */
	IN_PROGRESS,

	/**
	 * Initial state related to a new call in the system
	 */
	PENDING,

	/**
	 * This state is settled when a call was never answered
	 */
	LOST
}
