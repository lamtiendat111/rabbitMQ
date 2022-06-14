package com.dat.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Send
{
    private final static String QUEUE_NAME = "logs";
    public static void main (String[] argv) throws Exception
    {


//        factory.setPort(8081);
        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(ConnectionFactory.DEFAULT_HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            String message = String.join(".", argv);

            channel.basicPublish("", "hello", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
