package com.dat.rabbitmq.entities;


import lombok.Data;

@Data
public class User {
    private int id;
    private String codeQueue;
    private int status;
}
