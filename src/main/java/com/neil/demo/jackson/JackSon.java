package com.neil.demo.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 16/5/4.
 */
public class JackSon {

    /**
     * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
     * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
     * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
     * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
     * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
     * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
     */
    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("neil");
        user.setEmail("12345@qq.com");

        ObjectMapper mapper = new ObjectMapper();
        //User类转为json
        String userJson = mapper.writeValueAsString(user);
        System.out.println(userJson);
        //集合转为json
        List<User> users = new ArrayList<User>();
        users.add(user);
        String usersJson = mapper.writeValueAsString(users);
        System.out.println(usersJson);

        //json转为java类
        String jsonStr = "{\"name\":\"小民\",\"birthday\":844099200000,\"email\":\"xiaomin@sina.com\"}";
        User user1 = mapper.readValue(jsonStr,User.class);
        System.out.println(user1.getBirthday());
    }
}
