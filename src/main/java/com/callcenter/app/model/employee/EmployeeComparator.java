package com.callcenter.app.model.employee;

import com.callcenter.app.model.employee.AbstractEmployee;

import java.util.Comparator;

/**
 * Defines an employee priority using their State ordinal
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
public class EmployeeComparator implements Comparator<AbstractEmployee> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(final AbstractEmployee actualEmployee, final AbstractEmployee newEmployee) {

		return actualEmployee.getPriority().compareTo(newEmployee.getPriority());
	}
}
