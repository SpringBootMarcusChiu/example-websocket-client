package com.marcuschiu.examplewebsocket.model;

import lombok.Data;

@Data
public class Greeting {
    String content;

    public Greeting(String content) {
        this.content = content;
    }
}
