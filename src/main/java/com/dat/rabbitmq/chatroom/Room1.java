package com.dat.rabbitmq.chatroom;


import com.rabbitmq.client.*;

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class Room1 {
    /////////////Thuy
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchange = "room";
        String queueName = UUID.randomUUID().toString();
        String routingKey = "direct.chat.dat";
        channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC, true, false, null);
        channel.queueDeclare(queueName,true,false,false,null);
        String routingKeyReceive = "direct.chat.*";
        channel.queueBind(queueName,exchange,routingKeyReceive);
        String input = null;
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println();
            System.out.println("hello " + queueName+ ": " +new String(message.getBody().toString()));

        } ;
        channel.basicConsume(    queueName,
                deliverCallback,
                consumerTag -> {},
                null);
        while(true){
            System.out.print("Thuy (Me): ");

            InputStream stream = System.in;
            Scanner scanner = new Scanner(stream);
            input = scanner.nextLine();
            if(input != null) {
                byte[] message = input.getBytes(StandardCharsets.UTF_8);
                channel.basicPublish(exchange, routingKey, null, message);
                input = null;
            }

        }
    }
}

