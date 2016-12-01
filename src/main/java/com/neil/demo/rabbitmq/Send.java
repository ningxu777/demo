package com.neil.demo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Neil on 16/4/18.
 * 这里是消息发送放(producer)
 * 需要添加RabbitMQ的依赖
 */

public class Send {
    //队列名称
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        //创建连接到rabbitmq
        ConnectionFactory factory = new ConnectionFactory();
        //设置rabbitmq所在主机IP
        factory.setHost("localhost");
        Connection connection = null;
        //创建链接
        try {
            connection = factory.newConnection();
            Channel channel = connection.createChannel();
            //指定一个队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //要发送的消息
            String message = "这里是要发送的消息";
            //向队列中发送消息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            //关闭channel和连接
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        //创建一个频道

    }
}
