package com.neil.demo.jersey.demo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Neil on 2017/9/15.
 */
@Path("demo")
public class HelloResource {
    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(){
        return "HelloWorld!";
    }
}
