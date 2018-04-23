package com.neil.demo.filter;

import com.neil.demo.cookie.CookieTool;
import com.neil.demo.safe.TokenTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Neil on 2016/12/3.
 */
//@Component
public class SpringAccountFilter extends OncePerRequestFilter{

//    @Autowired
//    PersonService personService;

    Logger log = LoggerFactory.getLogger(AccountFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("--------------------------------------________________");
        String url = httpServletRequest.getRequestURI();
        if(url.contains("Account/SignIn") || url.contains("Account/LoginOn") || url.contains("Account/LoginOut")){
            //登录页面什么也不做
        }else if(url.endsWith(".js") || url.endsWith(".css") || url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".jpeg") || url.endsWith(".JPEG")){
            System.out.println("请求静态资源："+url);
        }else{
            Cookie cookie = CookieTool.getCookie(httpServletRequest, "token");
            if(cookie != null){
                String token = cookie.getValue();
                System.out.println("获取cookie："+token);
                log.info("~~~~~~~~~~~~~~~~~~~~~获取cookie为：{}~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",token);
                int id = TokenTool.decode(String.valueOf(token));
                System.out.println("解析token得id为："+String.valueOf(id));

//                PersonService personService = new PersonService();

                CookieTool.setCookie(httpServletRequest, httpServletResponse, "token", token, 3000);

//                ServletContext sc = httpServletRequest.getSession().getServletContext();
//                XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);
//                personService = (PersonService) cxt.getBean("PersonService");
//                Person person = personService.getUserByIdX(id);
                System.out.println("getUserById(id)得："+String.valueOf(id));

//                httpServletRequest.setAttribute("person", person);
            }else{
//                httpServletRequest.setAttribute("person", null);
                httpServletResponse.sendRedirect("/zh-cn/Account/SignIn");
                return;
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }


}
