package com.callcenter.app.model.employee;

/**
 * This class represents an Operator employee, that has the
 * {@link EmployeePriority#HIGH} priority in the system.
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
public class Operator extends AbstractEmployee {

	/**
	 * Instantiates a new operator employee.
	 *
	 * @param name
	 *            the employee name
	 */
	public Operator(final String name) {

		super(name, EmployeePriority.HIGH);
	}
}
