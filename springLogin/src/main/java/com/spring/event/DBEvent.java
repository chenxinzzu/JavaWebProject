package com.spring.event;

import org.springframework.context.ApplicationEvent;

public class DBEvent extends ApplicationEvent {
	/**
	 * <p>Description£º</p>
	 */
	private static final long serialVersionUID = 1L;
	public String  status;
	public DBEvent(Object source){
		super(source);
	}
	public DBEvent(Object source, String status){
		super(source);
		this.status=status;
	}
	public void print(){
		System.out.println(source);
	}
}
