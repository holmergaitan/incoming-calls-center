package com.callcenter.app.model.employee;

/**
 * This class represents an Executive employee, that has the
 * {@link EmployeePriority#LOW} priority in the system.
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
public class Executive extends AbstractEmployee {

	/**
	 * Instantiates a new executive employee.
	 *
	 * @param name
	 *            the employee name
	 */
	public Executive(final String name) {

		super(name, EmployeePriority.LOW);
	}
}
