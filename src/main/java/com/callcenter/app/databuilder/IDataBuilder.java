package com.callcenter.app.databuilder;

import com.callcenter.app.model.call.Call;
import com.callcenter.app.model.employee.AbstractEmployee;

import java.util.concurrent.PriorityBlockingQueue;

public interface IDataBuilder {

    public  PriorityBlockingQueue<AbstractEmployee> getEmployeesData();

    public PriorityBlockingQueue<Call> getCallData();
}
