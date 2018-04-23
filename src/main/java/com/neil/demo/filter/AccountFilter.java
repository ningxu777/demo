package com.neil.demo.filter;

import com.neil.demo.cookie.CookieTool;
import com.neil.demo.safe.TokenTool;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountFilter implements Filter {
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest)req;
		HttpServletResponse hresp = (HttpServletResponse)resp;
		Cookie cookie = CookieTool.getCookie(hreq, "token");
		if(cookie != null){
			int id = TokenTool.decode(String.valueOf(cookie.getValue()));
//			PersonService personService = new PersonService();
//			Person person = personService.getUserById(id);
//			hreq.setAttribute("person", person);
		}else{
			hreq.setAttribute("user", null);
		}
		filterChain.doFilter(hreq, hresp);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}
}
