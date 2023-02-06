package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Queues implements IObserver {
    private String id;
    @Autowired
    public ArrayList<Machine> getConnectedMachines() {
        return connectedMachines;
    }
    public void setConnectedMachines(ArrayList<Machine> connectedMachines) {
        this.connectedMachines = connectedMachines;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Queue<Product> getProductsList() {
        return productsList;
    }


    public void setProductsList(Queue<Product> productsList) {
        this.productsList = productsList;
    }

    private Queue<Product> productsList;
    private ArrayList<Machine> connectedMachines;

    public Queues(String id) {
        this.id = id;
        MainQ.last=id;
        this.productsList = new LinkedList<>();
        this.connectedMachines = new ArrayList<>();
    }

    public void addProductToQueue(Product product) {
        productsList.add(product);
        //change other attributes in other classes
    }

    public void addMachineToQueue(Machine m) {
        this.connectedMachines.add(m);
    }

    public synchronized Product getProductFromQueue() {
        return productsList.poll();
    }

    @Override
    public void update(Machine m) throws InterruptedException, JsonProcessingException {
        if (productsList.isEmpty())
            return;
        m.setReady(false);
        Product p = getProductFromQueue();
        if (p != null) {
            System.out.println("product " + p.getId() + " moved from queue " + this.id + " to machine " + m.getId() +" "+ Thread.currentThread().getName());
            SendObj sendObj=new SendObj(this.id,m.getId(),p.getColor());
            MainQ.q.add(sendObj.toJson());
            m.setProduct(p);
        }
    }
}