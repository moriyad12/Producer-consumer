package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class Controller {
    Service service;
    @Autowired
    Controller(Service service){
        this.service=service;
    }
    @GetMapping("/addM")
    public void addMachine(@RequestParam String id){
        service.createMachine(id);
    }

    @GetMapping("/addQ")
    public void addQ(@RequestParam String id){
        service.createQueue(id);
    }
    @GetMapping("connect")
    public void connect(@RequestParam String machineId,@RequestParam String queueId,@RequestParam boolean fromQtoM){
        service.Connect(machineId,queueId,fromQtoM);
    }
    @GetMapping("clear")
    public void clear(){
        service.clear();
    }
    @GetMapping("product")
   public void addP(@RequestParam int pro){
        service.addP(pro);
    }

    @GetMapping("run")
    public void run(@RequestParam int pro){
        service.run(pro);
    }
    @GetMapping("replay")
    public void replay(){
        service.replay();
    }
    @GetMapping("change")
    public ResponseEntity<String> change(){
        if(MainQ.q.size()>0){
            return new  ResponseEntity<String>(MainQ.q.poll(),HttpStatus.OK);
        }
        return new  ResponseEntity<String>("none",HttpStatus.OK);
    }
}
