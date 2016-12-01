package com.neil.demo.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieTool {
	
	/**
	 * maxAge设为null则为cookie默认过期时间（浏览器关闭，cookie失效） 
	 * 
	 */
	public static void setCookie(HttpServletRequest req,HttpServletResponse resp, String name,String value,Integer maxAge){
		Cookie cookie = new Cookie(name,value);
		if(maxAge != null){
			cookie.setMaxAge(maxAge);
		}
		cookie.setPath("/");
		cookie.setDomain(".era.com");
		resp.addCookie(cookie);
	}
	
	public static Cookie getCookie(HttpServletRequest req,String name){
		Cookie cookies[] = req.getCookies();
		if(cookies == null || name == null || name.length() == 0){
			return null;
		}
		for(int i=0; i<cookies.length; i++){
			if(name.equals(cookies[i].getName())){
				return cookies[i];
			}
		}
		return null;
	}
	
	public static void deleteCookie(HttpServletRequest req,HttpServletResponse resp,Cookie cookie){
		if(cookie != null){
			cookie.setPath("/");
			cookie.setValue("");
			cookie.setMaxAge(0);
			cookie.setDomain(".era.com");
			resp.addCookie(cookie);
		}
	}
	
	public static String getPath(HttpServletRequest req){
		String path = req.getContextPath();
		return (path == null || path.length() == 0) ? "/" : path;
	} 
}
