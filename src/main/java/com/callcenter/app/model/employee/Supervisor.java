package com.callcenter.app.model.employee;

/**
 * This class represents an Supervisor employee, that has the
 * {@link EmployeePriority#MEDIUM} priority in the system.
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
public class Supervisor extends AbstractEmployee {

	/**
	 * Instantiates a new supervisor employee.
	 *
	 * @param name
	 *            the name
	 */
	public Supervisor(final String name) {

		super(name, EmployeePriority.MEDIUM);
	}
}
