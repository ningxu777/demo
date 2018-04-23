package com.neil.demo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter{
	private FilterConfig filterConfig = null;
	private String encoding = null;
	
	public void destroy() {
		filterConfig = null;
		encoding = null;
	}
	
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest)req;
		HttpServletResponse hresp = (HttpServletResponse)resp;
//		if(req.getCharacterEncoding() == null){
			if(encoding != null){
				hreq.setCharacterEncoding(encoding);
				hresp.setContentType("text/html; charset="+encoding);
			}
//		}
		System.out.println("----------------------------------------------"+encoding);
		filterChain.doFilter(hreq, hresp);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
	}
	
}
