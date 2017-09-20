package edu.dt;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by tphadke on 8/29/17.
 */
public class Processor implements Observer {
    //Each processsor has a message Buffer to store messages
    Buffer messageBuffer ;
    Processor parent;
    // this variable is used to initialize the processor id
    private static int count = 0;
    Integer id ;
    List<Processor> children ;
    //Initially it will be all the neighbors of a Processor. When a graph is created this list is populated
    List<Processor> unexplored ;

    public Processor() {
        messageBuffer = new Buffer();
        // we increment the count by 1 each time a new processor is instantiated
        id = count ++;
        // making an arraylist of children processors
        children = new CopyOnWriteArrayList<Processor>();
        //Initially it will be all the neighbors of a Processor. When a graph is created this list is populated
        unexplored = new CopyOnWriteArrayList<Processor>();
        //Each processor is observing itself;
        messageBuffer.addObserver(this);
    }

    //This method will only be used by the Processor
	private void removeFromUnexplored(Processor p){
	    	unexplored.remove(p);
        //TODO: implement removing one processor from the list of unexplored
    }
    //here we are adding a processor p to the processor's children if it doesn't already contain it.
    private void addToChildren(Processor p) {
    		if(!children.contains(p)) {
    			children.add(p);
    		}
    }

    //This method will add a message to this processors buffer.
    //Other processors will invoke this method to send a message to this Processor
    public void sendMessgeToMyBuffer(Message message){
    		System.out.println("sending a " + message + " message to Processor#" + id);
        messageBuffer.setMessage(message);
    }

    //This is analogous to receive method. Whenever a message is dropped in its buffer this Processor will respond
    //TODO: implement the logic of receive method here
    //      Hint: Add switch case for each of the conditions given in receive
    public void update(Observable observable, Object arg) {
    		Buffer b = (Buffer) observable;
    		Message msg = b.getMessage();
    		if(msg==null) {
    			if(Main.root != null) {
    				// if the processor is the root and doesn't have a parent, set itself as its own parent and explore
    				if(this == Main.root && parent == null) {
    					parent = this;
    					explore();
    				}
    			}
    		}
	    	switch(msg){
		    	case M: {
		    		if (parent == null) {
		    			// if a processor receives a message and its parent is null, set its parent to the message's sender and
		    			// removeFromUnexplored(sender) and explore
		    			parent = msg.getSender();
		    			removeFromUnexplored(msg.getSender());
		    			explore();
		    		} else {
		    			// else processor already has a parent set and needs to send an already message to the message sneder, removing
		    			// from unexplored the sender.
		    			Processor sender = msg.getSender();
		    			Message already = Message.ALREADY;
		    			already.setSender(this);
		    			sender.sendMessgeToMyBuffer(already);
		    			removeFromUnexplored(sender);
		    		}
		    	}
		    	case ALREADY: {
		    		// when a processor receives an already message, just explore
		    		explore();
		    	}
		    	case PARENT: {
		    		//when a processor receives a parent message, it adds the sender processor to its children. 
		    		addToChildren(msg.getSender());
		    		explore();
		    	}
	    	}
    }

    private void explore(){
    		// iF processors remain in unexplored, extract the first one and send a message to it.
    		if(unexplored.size() != 0) {
			Processor p = unexplored.get(0);
			removeFromUnexplored(p);
			Message m = Message.M;
			m.setSender(this);
			p.sendMessgeToMyBuffer(m);
    		} else {
    			//else if parent is not equal to this, send parent to parent.
    			if(parent != this) {
    				Message parentMessage = Message.PARENT;
    				parentMessage.setSender(this);
    				if(parent != null) parent.sendMessgeToMyBuffer(parentMessage);
    				terminate();
    			}
    		}
    }
    public void terminate(){
    		return;
    }
}
