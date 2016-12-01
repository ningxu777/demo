package com.neil.demo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * Created by Neil on 16/4/18.
 * 消费者(消息接收端),本实例的消息发送者是NewRTask.java.
 * 测试的时候是启动多个NewWork实例(即多个消费者同时工作,共同消费发送者发出的消息)
 * 可以看到，默认的，RabbitMQ会一个一个的发送信息给下一个消费者(consumer)，而不考虑每个任务的时长等等，且是一次性分配，并非一个一个分配。平均的每个消费者将会获得相等数量的消息。这样分发消息的方式叫做round-robin。
 */
public class NewWork {

    private final static String QUEUE_NAME = "workqueue";

    public static void main(String[] args) throws Exception{
        //区分不同工作进程的输出
        int hashCode = NewWork.class.hashCode();
        //创建连接和channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //指定消费队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("消费者-"+hashCode+"收到消息:"+message);
        }
    }
}
