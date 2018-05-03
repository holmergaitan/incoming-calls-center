package com.callcenter.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.callcenter.app.jpa.ICallDao;
import com.callcenter.app.model.call.Call;
import com.callcenter.app.model.call.CallState;
import com.callcenter.app.model.employee.AbstractEmployee;
import com.callcenter.app.model.employee.EmployeeComparator;
import com.callcenter.app.model.employee.Executive;
import com.callcenter.app.model.employee.Operator;
import com.callcenter.app.model.employee.Supervisor;

/**
 * Test the {@link IncomingCallService} class
 * 
 * @author Holmer Gaitan
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class IncomingCallServiceMockTest extends PowerMockTestCase {
    
	/** The call dao. */
	@Mock
	private ICallDao callDao;

	/** The call dispatcher. */
	@InjectMocks
	private CallDispatcher callDispatcher;

	/**
	 * Send multiple calls answering attempts(10). The callDispatcher contains 16
	 * calls. After test execution, there will be 6 calls queued, pending for answer
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Test
	public void answerMultipleCallsTest() throws InterruptedException {

		final CallDispatcher dispatcher = new CallDispatcher(callDao);
        final IncomingCallService incomingCallService = new IncomingCallService(dispatcher);
		Mockito.when(callDao.saveAll(Mockito.any(Iterable.class))).thenReturn(new ArrayList<>());

		for (int i = 0; i < 10; i++) {

			incomingCallService.answerSingleCall();
		}

		TimeUnit.SECONDS.sleep(20);
		Mockito.verify(callDao, Mockito.atLeastOnce()).saveAll(Mockito.any(Iterable.class));
		Assert.assertEquals(dispatcher.countPendingCalls(), Integer.valueOf(6));
	}

	/**
	 * Try to answer all calls. There is 16 calls and 12 employees After test, there
	 * will be 15 calls pending for answer.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Test
	public void answerAllCallsTest() throws InterruptedException {

		final CallDispatcher dispatcher = new CallDispatcher(callDao);
        final IncomingCallService incomingCallService  = new IncomingCallService(dispatcher);
		incomingCallService.answerSingleCall();
		
		TimeUnit.SECONDS.sleep(20);
		Assert.assertEquals(dispatcher.countPendingCalls(), Integer.valueOf(15));
	}
	
	/**
	 * Test the dispatcher method with ten calls and on employee. After test
	 * execution, there will be nine pending calls in the queue
	 * 
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Test
	public void anwerCallsWithOneEmployee() throws InterruptedException {
		
		final CallDispatcher dispatcher = new CallDispatcher(callDao);
		final List<Call> calls = new ArrayList<>(10);

		calls.add(new Call("Marge Simpson", "You should not eat that, Homer!"));
		calls.add(new Call("Homer Simpson", "DoH!"));
		calls.add(new Call("Bart Simpson", "Eat my pants!"));
		calls.add(new Call("Maggie Simpson", "Hi!"));
		calls.add(new Call("Lisa Simpson", "If anyone wants me, i will be in my room."));
		calls.add(new Call("Sideshow Bob", "I WILL KILL YOU, BART!"));
		calls.add(new Call("Julius Hibbert", "Hi dilly ho dilly!"));
		calls.add(new Call("Patty Bouvier", "I hate Homer"));
		calls.add(new Call("Selma Bouvier", "Homer is a dirty dirty dog!"));
		calls.add(new Call("Kent Brockman", "You are viewing channel six."));

		final IncomingCallService incomingCallService = new IncomingCallService(dispatcher);
		dispatcher.deleteAllCalls();
		dispatcher.deleteAllEmployees();

		dispatcher.addNewEmployee(new Operator("Abraham Simpson"));
		dispatcher.receiveIncomingCalls(calls);
		Mockito.when(callDao.saveAll(Mockito.any(Iterable.class))).thenReturn(new ArrayList<>());
		incomingCallService.answerAllCalls();
		
		TimeUnit.SECONDS.sleep(20);
		final Call answeredCall = 
				calls.stream()
				.peek(testObject -> System.out.println("Will filter by ANSWERD state"))
				.filter(theOnlyAnsweredCall -> CallState.ANSWERED.equals(theOnlyAnsweredCall.getState()))
				.findFirst()
				.get();

		Mockito.verify(callDao, Mockito.atLeastOnce()).saveAll(Mockito.any(Iterable.class));
		Assert.assertNotNull(answeredCall.getResponse());
		Assert.assertEquals(Integer.valueOf(9), dispatcher.countPendingCalls());
	}
	
	/**
	 * Test the dispatcher method with five calls and three employee. After test
	 * execution, there will be two pending calls in the queue
	 * 
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Test
	public void anwerCallsWithThreeEmployees() throws InterruptedException {
		
		final CallDispatcher dispatcher = new CallDispatcher(callDao);
		final List<Call> calls = new ArrayList<>(10);

		calls.add(new Call("Marge Simpson", "You should not eat that, Homer!"));
		calls.add(new Call("Homer Simpson", "DoH!"));
		calls.add(new Call("Bart Simpson", "Eat my pants!"));
	    calls.add(new Call("Willie", "Damn scots! They ruined Scotland"));
        calls.add(new Call("Maude Flanders", "Lets talk about jesus."));

		final IncomingCallService incomingCallService = new IncomingCallService(dispatcher);
		dispatcher.deleteAllCalls();
		dispatcher.deleteAllEmployees();

		dispatcher.addNewEmployee(new Operator("Abraham Simpson"));
		dispatcher.addNewEmployee(new Supervisor("Julius Hibbert"));
		dispatcher.addNewEmployee(new Executive("Clancy Wiggum"));
		dispatcher.receiveIncomingCalls(calls);
		
		Mockito.when(callDao.saveAll(Mockito.any(Iterable.class))).thenReturn(new ArrayList<>());
		incomingCallService.answerAllCalls();
		
		TimeUnit.SECONDS.sleep(20);
		Mockito.verify(callDao, Mockito.atLeastOnce()).saveAll(Mockito.any(Iterable.class));
		Assert.assertEquals(Integer.valueOf(2), dispatcher.countPendingCalls());
	}
	
	/**
	 * Test the employees assignment in the queue
	 */
	@Test
	public void employeePriorityAssignmentTest() {

		final String expectedOrder = "Moe Szyslak HIGH|Apu Nahasapeemapetilon HIGH|Clancy Wiggum HIGH|"
				+ "Nelson Muntz HIGH|Milhouse Van Houten MEDIUM|Abraham Simpson MEDIUM|"
				+ "Barney Gumble MEDIUM|Seymour Skinner MEDIUM|Edna Krabappel LOW|"
				+ "Ralph Wiggum LOW|Waylon Smithers LOW|Krusty the Clown LOW|";

		StringBuilder builder = new StringBuilder();
		final PriorityBlockingQueue<AbstractEmployee> employees = new PriorityBlockingQueue<>(5,
				new EmployeeComparator());

		employees.add(new Supervisor("Barney Gumble"));
		employees.add(new Operator("Moe Szyslak"));
		employees.add(new Executive("Krusty the Clown"));

		employees.add(new Supervisor("Abraham Simpson"));
		employees.add(new Operator("Apu Nahasapeemapetilon"));
		employees.add(new Executive("Waylon Smithers"));

		employees.add(new Supervisor("Seymour Skinner"));
		employees.add(new Operator("Clancy Wiggum"));
		employees.add(new Executive("Ralph Wiggum"));

		employees.add(new Supervisor("Milhouse Van Houten"));
		employees.add(new Operator("Nelson Muntz"));
		employees.add(new Executive("Edna Krabappel"));

		while (!employees.isEmpty()) {

			builder.append(employees.poll().toString() + "|");
		}

		Assert.assertEquals(builder.toString(), expectedOrder);
	}
	
	/**
	 * Test the employees assignment in the queue
	 */
	@Test
	public void employeePriorityAssignmentTest2() {
		
		final String expectedOrder = "Moe Szyslak HIGH|Barney Gumble MEDIUM|Krusty the Clown LOW|";

		StringBuilder builder = new StringBuilder();
		final PriorityBlockingQueue<AbstractEmployee> employees = new PriorityBlockingQueue<>(5,
				new EmployeeComparator());

		employees.add(new Executive("Krusty the Clown"));
		employees.add(new Operator("Moe Szyslak"));
		employees.add(new Supervisor("Barney Gumble"));

		while (!employees.isEmpty()) {

			builder.append(employees.poll().toString() + "|");
		}

		Assert.assertEquals(builder.toString(), expectedOrder);
	}
}
