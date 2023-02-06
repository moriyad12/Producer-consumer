package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

public class main {
    static Board board=new Board();
    static Service service=new Service(board);
    public static void main(String[] args) {
        main.service.createMachine("M1");
        main.service.createQueue("Q1");
        main.service.createQueue("Q2");
        main.service.Connect("M1","Q1",true);
        main.service.Connect("M1","Q2",false);
        main.service.addP(10);
//        main.service.run();
//        main.service.replay();

    }

}
