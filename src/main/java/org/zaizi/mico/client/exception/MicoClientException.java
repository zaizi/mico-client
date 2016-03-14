package org.zaizi.mico.client.exception;

/**
 * 
 * @author Chalitha Perera
 *
 */
public class MicoClientException extends Exception{

	private static final long serialVersionUID = -1253277058438872097L;
	
	/**
	 * 
	 * @param msg
	 */
	public MicoClientException(String msg){
		super(msg);
	}
	
	/**
	 * 
	 * @param msg
	 * @param e
	 */
	public MicoClientException(String msg, Throwable e){
		super(msg, e);
	}

}
