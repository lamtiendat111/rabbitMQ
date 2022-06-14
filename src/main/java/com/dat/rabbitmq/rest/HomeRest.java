package com.dat.rabbitmq.rest;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class HomeRest {

    private static final String EXCHANGE_NAME = "MessageService";

    @GetMapping("send")
    public String sendMessage(String message)
    {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(ConnectionFactory.DEFAULT_HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
//            channel.queueDeclare(EXCHANG_NAME,false,false,false,null);
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT,true,false,null);

            channel.basicPublish(EXCHANGE_NAME, "key.direct.enable", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            channel.close();
            connection.close();
            return "okay";
        } catch (Exception e) {
            return e.getMessage();
        }

    }
}
