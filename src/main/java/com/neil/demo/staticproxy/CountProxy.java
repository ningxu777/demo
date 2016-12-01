package com.neil.demo.staticproxy;
/**
 * 这是一个静态代理类（增强CountImpl实现类）
 * @author gooooooo
 *
 */
public class CountProxy implements Count{
	
	private CountImpl countImpl;
	
	public CountProxy(CountImpl countImpl){
		this.countImpl = countImpl;
	}
	
	public void queryCount() {
		System.out.println("查询账户之前执行的代码。。。");
		countImpl.queryCount();
		System.out.println("查询账户之后要执行的代码。。。");
	}

	public void updateCount() {
		System.out.println("修改账户之前要执行的代码。。。");
		countImpl.updateCount();
		System.out.println("修改账户之后会执行的代码。。。");
	}
	
	//测试静态代理类
	public static void main(String[] argsStrings){
		CountImpl countImpl = new CountImpl();
		CountProxy countProxy = new CountProxy(countImpl);
		countProxy.queryCount();
		countProxy.updateCount();
	}
}
