package com.neil.demo.rabbitmq;

import com.rabbitmq.client.*;

/**
 * Created by Neil on 16/4/18.
 * 不断的等待rabbitmq服务器发送消息
 */
public class Receive {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args){
        //与发送端一样,打开和创建频道
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            //创建队列消费者
            QueueingConsumer consumer = new QueueingConsumer(channel);
            //指定消息队列
            channel.basicConsume(QUEUE_NAME,true,consumer);
            while(true){
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                System.out.println("消费者收到消息:"+message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
