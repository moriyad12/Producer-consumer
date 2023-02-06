package com.example.demo;

public class Product {
    private String Id;
    private Long Time=1l;
    private String Color;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Long getTime() {
        return Time;
    }

    public void setTime(Long time) {
        Time = time;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public Product(String id, String color) {
        Id = id;
        Color = color;
    }
}
