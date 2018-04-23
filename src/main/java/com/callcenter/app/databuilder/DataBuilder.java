package com.callcenter.app.databuilder;

import com.callcenter.app.model.call.Call;
import com.callcenter.app.model.employee.*;

import java.util.concurrent.PriorityBlockingQueue;

// TODO: Auto-generated Javadoc
/**
 * Contains data for testing and initial applicacion start up.
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
public class DataBuilder  {

    /**
     * Gets the employees data.
     *
     * @return the employees data
     */
    public static PriorityBlockingQueue<AbstractEmployee> getEmployeesData() {

        final PriorityBlockingQueue<AbstractEmployee> employees =
                new PriorityBlockingQueue<>(5, new EmployeeComparator());

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
        

        return employees;
    }

    /**
     * Gets the call data.
     *
     * @return the call data
     */
    public static PriorityBlockingQueue<Call> getCallData() {

        final PriorityBlockingQueue<Call> calls = new PriorityBlockingQueue<>();
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
        calls.add(new Call("Willie", "Damn scots! They ruined Scotland"));
        calls.add(new Call("Maude Flanders", "Lets talk about jesus."));
        calls.add(new Call("Philip J Fry", "I came from 20th century"));
        calls.add(new Call("Turanga Leela", "Im a ciclope"));
        calls.add(new Call("Bender", "Bite my shinny a**"));
        calls.add(new Call("Bonder", "I will kill you, Bender"));
        
        return calls;
    }
}
