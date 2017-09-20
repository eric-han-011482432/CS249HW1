package edu.dt;

/**
 * Created by tphadke on 8/29/17.
 */
public enum  Message {
    ALREADY ,PARENT, M;
	private Processor sender = null;
	// returns sender in Message
	public Processor getSender() {
		return this.sender;
	}
	// set sender of a message
	public void setSender(Processor p) {
		this.sender = p;
	}
}
