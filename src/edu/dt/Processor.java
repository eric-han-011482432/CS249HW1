package edu.dt;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by tphadke on 8/29/17.
 */
public class Processor implements Observer {
    //Each processsor has a message Buffer to store messages
    Buffer messageBuffer ;
    Processor parent = null;
    private static int count = 0;
    Integer id ;
    List<Processor> children ;
    //Initially it will be all the neighbors of a Processor. When a graph is created this list is populated
    List<Processor> unexplored ;

    public Processor() {
        messageBuffer = new Buffer();
        id = count ++;
        children = new ArrayList<Processor>();
        //Initially it will be all the neighbors of a Processor. When a graph is created this list is populated
        unexplored = new ArrayList<Processor>();
        //Each processor is observing itself;
        messageBuffer.addObserver(this);
    }

    //This method will only be used by the Processor
    @SuppressWarnings("unused")
	private void removeFromUnexplored(Processor p){
	    	unexplored.remove(p);
        //TODO: implement removing one processor from the list of Children or Unexplored??
    }
    
    private void addToChildren(Processor p) {
    		children.add(p);
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
    				if(this == Main.root && parent == null) {
    					parent = this;
    					explore();
    				}
    			}
    		}
	    	switch(msg){
		    	case M: {
		    		if (parent == null) {
		    			parent = msg.getSender();
		    			removeFromUnexplored(msg.getSender());
		    			explore();
		    		} else {
		    			Processor sender = msg.getSender();
		    			Message already = Message.ALREADY;
		    			already.setSender(this);
		    			sender.sendMessgeToMyBuffer(already);
		    			removeFromUnexplored(msg.getSender());
		    		}
		    	}
		    	case ALREADY: {
		    		explore();
		    	}
		    	case PARENT: {
		    		addToChildren(msg.getSender());
		    		explore();
		    	}
	    	}
    }

    private void explore(){
    		if(unexplored.size() != 0) {
    			for(Processor p : unexplored){
    				removeFromUnexplored(p);
    				Message m = Message.M;
    				m.setSender(this);
    				p.sendMessgeToMyBuffer(m);
    			}
    		} else {
    			if(parent != this) {
    				Message parentMessage = Message.PARENT;
    				parentMessage.setSender(this);
    				parent.sendMessgeToMyBuffer(parentMessage);
    				terminate();
    			}
    		}
    }
    
    public void terminate(){
    		return;
    }
}
