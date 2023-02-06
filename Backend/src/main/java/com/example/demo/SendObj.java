package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SendObj {
    String from;
    String to;
    String color;

    public SendObj(String from, String to, String color){
        this.from = from;
        this.to = to;
        this.color = color;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    protected ObjectMapper mapper =new ObjectMapper();
    public String toJson() throws JsonProcessingException {
        SendObj send=new SendObj(this.from,this.to,this.color);
        String json= mapper.writeValueAsString(send);
        return json;
    }
}
