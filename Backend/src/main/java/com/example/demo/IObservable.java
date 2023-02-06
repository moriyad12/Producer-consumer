package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IObservable {
    void notifyAllObservers() throws InterruptedException, JsonProcessingException;
}
