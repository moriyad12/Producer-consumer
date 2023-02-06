package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.LinkedList;
import java.util.Random;

public class Machine implements IObservable,Runnable {
    Thread th;
    private String id;
    private long time;
    private String color;
    private boolean Ready;
    private Product product;
    Random random = new Random();


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) throws InterruptedException, JsonProcessingException {

        this.product = product;
        this.Ready = false;
        this.th.sleep((this.time)*1000);
        System.out.println("yes" + product.getId() + "  " + this.id);
        SendObj sendObj=new SendObj(this.id,this.getQafter().getId(), product.getId());
        MainQ.q.add(sendObj.toJson());
        this.getQafter().addProductToQueue(this.product);
        this.product = null;
        this.run();
    }

    private LinkedList<Queues> Qbefore;
    private Queues Qafter;

    public boolean isReady() {
        return Ready;
    }

    public void setReady(boolean ready) {
        Ready = ready;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Queues getQafter() {
        return Qafter;
    }

    public void setQafter(Queues qafter) {
        Qafter = qafter;
    }

    public void notifyAllObservers() throws InterruptedException, JsonProcessingException {
        for (Queues queue : this.Qbefore) {

            queue.update(this);
        }
    }

    public Machine(String id) {
        this.id = id;
        this.color = "grey";
        this.setReady(true);
        Qbefore = new LinkedList<Queues>();
        this.time = this.random.nextInt(8) + 2;
    }

    public Thread SecCons() {
        Thread tr= new Thread(this);
        tr.start();
        return tr;
    }
    public void kill(){
        Thread.currentThread().stop();
    }

    public void addQueueBefore(Queues q) {
        this.Qbefore.add(q);
    }

    @Override
    public void run() {
        while (true) {
            this.Ready = true;
            try {
                this.notifyAllObservers();
            } catch (InterruptedException | JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
