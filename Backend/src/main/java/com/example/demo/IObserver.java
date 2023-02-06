package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IObserver {
    void update(Machine m) throws InterruptedException, JsonProcessingException;
}