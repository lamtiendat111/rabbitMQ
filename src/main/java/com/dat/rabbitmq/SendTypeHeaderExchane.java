package com.dat.rabbitmq;

import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.Map;

public class SendTypeHeaderExchane
{
    private final static String EXCHANGE_NAME = "HeaderExchange";
    public static void main (String[] argv) throws Exception
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConnectionFactory.DEFAULT_HOST);
//        factory.setPort(8081);
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
//            channel.queueDeclare(EXCHANG_NAME,false,false,false,null);
            Map<String, Object> header = new HashMap<>();
            header.put("header","header1");
            header.put("action","action1");
            header.put("action2","action1");
            header.put("group","1");
            channel.exchangeDeclare(EXCHANGE_NAME,BuiltinExchangeType.HEADERS,true,false,null);
            String message = String.join(".", argv);
            AMQP.BasicProperties headers =  AMQP.BasicProperties.Builder.class.newInstance().headers(header).build();
            channel.basicPublish(EXCHANGE_NAME, "", headers, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
