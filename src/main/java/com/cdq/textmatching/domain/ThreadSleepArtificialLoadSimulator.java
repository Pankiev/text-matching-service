package com.cdq.textmatching.domain;


import lombok.SneakyThrows;

public class ThreadSleepArtificialLoadSimulator implements ArtificialLoadSimulator {
    @Override
    @SneakyThrows
    public void simulateArtificialLoad() {
        Thread.sleep(2000);
    }
}
