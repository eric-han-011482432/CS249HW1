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
    Integer id ;
    List<Processor> children ;
    //Initially it will be all the neighbors of a Processor. When a graph is created this list is populated
    List<Processor> unexplored ;

    public Processor() {
        messageBuffer = new Buffer();
        id = Integer.MIN_VALUE; //This is an invalid value. Since only +ve values are acceptable as processor Ids.
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
    	children.add(p);
        //TODO: implement removing one processor from the list of Children
    }

    //This method will add a message to this processors buffer.
    //Other processors will invoke this method to send a message to this Processor
    public void sendMessgeToMyBuffer(Message message){
        messageBuffer.setMessage(message);
    }


    //This is analogous to recieve method.Whenever a message is dropped in its buffer this Pocesssor will respond
    //TODO: implement the logic of receive method here
    //      Hint: Add switch case for each of the conditions given in receive
    public void update(Observable observable, Object arg) {
    	Message msg = messageBuffer.getMessage();
    	switch(msg){
	    	case ALREADY: {
	    	}
	    		//add pj to other
	    	case PARENT: {
	    		//add pj to children
	    	}
	    	case M: {
	    		explore();
	    		//sen
	    	}
    	}
    }

    private void explore(){
    	for(Processor p : unexplored){
    		removeFromUnexplored(p);
    	}
        //TODO: implement this method.
    }

}
