package com.callcenter.app.model.employee;

/**
 * Contains all priority levels for {@link AbstractEmployee}
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
public enum EmployeePriority {

	/**
	 * High priority value related to {@link Operator} employees
	 */
	HIGH,

	/**
	 * Medium priority value related to {@link Supervisor} employees
	 */
	MEDIUM,

	/**
	 * Low priority value related to {@link Executive} employees
	 */
	LOW
}
