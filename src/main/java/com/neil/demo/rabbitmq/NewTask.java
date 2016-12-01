package com.neil.demo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Neil on 16/4/18.
 * 在多个消费者间分发耗时任务
 */
public class NewTask {

    //队列名称
    private final static String QUEUE_NAME = "workqueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接和频道
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发送10条消息
        for(int i = 0; i<10; i++){
            String message = "消息"+i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        }
        channel.close();
        connection.close();

    }
}
