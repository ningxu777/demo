package com.neil.demo.threadpool;

import java.util.concurrent.Callable;

/**
 * 
 * @author gooooooo
 *
 */
public class Task<V> implements Callable<V>{
	
	private String digest; //参数或描述
	private Callable<V> callable; //Callable实例
	
	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public Callable<V> getCallable() {
		return callable;
	}

	public void setCallable(Callable<V> callable) {
		this.callable = callable;
	}



	@Override
	public V call() throws Exception {
		// TODO Auto-generated method stub
		return this.callable.call();
	}

}
