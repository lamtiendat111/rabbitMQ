package com.dat.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send2
{
    private final static String EXCHANGE_NAME = "logs1";
    public static void main (String[] argv) throws Exception
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConnectionFactory.DEFAULT_HOST);
//        factory.setPort(8081);
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
//            channel.queueDeclare(EXCHANG_NAME,false,false,false,null);
            channel.exchangeDeclare(EXCHANGE_NAME,BuiltinExchangeType.DIRECT,true);
            String message = String.join(".", argv);

            channel.basicPublish(EXCHANGE_NAME, "key.direct.enable", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
