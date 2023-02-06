package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class Board {

    public List<Thread> list=new ArrayList<>();
    private HashMap<String, Machine> machines = new HashMap<>();
    private HashMap<String, Queues> queues = new HashMap<>();
    private Queue<Product> products=new LinkedList<>();


    public HashMap<String, Machine> getMachine() {
        return machines;
    }

    public void setMachine(HashMap<String, Machine> machine) {
        this.machines = machine;
    }

    public HashMap<String, Queues> getQueue() {
        return queues;
    }

    public void setQueue(HashMap<String, Queues> queue) {
        this.queues = queue;
    }

    int num=1;
    void addMachine(Machine machine) {
        this.machines.put(machine.getId(), machine);
    }
    String[] colors=new String[]{"red", "blue", "pink", "grey", "cyan", "orange", "yellow","lightseagreen","beige"};
    public void addP(int pro){
        while (pro>0){
            Product product= new Product("P"+String.valueOf(num++),colors[pro%7]);
            this.products.add(product);
            queues.get("Q1").addProductToQueue(product);
            pro--;
        }
    }
    void addQueue(Queues queue) {
        this.queues.put(queue.getId(), queue);
    }
    void clear(){
        machines.clear();
        queues.clear();
        products.clear();
    }

    public Board() {
    }

    void run(int pro){
        if(pro>0)
        addP(pro);
        machines.forEach(
                (key, value)
                        -> list.add(value.SecCons()));
    }
    void replay(){
        for (int i=0;i<list.size();i++){
            list.get(i).stop();
        }
        queues.get(MainQ.last).setProductsList(new LinkedList<>());
//        Collections.reverse((List<?>) products);
        queues.get("Q1").setProductsList(products);
//        queues.forEach((key,value)-> value=new Queues(key));
    }

    void removeQueue(String id) {
        this.queues.remove(id);
    }

    void removeMachine(String id) {
        this.machines.remove(id);
    }

    public Machine getMachine(String Id) {
        return this.machines.get(Id);
    }

    public Queues getQueue(String Id) {
        return this.queues.get(Id);
    }
}
