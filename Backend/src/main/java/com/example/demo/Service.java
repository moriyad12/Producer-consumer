package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Queue;
@org.springframework.stereotype.Service
public class Service {
    Board board;

    @Autowired
    public Service(Board board) {
        this.board = board;
    }
    public void Connect(String MachineId, String QueueId, Boolean FromQToM) {
        Machine ma = board.getMachine(MachineId);
        Queues q = board.getQueue(QueueId);
        if (FromQToM) {
            ma.addQueueBefore(q);
            q.addMachineToQueue(ma);
        } else {
            ma.setQafter(q);
        }
    }

    public void addP(int pro){
        board.addP(pro);
    }
    public void clear(){
        board.clear();
    }
    public void createMachine(String Id) {
        Machine ma = new Machine(Id);
        board.addMachine(ma);
    }
    public void createQueue(String Id) {
        Queues q = new Queues(Id);
        board.addQueue(q);
    }

    public void removeMachine(String Id) {
        board.removeMachine(Id);
    }

    public void removeQueue(String Id) {
        board.removeQueue(Id);
    }
    public void run(int pro){
        board.run(pro);
    }
    public void replay(){

        board.replay();
        board.run(0);
    }
}
