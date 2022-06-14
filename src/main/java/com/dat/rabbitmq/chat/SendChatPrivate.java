package com.dat.rabbitmq.chat;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class SendChatPrivate {
    //////////Dat
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchange = "private";
        String queueName = "queue.dat";
        String routingKey = "direct.chat.thuy";
        Channel channel1 = connection.createChannel();
        channel1.queueDeclare(queueName, true, false, false, null);
        String routingKeyReceive = "direct.chat.dat";
        channel1.queueBind(queueName, exchange, routingKeyReceive);
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println();
            System.out.println("Thuy: " + new String(message.getBody()));
        };
        channel.basicConsume(queueName,
                deliverCallback,
                consumerTag -> {
                },
                null);
        while(true){
            System.out.print("Dat (Me): ");
            InputStream stream = System.in;
            Scanner scanner = new Scanner(stream);
            String input = scanner.nextLine();
            if(input != null) {
                channel.exchangeDeclare(exchange, BuiltinExchangeType.DIRECT, true, false, null);

                byte[] message = input.getBytes(StandardCharsets.UTF_8);
                channel.basicPublish(exchange, routingKey, null, message);
                input = null;
            }

        }
    }
}
