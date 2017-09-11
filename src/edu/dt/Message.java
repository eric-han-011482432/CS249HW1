package edu.dt;

/**
 * Created by tphadke on 8/29/17.
 */
public enum  Message {
    ALREADY ,PARENT, M;
	private Processor sender = null;
	public Processor getSender() {
		return this.sender;
	}
	public void setSender(Processor p) {
		this.sender = p;
	}
}
